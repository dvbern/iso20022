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

package ch.dvbern.oss.lib.iso20022.pain001.v00109ch03;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import ch.dvbern.oss.lib.iso20022.Iso20022JaxbUtil;
import ch.dvbern.oss.lib.iso20022.Iso20022Util;
import ch.dvbern.oss.lib.iso20022.dtos.pain.AuszahlungDTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain001DTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain001SoftwareDTO;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import iso.std.iso._20022.tech.xsd.pain_001_001.AccountIdentification4Choice;
import iso.std.iso._20022.tech.xsd.pain_001_001.ActiveOrHistoricCurrencyAndAmount;
import iso.std.iso._20022.tech.xsd.pain_001_001.AmountType4Choice;
import iso.std.iso._20022.tech.xsd.pain_001_001.BranchAndFinancialInstitutionIdentification6;
import iso.std.iso._20022.tech.xsd.pain_001_001.CashAccount38;
import iso.std.iso._20022.tech.xsd.pain_001_001.ClearingSystemIdentification2Choice;
import iso.std.iso._20022.tech.xsd.pain_001_001.ClearingSystemMemberIdentification2;
import iso.std.iso._20022.tech.xsd.pain_001_001.CreditTransferTransaction34;
import iso.std.iso._20022.tech.xsd.pain_001_001.DateAndDateTime2Choice;
import iso.std.iso._20022.tech.xsd.pain_001_001.DocumentPain001Ch;
import iso.std.iso._20022.tech.xsd.pain_001_001.FinancialInstitutionIdentification18;
import iso.std.iso._20022.tech.xsd.pain_001_001.GroupHeader85;
import iso.std.iso._20022.tech.xsd.pain_001_001.ObjectFactory;
import iso.std.iso._20022.tech.xsd.pain_001_001.OtherContact1;
import iso.std.iso._20022.tech.xsd.pain_001_001.PartyIdentification135;
import iso.std.iso._20022.tech.xsd.pain_001_001.PaymentIdentification6;
import iso.std.iso._20022.tech.xsd.pain_001_001.PaymentInstruction30;
import iso.std.iso._20022.tech.xsd.pain_001_001.PaymentMethod3Code;
import iso.std.iso._20022.tech.xsd.pain_001_001.PostalAddress24;
import iso.std.iso._20022.tech.xsd.pain_001_001.RemittanceInformation16;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBElement;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.CCY;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * Service implementation to generate Payment-File Pain001 according to ISO20022 for a swiss bank
 */
@ApplicationScoped
public class Pain001V00109CH03Service implements Pain001Service {

	private static final PaymentMethod3Code PAYMENT_METHOD_3_CODE = PaymentMethod3Code.TRF;
	private static final Boolean BTCHBOOKG = true;
	private static final String CLRSYS_CD = "CHBCC"; // Code swiss bank
	private static final Pattern FIND_SPACES = Pattern.compile(SPACE);
	private static final Pattern NON_ASCII = Pattern.compile("[^\\p{ASCII}]");
	private static final int MAX_SIGNS = 35;
	private static final int MAX_70_TEXT = 70;

	@Override
	public byte[] getPainFileContent(@Nonnull Pain001DTO pain001DTO) {
		ObjectFactory objectFactory = new ObjectFactory();
		DocumentPain001Ch document = createPain001Document(objectFactory, pain001DTO);
		JAXBElement<DocumentPain001Ch> jaxbElement = objectFactory.createDocument(document);

		return Iso20022JaxbUtil.getXMLString(jaxbElement, objectFactory.getClass())
			.getBytes(StandardCharsets.UTF_8);
	}

