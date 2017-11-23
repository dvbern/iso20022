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

package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import ch.dvbern.oss.lib.iso20022.camt.AbstractCamtService;
import ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion;
import ch.dvbern.oss.lib.iso20022.camt.dtos.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.camt.dtos.MessageIdentifier;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import iso.std.iso._20022.tech.xsd.camt_053_001.AccountStatement4;
import iso.std.iso._20022.tech.xsd.camt_053_001.BankToCustomerStatementV04;
import iso.std.iso._20022.tech.xsd.camt_053_001.Document;
import iso.std.iso._20022.tech.xsd.camt_053_001.GroupHeader58;
import iso.std.iso._20022.tech.xsd.camt_053_001.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.dvbern.oss.lib.iso20022.camt.CamtValidateUtility.isMatchingXsdSchema;

/**
 * This Service reads an xml document complying to camt054 version 00104 standard and returns parts or the whole
 * document
 */
@Stateless
@Local(Camt053Service.class)
public class Camt053V00104Service extends AbstractCamtService implements Camt053Service {

	private static final Logger LOG = LoggerFactory.getLogger(Camt053V00104Service.class);
	public static final String PATH_CAMT_053_001_04_XSD =
		"iso/std/iso/20022/tech/xsd/camt_053_001/camt.053.001.04.xsd";

	/**
	 * Returns the Notification part of the xml input as Java object
	 *
	 * @param xmlAsBytes xml content
	 * @return object of type BankToCustomerDebitCreditNotificationV04
	 */
	@Nonnull
	@Override
	public BankToCustomerStatementV04 getNotificationFromXml(@Nonnull byte[] xmlAsBytes) {

		if (!isMatchingXsdSchema(xmlAsBytes, PATH_CAMT_053_001_04_XSD)) {
			throw new Iso20022RuntimeException("SAX Exception while validating XML File");
		}

		BankToCustomerStatementV04 notification = unmarshallNotificationFromXml(xmlAsBytes);

		return notification;
	}

	@Nonnull
	private BankToCustomerStatementV04 unmarshallNotificationFromXml(byte[] xmlAsBytes) {

		try (ByteArrayInputStream xmlAsStream = new ByteArrayInputStream(xmlAsBytes)) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StreamSource streamSource = new StreamSource(xmlAsStream);
			JAXBElement<Document> doc = jaxbUnmarshaller.unmarshal(streamSource, Document.class);

			return doc.getValue().getBkToCstmrStmt();
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

		if (!isMatchingXsdSchema(xmlAsBytes, PATH_CAMT_053_001_04_XSD)) {
			throw new Iso20022RuntimeException("SAX Exception while validating XML File");
		}

		BankToCustomerStatementV04 notification = unmarshallNotificationFromXml(xmlAsBytes);

		MessageIdentifier messageIdentifier = getaGroupHeaderDTO(notification);
		DocumentDTO documentDTO = new DocumentDTO(CamtTypeVersion.CAMT053V00104, messageIdentifier);

		List<AccountStatement4> accountStatement4s = notification.getStmt();
		if (accountStatement4s.size() > 1) {
			// According to the swiss implementation guideline there should be only one notification per document
			LOG.warn("More than one Account Statement present in {}", documentDTO);
		}

		//		accountStatement4s.stream()
		//			.map(this::getbAccountStatementDTO)
		//			.forEach(dto -> documentDTO.getAccounts().add(dto));

		return documentDTO;
	}

	private MessageIdentifier getaGroupHeaderDTO(BankToCustomerStatementV04 notification) {
		GroupHeader58 grpHdr = notification.getGrpHdr();
		Pagination pagination = grpHdr.getMsgPgntn();

		MessageIdentifier aGroupHeaderDTO = new MessageIdentifier(
			grpHdr.getMsgId(),
			grpHdr.getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime(),
			pagination.getPgNb(),
			pagination.isLastPgInd()
		);

		return aGroupHeaderDTO;
	}

