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

package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import ch.dvbern.oss.lib.iso20022.Iso20022Util;
import ch.dvbern.oss.lib.iso20022.camt.AbstractCamtService;
import ch.dvbern.oss.lib.iso20022.camt.CamtRejectCodes;
import ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion;
import ch.dvbern.oss.lib.iso20022.camt.dtos.Account;
import ch.dvbern.oss.lib.iso20022.camt.dtos.Booking;
import ch.dvbern.oss.lib.iso20022.camt.dtos.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.camt.dtos.MessageIdentifier;
import ch.dvbern.oss.lib.iso20022.camt.dtos.IsrTransaction;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import iso.std.iso._20022.tech.xsd.camt_054_001.AccountNotification7;
import iso.std.iso._20022.tech.xsd.camt_054_001.BankToCustomerDebitCreditNotificationV04;
import iso.std.iso._20022.tech.xsd.camt_054_001.CreditDebitCode;
import iso.std.iso._20022.tech.xsd.camt_054_001.CreditorReferenceInformation2;
import iso.std.iso._20022.tech.xsd.camt_054_001.Document;
import iso.std.iso._20022.tech.xsd.camt_054_001.EntryStatus2Code;
import iso.std.iso._20022.tech.xsd.camt_054_001.EntryTransaction4;
import iso.std.iso._20022.tech.xsd.camt_054_001.GroupHeader58;
import iso.std.iso._20022.tech.xsd.camt_054_001.Pagination;
import iso.std.iso._20022.tech.xsd.camt_054_001.RemittanceInformation7;
import iso.std.iso._20022.tech.xsd.camt_054_001.ReportEntry4;
import iso.std.iso._20022.tech.xsd.camt_054_001.StructuredRemittanceInformation9;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.dvbern.oss.lib.iso20022.camt.CamtValidateUtility.isMatchingXsdSchema;

/**
 * This Service reads an xml document complying to camt054 version 00104 standard and returns parts or the whole
 * document
 */
@Stateless
@Local(Camt054Service.class)
public class Camt054V00104Service extends AbstractCamtService implements Camt054Service {

	private static final Logger LOG = LoggerFactory.getLogger(Camt054V00104Service.class);
	public static final String PATH_CAMT_054_001_04_XSD =
		"iso/std/iso/20022/tech/xsd/camt_054_001_04/camt.054.001.04.xsd";

	/**
	 * Returns the Notification part of the xml input as Java object
	 *
	 * @param xmlAsBytes xml content
	 * @return object of type BankToCustomerDebitCreditNotificationV04
	 */
	@Nonnull
	@Override
	public BankToCustomerDebitCreditNotificationV04 getNotificationFromXml(@Nonnull byte[] xmlAsBytes) {

		if (!isMatchingXsdSchema(xmlAsBytes, PATH_CAMT_054_001_04_XSD)) {
			throw new Iso20022RuntimeException("SAX Exception while validating XML File");
		}

		BankToCustomerDebitCreditNotificationV04 notification = unmarshallNotificationFromXml(xmlAsBytes);

		return notification;
	}

	@Nonnull
	private BankToCustomerDebitCreditNotificationV04 unmarshallNotificationFromXml(byte[] xmlAsBytes) {

		try (ByteArrayInputStream xmlAsStream = new ByteArrayInputStream(xmlAsBytes)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StreamSource streamSource = new StreamSource(xmlAsStream);
			JAXBElement<Document> doc = jaxbUnmarshaller.unmarshal(streamSource, Document.class);

			return doc.getValue().getBkToCstmrDbtCdtNtfctn();
		} catch (JAXBException e) {
			throw new Iso20022RuntimeException("Document can not be parsed by JAXB", e);
		} catch (IOException e) {
			throw new Iso20022RuntimeException("IO Exception while unmarshalling notification from xml", e);
		}
	}

	/**
	 * Reads an xml file and returns data about booked credit notes with reference number (not reversals)
	 *
	 * @param xmlAsBytes xml content
	 * @return object of type DocumentDTO containing data about booked ESR notifications
	 */
	@Nonnull
	@Override
	public DocumentDTO getDocumentWithBookedEsrPaymentsFromXml(@Nonnull byte[] xmlAsBytes)
		throws Iso20022RuntimeException {

		if (!isMatchingXsdSchema(xmlAsBytes, PATH_CAMT_054_001_04_XSD)) {
			throw new Iso20022RuntimeException("SAX Exception while validating XML File");
		}

		BankToCustomerDebitCreditNotificationV04 notification = unmarshallNotificationFromXml(xmlAsBytes);

		MessageIdentifier messageIdentifier = getMessageIdentifier(notification.getGrpHdr());
		DocumentDTO documentDTO = new DocumentDTO(CamtTypeVersion.CAMT054V00104, messageIdentifier);

		List<AccountNotification7> notification7List = notification.getNtfctn();

		if (notification7List.size() > 1) {
			// According to the swiss implementation guideline there should be only one notification per document
			LOG.warn("More than one Account Statement present in {}", documentDTO);
		}

		notification7List.stream()
			.map(this::getAccount)
			.forEach(dto -> documentDTO.getAccounts().add(dto));

		return documentDTO;
	}

