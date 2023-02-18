/*
 * Copyright 2017 DV Bern AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * limitations under the License.
 */

package ch.dvbern.oss.lib.iso20022.camt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import ch.dvbern.oss.lib.iso20022.Iso20022Util;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.CamtDocument;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.Notification;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.header.GroupHeader;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.header.MessagePagination;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.StatementOrNotification;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.Entry;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.TransactionDetails;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.parties.PartyOrDeptor;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.parties.PostalAddress;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.parties.RelatedParties;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.remittenceinformation.CreditorReferenceInformation;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.remittenceinformation.RemittanceInformation;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.remittenceinformation.StructuredRemittanceInformation;
import ch.dvbern.oss.lib.iso20022.dtos.camt.Account;
import ch.dvbern.oss.lib.iso20022.dtos.camt.Booking;
import ch.dvbern.oss.lib.iso20022.dtos.camt.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.dtos.camt.IsrTransaction;
import ch.dvbern.oss.lib.iso20022.dtos.camt.MessageIdentifier;
import ch.dvbern.oss.lib.iso20022.dtos.shared.TransactionInformationDTO;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * This Service reads an xml document complying to camt054 version 00104 standard and returns parts or the whole
 * document
 */
@ApplicationScoped
public class CamtServiceBean implements CamtService {

	private static final Logger LOG = LoggerFactory.getLogger(CamtServiceBean.class);

	/**
	 * Returns the Notification part of the xml input as Java object.
	 *
	 * @param xmlAsBytes xml content
	 * @return object of type BankToCustomerDebitCreditNotificationV04
	 */
	@Nonnull
	@Override
	public CamtDocument getNotificationFromXml(@Nonnull byte[] xmlAsBytes) {
		CamtTypeVersion camtTypeVersion = CamtUtil.detectCamtTypeVersion(xmlAsBytes);

		return unmarshallNotificationFromXml(xmlAsBytes, camtTypeVersion);
	}

	/**
	 * Reads an xml file and returns data about booked credit notes with reference number (no reversals).
	 *
	 * @param xmlAsBytes xml content
	 * @return object of type DocumentDTO containing data about booked ESR notifications
	 */
	@Nonnull
	@Override
	public DocumentDTO getCreditingRecords(@Nonnull byte[] xmlAsBytes) throws Iso20022RuntimeException {
		CamtTypeVersion camtTypeVersion = CamtUtil.detectCamtTypeVersion(xmlAsBytes);

		CamtDocument document = unmarshallNotificationFromXml(xmlAsBytes, camtTypeVersion);
		Notification notification = document.getNotification();

		return toDocument(requireNonNull(notification), camtTypeVersion);
	}

	@Nonnull
	private CamtDocument unmarshallNotificationFromXml(byte[] xmlAsBytes, @Nonnull CamtTypeVersion camtTypeVersion) {

		try (ByteArrayInputStream xmlAsStream = new ByteArrayInputStream(xmlAsBytes)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(camtTypeVersion.getDocumentClass());
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StreamSource streamSource = new StreamSource(xmlAsStream);
			JAXBElement<CamtDocument> doc =
				jaxbUnmarshaller.unmarshal(streamSource, camtTypeVersion.getDocumentClass());

			return doc.getValue();
		} catch (JAXBException e) {
			throw new Iso20022RuntimeException("Document can not be parsed by JAXB", e);
		} catch (IOException e) {
			throw new Iso20022RuntimeException("IO Exception while unmarshalling notification from xml", e);
		}
	}

	@Nonnull
	private DocumentDTO toDocument(@Nonnull Notification notification, @Nonnull CamtTypeVersion camtTypeVersion) {
		MessageIdentifier messageIdentifier = toMessageIdentifier(notification.getGrpHdr());
		DocumentDTO documentDTO = new DocumentDTO(camtTypeVersion, messageIdentifier);

		List<StatementOrNotification> accountNotification = notification.getAccountNotification();

		if (accountNotification.size() > 1) {
			// According to the swiss implementation guideline there should be only one notification per document
			LOG.warn("More than one Account Statement present in {}", documentDTO);
		}

		accountNotification.stream()
			.filter(n -> n.getAcct().getId().getIBAN() != null)
			.map(n -> toAccount(n, documentDTO))
			.forEach(dto -> documentDTO.getAccounts().add(dto));

		return documentDTO;
	}

	@Nonnull
	private MessageIdentifier toMessageIdentifier(@Nonnull GroupHeader grpHdr) {
		MessagePagination pagination = grpHdr.getMsgPgntn();

		if (pagination == null) {
			return new MessageIdentifier(grpHdr.getMsgId(), Iso20022Util.from(grpHdr.getCreDtTm()));
		}

		return new MessageIdentifier(
			grpHdr.getMsgId(),
			Iso20022Util.from(grpHdr.getCreDtTm()),
			pagination.getPgNb(),
			pagination.isLastPgInd()
		);
	}