	@Nonnull
	private DocumentPain001Ch createPain001Document(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull Pain001DTO pain001DTO
	) {
		String debtorName = pain001DTO.getSchuldnerName();
		String debtorBic = pain001DTO.getSchuldnerBIC();
		String debtorIban = pain001DTO.getSchuldnerIBAN();

		if (debtorName == null) {
			throw new Iso20022RuntimeException("Empty debtor name: debtor_name is required");
		}
		if (debtorBic == null) {
			throw new Iso20022RuntimeException("Empty debtor Bank BIC Number: debtor_bic is required");
		}
		if (debtorIban == null) {
			throw new Iso20022RuntimeException("Empty IBAN: debtor_iban is required");
		}

		DocumentPain001Ch document = objectFactory.createDocumentPain001Ch();
		document.setCstmrCdtTrfInitn(objectFactory.createCustomerCreditTransferInitiationV09());

		PaymentInstruction30 paymentInstruction30 = createPaymentInstruction30(objectFactory, pain001DTO);

		document.getCstmrCdtTrfInitn().getPmtInf().add(paymentInstruction30);

		paymentInstruction30.getCdtTrfTxInf().clear();

		int transaktion = 0;
		BigDecimal ctrlSum = BigDecimal.ZERO;
		for (AuszahlungDTO auszahlungDTO : pain001DTO.getAuszahlungen()) {
			requireNonNull(auszahlungDTO.getBetragTotalZahlung(), "Amount is required");
			transaktion++;

			ctrlSum = ctrlSum.add(auszahlungDTO.getBetragTotalZahlung());

			CreditTransferTransaction34 info = createCreditTransferTransaction34(
				objectFactory,
				transaktion,
				auszahlungDTO,
				requireNonNull(pain001DTO.getAuszahlungsDatum()));

			paymentInstruction30.getCdtTrfTxInf().add(info);
		}

		GroupHeader85 groupHeader = createGroupHeader(objectFactory, pain001DTO, transaktion, ctrlSum);
		document.getCstmrCdtTrfInitn().setGrpHdr(groupHeader);

		return document;
	}

	@Nonnull
	private CreditTransferTransaction34 createCreditTransferTransaction34(
		@Nonnull ObjectFactory objectFactory,
		int transaktion,
		@Nonnull AuszahlungDTO auszahlungDTO,
		@Nonnull LocalDate date
	) {
		CreditTransferTransaction34 cTT34 = objectFactory.createCreditTransferTransaction34();
		cTT34.setPmtId(createPaymentIdentification(objectFactory, transaktion, auszahlungDTO, date));
		cTT34.setAmt(createAmount(objectFactory, auszahlungDTO));
		cTT34.setCdtrAgt(createCreditorAgentAccount(objectFactory, auszahlungDTO));
		cTT34.setCdtrAcct(createCreditorAccount(objectFactory, auszahlungDTO));
		cTT34.setCdtr(createCreditor(objectFactory, auszahlungDTO));
		cTT34.setRmtInf(createPaymentReference(objectFactory, auszahlungDTO, date));

		return cTT34;
	}

	@Nonnull
	private RemittanceInformation16 createPaymentReference(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull AuszahlungDTO auszahlungDTO,
		@Nonnull LocalDate date
	) {
		RemittanceInformation16 rmtInf = objectFactory.createRemittanceInformation16();

		if (auszahlungDTO.getZahlungText() == null) {
			String monat = date.format(DateTimeFormatter.ofPattern("MMM.yyyy", Locale.GERMAN));
			String zahlungsempfaengerName = requireNonNull(auszahlungDTO.getZahlungsempfaengerName());
			String ustrd = normalize(zahlungsempfaengerName + ", Monat " + monat);
			rmtInf.getUstrd().add(ustrd);    // 2.99
		} else {
			rmtInf.getUstrd().add(normalize(auszahlungDTO.getZahlungText()));
		}

		return rmtInf;
	}

	@Nonnull
	private PartyIdentification135 createCreditor(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull AuszahlungDTO auszahlungDTO
	) {
		String zahlungsempfaengerName = requireNonNull(auszahlungDTO.getZahlungsempfaengerName());
		String normalizedName = normalize(StringUtils.abbreviate(zahlungsempfaengerName, MAX_70_TEXT));

		PartyIdentification135 cdtr = objectFactory.createPartyIdentification135();
		cdtr.setNm(normalizedName); // 2.79
		createPstlAdr(objectFactory, auszahlungDTO)
			.ifPresent(cdtr::setPstlAdr);

		return cdtr;
	}