	/*
	private BAccountStatementDTO getbAccountStatementDTO(AccountStatement4 accountStatement4) {
		BAccountStatementDTO bAccountStatementDTO = new BAccountStatementDTO();
		bAccountStatementDTO.setIdentification(accountStatement4.getId());
		bAccountStatementDTO.setAccountIdentificationIBAN(accountStatement4.getAcct().getId().getIBAN());
		XMLGregorianCalendar creationDateTime = accountStatement4.getCreDtTm();
		bAccountStatementDTO.setCreationDateTime(creationDateTime.toGregorianCalendar().toZonedDateTime()
			.toLocalDateTime());

		// 'Statement Entry' (C-Level) (0..n)
		final List<ReportEntry4> entries = accountStatement4.getNtry();
		for (ReportEntry4 reportEntry4 : entries) {
			if (reportEntry4.getCdtDbtInd() != null && CreditDebitCode.CRDT == reportEntry4.getCdtDbtInd()
				&& reportEntry4.getSts() != null && EntryStatus2Code.BOOK == reportEntry4.getSts()
				&& (reportEntry4.isRvslInd() == null || Boolean.FALSE.equals(reportEntry4.isRvslInd()))) {
				CStatementEntryDTO cEntryDTO = getcStatementEntryDTO(reportEntry4);
				bAccountStatementDTO.getcStatementEntryDTOS().add(cEntryDTO);
			} else {
				LOG.warn("Skipped Statement Entry with Credit Debit Indicator {}, Status {}, Reversal Indicator {}",
					reportEntry4.getCdtDbtInd(), reportEntry4.getSts(), reportEntry4.isRvslInd());
			}
		}
		return bAccountStatementDTO;
	}

	private CStatementEntryDTO getcStatementEntryDTO(@Nonnull ReportEntry4 reportEntry4) {
		CStatementEntryDTO cEntryDTO = new CStatementEntryDTO();
		// mandatory items
		cEntryDTO.setAmountCurrency(reportEntry4.getAmt().getCcy());
		cEntryDTO.setAmount(reportEntry4.getAmt().getValue().toString());
		cEntryDTO.setCreditDebitIndicator(reportEntry4.getCdtDbtInd().value());
		cEntryDTO.setStatus(reportEntry4.getSts().value());
		// Following the xsd schema one of the following must be present for an Entry : BookgDt, ValDt, AcctSvcrRef,
		// BkTxCd, Avlbty (not specified in the CH version), more BkTxCd must be present (but Domn could be absent)
		if (reportEntry4.getBkTxCd().getDomn() != null) {
			cEntryDTO.setBankTransactionCodeDomainCode(reportEntry4.getBkTxCd().getDomn().getCd());
			cEntryDTO.setBankTransactionCodeDomainFamilyCode(reportEntry4.getBkTxCd().getDomn().getFmly().getCd());
			cEntryDTO.setBankTransactionCodeDomainFamilySubFamilyCode(reportEntry4.getBkTxCd().getDomn()
				.getFmly().getSubFmlyCd());
		}

		// optional
		if (reportEntry4.getBookgDt() != null && reportEntry4.getBookgDt().getDt() != null) {
			cEntryDTO.setBookingDate(reportEntry4.getBookgDt().getDt().toGregorianCalendar().toZonedDateTime()
				.toLocalDate());
		} else if (reportEntry4.getBookgDt() != null && reportEntry4.getBookgDt().getDtTm() != null) {
			cEntryDTO.setBookingDate(reportEntry4.getBookgDt().getDtTm().toGregorianCalendar().toZonedDateTime()
				.toLocalDate());
		}
		// Swiss ISO 20022 Payments Standard: Always used. Type3: Credit date. Type4: Credit date.
		if (reportEntry4.getValDt() != null && reportEntry4.getValDt().getDt() != null) {
			cEntryDTO.setValueDate(reportEntry4.getValDt()
				.getDt()
				.toGregorianCalendar()
				.toZonedDateTime()
				.toLocalDate());
		} else if (reportEntry4.getValDt() != null && reportEntry4.getValDt().getDtTm() != null) {
			cEntryDTO.setValueDate(reportEntry4.getValDt().getDtTm().toGregorianCalendar().toZonedDateTime()
				.toLocalDate());
		}
		cEntryDTO.setEntryReference(reportEntry4.getNtryRef()); // ISR participant number in the format 010001628

		// 'Entry Details' (NtryDtls, D-Level) (0..n)
		final List<EntryDetails3> ntryDtls = reportEntry4.getNtryDtls();
		for (EntryDetails3 entryDetails3 : ntryDtls) {
			final DEntryDetailsDTO dEntryDetailsDTO = getdEntryDetailsDTO(entryDetails3);
			cEntryDTO.getdEntryDetailsDTOS().add(dEntryDetailsDTO);
		}
		return cEntryDTO;
	}

	private DEntryDetailsDTO getdEntryDetailsDTO(EntryDetails3 entryDetails3) {
		final DEntryDetailsDTO dEntryDetailsDTO = new DEntryDetailsDTO();
		List<DTransactionDetailsDTO> dTransactionDetailsDTOS = new ArrayList<>();
		// multiple TransactionDetails e.g. for 'Sammelbuchungen'
		for (EntryTransaction4 entryTransaction4 : entryDetails3.getTxDtls()) {
			DTransactionDetailsDTO dTransactionDetailsDTO = new DTransactionDetailsDTO();
			// mandatory items
			dTransactionDetailsDTO.setAmountCurrency(entryTransaction4.getAmt().getCcy());
			dTransactionDetailsDTO.setAmount(entryTransaction4.getAmt().getValue());
			dTransactionDetailsDTO.setCreditDebitIndicator(entryTransaction4.getCdtDbtInd().value());
			// optional items
			final TransactionParties3 rltdPties = entryTransaction4.getRltdPties();
			if (rltdPties != null && rltdPties.getDbtr() != null && rltdPties.getDbtr().getNm() != null) {
				dTransactionDetailsDTO.setRelatedPartiesDebitorName(rltdPties.getDbtr().getNm());
			}
			final RemittanceInformation7 rmtInf1 = entryTransaction4.getRmtInf();
			if (rmtInf1 != null && rmtInf1.getStrd() != null) {
				final List<StructuredRemittanceInformation9> strd = rmtInf1.getStrd();
				// bookings can only be assigned automatically to bills if the referenceId is present thus we return
				// only these
				Optional<StructuredRemittanceInformation9> structuredRmtInf9WithReferenceId = strd.stream()
					.filter(this::hasReferenceNumber)
					.filter(this::isNotRejected)
					.findAny();
				if (structuredRmtInf9WithReferenceId.isPresent()) {
					dTransactionDetailsDTO.setRemittanceCreditorReferenceInformation(structuredRmtInf9WithReferenceId
						.get()
						.getCdtrRefInf()
						.getRef());
					dTransactionDetailsDTOS.add(dTransactionDetailsDTO);
				} else {
					LOG.warn("No Reference Id present in transaction detail with {}.", dTransactionDetailsDTO
						.toString());
				}
			} else {
				LOG.warn(
					"No Remittance Information (hence no Reference Id) present in transaction detail with {}.",
					dTransactionDetailsDTO.toString());
			}
			if (entryTransaction4.getRltdDts() != null && entryTransaction4.getRltdDts().getAccptncDtTm() != null) {
				dTransactionDetailsDTO.setAcceptanceDateTime(entryTransaction4.getRltdDts().getAccptncDtTm()
					.toGregorianCalendar().toZonedDateTime().toLocalDate());
			}
		}
		dEntryDetailsDTO.setTransactionDetailsDTOS(dTransactionDetailsDTOS);
		return dEntryDetailsDTO;
	}

	private boolean hasReferenceNumber(StructuredRemittanceInformation9 structuredRemittanceInformation9) {
		return Optional.ofNullable(structuredRemittanceInformation9)
			.map(StructuredRemittanceInformation9::getCdtrRefInf)
			.map(CreditorReferenceInformation2::getRef)
			.isPresent();
	}

	private boolean isNotRejected(StructuredRemittanceInformation9 structuredRemittanceInformation9) {
		if (structuredRemittanceInformation9.getAddtlRmtInf() == null) {
			return true;
		}

		return structuredRemittanceInformation9.getAddtlRmtInf().stream().noneMatch(this::isRejectCode);
	}

	private boolean isRejectCode(String value) {
		return CamtRejectCodes.MASS_REJECT.getCode().equals(value) || CamtRejectCodes.REJECT.getCode().equals(value);
	}
	*/
}