	@Nonnull
	private MessageIdentifier getMessageIdentifier(@Nonnull GroupHeader58 grpHdr) {
		Pagination msgPgntn = grpHdr.getMsgPgntn();

		return new MessageIdentifier(
			grpHdr.getMsgId(),
			Iso20022Util.from(grpHdr.getCreDtTm()),
			msgPgntn.getPgNb(),
			msgPgntn.isLastPgInd()
		);
	}

	@Nonnull
	private Account getAccount(@Nonnull AccountNotification7 notification) {
		Account bAccountStatementDTO = new Account(
			notification.getId(),
			notification.getElctrncSeqNb(),
			notification.getAcct().getId().getIBAN(),
			Iso20022Util.from(notification.getCreDtTm())
		);

		// 'Statement Entry' (C-Level) (0..n)
		notification.getNtry().stream()
			.filter(e -> {
				if (isCreditingEntry(e) && isBookedEntry(e) && !isReversal(e)) {
					return true;
				}
				LOG.debug("Skipped Statement Entry with Credit Debit Indicator {}, Status {}, Reversal Indicator {}",
					e.getCdtDbtInd(), e.getSts(), e.isRvslInd());
				return false;
			})
			.map(this::getBooking)
			.forEach(c -> bAccountStatementDTO.getBookings().add(c));

		return bAccountStatementDTO;
	}

	private boolean isCreditingEntry(@Nonnull ReportEntry4 entry) {
		return entry.getCdtDbtInd() != null && CreditDebitCode.CRDT == entry.getCdtDbtInd();
	}

	private boolean isBookedEntry(@Nonnull ReportEntry4 entry) {
		return entry.getSts() != null && EntryStatus2Code.BOOK == entry.getSts();
	}

	private boolean isReversal(@Nonnull ReportEntry4 entry) {
		return entry.isRvslInd() != null && entry.isRvslInd();
	}

	@Nonnull
	private Booking getBooking(@Nonnull ReportEntry4 reportEntry4) {
		Booking booking = new Booking(
			Objects.requireNonNull(Iso20022Util.from(reportEntry4.getBookgDt())),
			Objects.requireNonNull(Iso20022Util.from(reportEntry4.getValDt())),
			reportEntry4.getNtryRef()
		);

		reportEntry4.getNtryDtls().stream()
			.flatMap(entryDetails3 -> entryDetails3.getTxDtls().stream())
			.filter(this::isIsrTransaction)
			.map(this::toDTransactionDetailsDTO)
			.forEach(transaction -> booking.getTransactions().add(transaction));

		return booking;
	}

	@Nonnull
	private IsrTransaction toDTransactionDetailsDTO(@Nonnull EntryTransaction4 entryTransaction4) {
		String referenceNumber = getIsrRemittanceInfo(entryTransaction4.getRmtInf())
			.orElseThrow(() -> new IllegalStateException("This should have been checked earlier"))
			.getCdtrRefInf()
			.getRef();

		return new IsrTransaction(
			entryTransaction4.getAmt().getCcy(),
			entryTransaction4.getAmt().getValue(),
			referenceNumber
		);
	}

	private boolean isIsrTransaction(@Nonnull EntryTransaction4 transaction) {
		if (transaction.getRmtInf() == null) {
			return false;
		}

		return getIsrRemittanceInfo(transaction.getRmtInf()).isPresent();
	}

	@Nonnull
	private Optional<StructuredRemittanceInformation9> getIsrRemittanceInfo(@Nonnull RemittanceInformation7 info) {
		// bookings can only be assigned automatically to bills if the referenceId is present thus we return only these
		return info.getStrd().stream()
			.filter(this::hasReferenceNumber)
			.filter(this::isNotRejected)
			.findAny();
	}

	private boolean hasReferenceNumber(@Nullable StructuredRemittanceInformation9 structuredRemittanceInformation9) {
		return Optional.ofNullable(structuredRemittanceInformation9)
			.map(StructuredRemittanceInformation9::getCdtrRefInf)
			.map(CreditorReferenceInformation2::getRef)
			.isPresent();
	}

	private boolean isNotRejected(@Nonnull StructuredRemittanceInformation9 structuredRemittanceInformation9) {
		if (structuredRemittanceInformation9.getAddtlRmtInf() == null) {
			return true;
		}

		return structuredRemittanceInformation9.getAddtlRmtInf().stream()
			.noneMatch(this::isRejectCode);
	}

	private boolean isRejectCode(@Nonnull String value) {
		return CamtRejectCodes.MASS_REJECT.getCode().equals(value) || CamtRejectCodes.REJECT.getCode().equals(value);
	}
}