	@Nonnull
	private CashAccount38 createCreditorAccount(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull AuszahlungDTO auszahlungDTO
	) {
		String ibanRaw = requireNonNull(auszahlungDTO.getZahlungsempfaengerIBAN(), "IBAN is required");
		String iban = FIND_SPACES.matcher(ibanRaw).replaceAll(EMPTY);

		AccountIdentification4Choice accountIdentification4Choice = objectFactory.createAccountIdentification4Choice();
		accountIdentification4Choice.setIBAN(iban); // 2.80

		CashAccount38 cdtrAcct = objectFactory.createCashAccount38();
		cdtrAcct.setId(accountIdentification4Choice); // 2.80

		return cdtrAcct;
	}

	@Nonnull
	private BranchAndFinancialInstitutionIdentification6 createCreditorAgentAccount(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull AuszahlungDTO auszahlungDTO
	) {
		FinancialInstitutionIdentification18 finInstnId = objectFactory.createFinancialInstitutionIdentification18();

		if (auszahlungDTO.getZahlungsempfaengerBIC() != null) {
			// BIC
			finInstnId.setBICFI(auszahlungDTO.getZahlungsempfaengerBIC());
		} else {
			ClearingSystemIdentification2Choice clrSysId = objectFactory.createClearingSystemIdentification2Choice();
			// CH bank clearing number
			clrSysId.setCd(CLRSYS_CD);

			String mmbId =
				requireNonNull(auszahlungDTO.getZahlungsempfaengerBankClearingNumber(), "Clearing number is required");

			ClearingSystemMemberIdentification2 clrSysMmbId =
				objectFactory.createClearingSystemMemberIdentification2();
			clrSysMmbId.setMmbId(mmbId);
			clrSysMmbId.setClrSysId(clrSysId);

			finInstnId.setClrSysMmbId(clrSysMmbId);
		}

		BranchAndFinancialInstitutionIdentification6 cdtrAgt =
			objectFactory.createBranchAndFinancialInstitutionIdentification6();
		cdtrAgt.setFinInstnId(finInstnId);

		return cdtrAgt;
	}

	@Nonnull
	private AmountType4Choice createAmount(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull AuszahlungDTO auszahlungDTO
	) {
		BigDecimal amount = requireNonNull(auszahlungDTO.getBetragTotalZahlung(), "Amount is required");

		ActiveOrHistoricCurrencyAndAmount instdAmt = objectFactory.createActiveOrHistoricCurrencyAndAmount();
		instdAmt.setCcy(CCY);// 2.43
		instdAmt.setValue(amount);// 2.43

		AmountType4Choice amt = objectFactory.createAmountType4Choice();
		amt.setInstdAmt(instdAmt);

		return amt;
	}

	@Nonnull
	private PaymentIdentification6 createPaymentIdentification(
		@Nonnull ObjectFactory objectFactory,
		int transaktion,
		@Nonnull AuszahlungDTO auszahlungDTO,
		@Nonnull LocalDate date
	) {
		PaymentIdentification6 pmtId = objectFactory.createPaymentIdentification6();

		String transaktionStr = String.valueOf(transaktion);
		pmtId.setInstrId(Iso20022Util.replaceSwift(transaktionStr)); // 2.29 // SWIFT
		pmtId.setEndToEndId(endToEndId(auszahlungDTO, date, transaktionStr));

		return pmtId;
	}

	@Nonnull
	private String endToEndId(
		@Nonnull AuszahlungDTO auszahlungDTO,
		@Nonnull LocalDate date,
		@Nonnull String transaktionStr
	) {
		String endToEndId;
		if (auszahlungDTO.getEndToEndId() == null) {
			// "{id}/{month number}/{normalized without umlauts (öäü)} => "1/2/Brunnen
			endToEndId =
				transaktionStr + '/' + date.getMonthValue() + '/' + auszahlungDTO.getZahlungsempfaengerName(); // SWIFT
		} else {
			endToEndId = auszahlungDTO.getEndToEndId();
		}
		endToEndId = Iso20022Util.replaceSwift(endToEndId);

		return endToEndId.substring(0, Math.min(endToEndId.length(), MAX_SIGNS)); // 2.30 max 35 signs
	}