	@Nonnull
	private Account toAccount(@Nonnull StatementOrNotification notification, @Nonnull DocumentDTO document) {
		Account account = new Account(
			document,
			notification.getId(),
			notification.getElctrncSeqNb(),
			requireNonNull(notification.getAcct().getId().getIBAN()),
			Iso20022Util.from(notification.getCreDtTm())
		);

		// 'Statement Entry' (C-Level) (0..n)
		notification.getNtry().stream()
			.filter(e -> {
				if (e.isCreditingEntry() && e.isBookedEntry() && !e.isReversal()) {
					return true;
				}
				LOG.debug("Skipped Statement Entry with Credit Debit Indicator {}, Status {}, Reversal Indicator {}",
					e.getCdtDbtInd(), e.getSts(), e.isRvslInd());
				return false;
			})
			.map(reportEntry4 -> toBooking(reportEntry4, account))
			.forEach(c -> account.getBookings().add(c));

		return account;
	}

	@Nonnull
	private Booking toBooking(@Nonnull Entry reportEntry4, @Nonnull Account account) {
		Booking booking = new Booking(
			account,
			requireNonNull(Iso20022Util.from(reportEntry4.getBookgDt())),
			requireNonNull(Iso20022Util.from(reportEntry4.getValDt())),
			reportEntry4.getNtryRef()
		);

		reportEntry4.getNtryDtls().stream()
			.flatMap(entryDetails3 -> entryDetails3.getTxDtls().stream())
			.filter(this::isIsrTransaction)
			.map(entryTransaction4 -> toTransaction(entryTransaction4, booking))
			.forEach(transaction -> booking.getTransactions().add(transaction));

		return booking;
	}

	@Nonnull
	private IsrTransaction toTransaction(@Nonnull TransactionDetails entryTransaction4, @Nonnull Booking booking) {
		String referenceNumber = findIsrRemittanceInfo(requireNonNull(entryTransaction4.getRmtInf()))
			.map(StructuredRemittanceInformation::getCdtrRefInf)
			.map(CreditorReferenceInformation::getRef)
			.orElseThrow(() -> new IllegalStateException("This should have been checked earlier"));

		return new IsrTransaction(
			booking,
			entryTransaction4.getAmt().getCcy(),
			entryTransaction4.getAmt().getValue(),
			referenceNumber,
			toTransactionDetails(entryTransaction4.getRltdPties())
		);
	}

	@Nullable
	private TransactionInformationDTO toTransactionDetails(@Nullable RelatedParties transactionParties) {
		if (transactionParties == null) {
			return null;
		}

		TransactionInformationDTO transactionInformationDTO = new TransactionInformationDTO();

		Optional.ofNullable(transactionParties.getDbtr())
			.map(PartyOrDeptor::getParty)
			.ifPresent(party -> {
				String name = party.getName();
				if (name != null && !name.equalsIgnoreCase(INVALID_NAME)) {
					transactionInformationDTO.setDebitorName(name);
				}
				toDbtrPostalDetails(party.getPstlAdr(), transactionInformationDTO);
			});

		if (transactionParties.getDbtrAcct() != null) {
			transactionInformationDTO.setDebitorIBAN(transactionParties.getDbtrAcct().getId().getIBAN());
		}

		return transactionInformationDTO;
	}

	private void toDbtrPostalDetails(
		@Nullable PostalAddress postalAddress,
		@Nonnull TransactionInformationDTO transactionInformationDTO) {
		if (postalAddress == null) {
			return;
		}

		transactionInformationDTO.setDebitorBuildingNumber(postalAddress.getBldgNb());
		transactionInformationDTO.setDebitorStreetName(postalAddress.getStrtNm());
		transactionInformationDTO.setDebitorPostCode(postalAddress.getPstCd());
		transactionInformationDTO.setDebitorTownName(postalAddress.getTwnNm());
		transactionInformationDTO.setDebitorCountry(postalAddress.getCtry());
	}

	private boolean isIsrTransaction(@Nonnull TransactionDetails transaction) {
		if (transaction.getRmtInf() == null) {
			return false;
		}

		return findIsrRemittanceInfo(transaction.getRmtInf()).isPresent();
	}

	@Nonnull
	private Optional<StructuredRemittanceInformation> findIsrRemittanceInfo(@Nonnull RemittanceInformation info) {
		// bookings can only be assigned automatically to bills if the referenceId is present thus we return only these
		return info.getStrd().stream()
			.filter(this::hasReferenceNumber)
			.findAny();
	}

	private boolean hasReferenceNumber(@Nullable StructuredRemittanceInformation structuredRemittanceInformation9) {
		return Optional.ofNullable(structuredRemittanceInformation9)
			.map(StructuredRemittanceInformation::getCdtrRefInf)
			.map(CreditorReferenceInformation::getRef)
			.isPresent();
	}
}