	@Nonnull
	private Optional<PostalAddress24> createPstlAdr(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull AuszahlungDTO auszahlungDTO
	) {
		if (!hasAddressData(auszahlungDTO)) {
			return Optional.empty();
		}

		PostalAddress24 pstlAdr = objectFactory.createPostalAddress24();
		if (auszahlungDTO.getZahlungsempfaengerStrasse() != null) {
			pstlAdr.setStrtNm(normalize(auszahlungDTO.getZahlungsempfaengerStrasse())); // 2.79
		}
		if (auszahlungDTO.getZahlungsempfaengerHausnummer() != null) {
			pstlAdr.setBldgNb(auszahlungDTO.getZahlungsempfaengerHausnummer()); // 2.79
		}
		if (auszahlungDTO.getZahlungsempfaengerPlz() != null) {
			pstlAdr.setPstCd(auszahlungDTO.getZahlungsempfaengerPlz());// 2.79
		}
		if (auszahlungDTO.getZahlungsempfaengerOrt() != null) {
			pstlAdr.setTwnNm(normalize(auszahlungDTO.getZahlungsempfaengerOrt()));// 2.79
		}
		if (auszahlungDTO.getZahlungsempfaengerLand() != null) {
			pstlAdr.setCtry(auszahlungDTO.getZahlungsempfaengerLand());// 2.79
		}

		return Optional.of(pstlAdr);
	}

	@SuppressWarnings("checkstyle:BooleanExpressionComplexity")
	private boolean hasAddressData(@Nonnull AuszahlungDTO auszahlungDTO) {
		return auszahlungDTO.getZahlungsempfaengerStrasse() != null
			|| auszahlungDTO.getZahlungsempfaengerHausnummer() != null
			|| auszahlungDTO.getZahlungsempfaengerPlz() != null
			|| auszahlungDTO.getZahlungsempfaengerOrt() != null
			|| auszahlungDTO.getZahlungsempfaengerLand() != null;
	}

	@Contract("!null->!null; null->null;")
	@Nullable
	private String normalize(@Nullable String text) {
		if (null == text) {
			return null;
		}

		return NON_ASCII.matcher(Normalizer.normalize(text, Form.NFD)).replaceAll(EMPTY);
	}

	@Nonnull
	private PaymentInstruction30 createPaymentInstruction30(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull Pain001DTO pain001DTO
	) {
		PaymentInstruction30 paymentInstruction30 = objectFactory.createPaymentInstruction30();

		String paymentInfoId = Optional.ofNullable(pain001DTO.getPmtInfId())
			.orElse(pain001DTO.getMsgId());
		paymentInstruction30.setPmtInfId(Iso20022Util.replaceSwift(paymentInfoId)); // SWIFT

		paymentInstruction30.setPmtMtd(PAYMENT_METHOD_3_CODE);

		paymentInstruction30.setBtchBookg(BTCHBOOKG);

		if (pain001DTO.getAuszahlungsDatum() != null) {
			DateAndDateTime2Choice requestExecutionDate = new DateAndDateTime2Choice();
			requestExecutionDate.setDt(pain001DTO.getAuszahlungsDatum());
			paymentInstruction30.setReqdExctnDt(requestExecutionDate);
		}

		// Debtor name
		String name = StringUtils.abbreviate(requireNonNull(pain001DTO.getSchuldnerName()), MAX_70_TEXT);
		paymentInstruction30.setDbtr(objectFactory.createPartyIdentification135());
		paymentInstruction30.getDbtr().setNm(name);

		// Debtor Iban
		paymentInstruction30.setDbtrAcct(objectFactory.createCashAccount38());
		paymentInstruction30.getDbtrAcct().setId(objectFactory.createAccountIdentification4Choice());
		paymentInstruction30.getDbtrAcct().getId().setIBAN(requireNonNull(pain001DTO.getSchuldnerIBAN()));

		// Debtor BIC
		paymentInstruction30.setDbtrAgt(objectFactory.createBranchAndFinancialInstitutionIdentification6());
		FinancialInstitutionIdentification18 finInstnId = objectFactory.createFinancialInstitutionIdentification18();
		paymentInstruction30.getDbtrAgt().setFinInstnId(finInstnId);
		finInstnId.setBICFI(requireNonNull(pain001DTO.getSchuldnerBIC()));

		// Debtor charge Iban: Wird nur verwendet, wenn es tatsaechlich eine separate SchuldnerIBAN hat
		String debtorIbanGebuehren = pain001DTO.getSchuldnerIBANGebuehren();
		if (StringUtils.isNotEmpty(debtorIbanGebuehren)) {
			paymentInstruction30.setChrgsAcct(objectFactory.createCashAccount38());
			paymentInstruction30.getChrgsAcct().setId(objectFactory.createAccountIdentification4Choice());
			paymentInstruction30.getChrgsAcct().getId().setIBAN(debtorIbanGebuehren);
		}

		return paymentInstruction30;
	}

	@Nonnull
	private GroupHeader85 createGroupHeader(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull Pain001DTO pain001DTO,
		int transaktion,
		@Nonnull BigDecimal ctrlSum
	) {
		GroupHeader85 groupHeader85 = objectFactory.createGroupHeader85();
		groupHeader85.setInitgPty(createInitiatingParty(objectFactory, pain001DTO));
		groupHeader85.setMsgId(Iso20022Util.replaceSwift(pain001DTO.getMsgId())); // 1.1 // SWIFT
		groupHeader85.setNbOfTxs(Integer.toString(transaktion)); // 1.6
		groupHeader85.setCtrlSum(ctrlSum); // 1.7
		LocalDateTime creationTime = requireNonNull(pain001DTO.getGenerierungsDatum());
		groupHeader85.setCreDtTm(Iso20022Util.toXmlGregorianCalendar(creationTime)); // 1.2

		return groupHeader85;
	}

	@Nonnull
	private PartyIdentification135 createInitiatingParty(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull Pain001DTO pain001DTO
	) {
		PartyIdentification135 initgPty = objectFactory.createPartyIdentification135();
		initgPty.setCtctDtls(objectFactory.createContact4());
		initgPty.setNm(requireNonNull(pain001DTO.getSchuldnerName())); // 1.8

		streamSoftwareDetails(pain001DTO)
			.map(detail -> createOtherContact(objectFactory, detail))
			.forEach(initgPty.getCtctDtls().getOthr()::add); // 3.9

		return initgPty;
	}

	@Nonnull
	private Stream<SoftwareDetail> streamSoftwareDetails(@Nonnull Pain001DTO pain001DTO) {
		Pain001SoftwareDTO softwareDetails = pain001DTO.getSoftwareDetails();
		if (softwareDetails == null) {
			return Stream.empty();
		}

		return Stream.of(
				new SoftwareDetail("NAME", softwareDetails.productName()),
				new SoftwareDetail("PRVD", softwareDetails.manufacturerName()),
				new SoftwareDetail("VRSN", softwareDetails.softwareVersion()),
				new SoftwareDetail("SPSV", softwareDetails.spsIgVersion())
			)
			.filter(detail -> StringUtils.isNotBlank(detail.value()));
	}

	private record SoftwareDetail(@Nonnull String channelType, @Nullable String value) {
	}

	@Nonnull
	private OtherContact1 createOtherContact(
		@Nonnull ObjectFactory objectFactory,
		@Nonnull SoftwareDetail detail
	) {
		OtherContact1 contact = objectFactory.createOtherContact1();
		contact.setChanlTp(detail.channelType());
		contact.setId(requireNonNull(detail.value()));

		return contact;
	}
}
