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

package ch.dvbern.oss.lib.iso20022.pain001.v00103ch02;

import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.XMLConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import ch.dvbern.oss.lib.iso20022.Iso20022Util;
import ch.dvbern.oss.lib.iso20022.PainJaxbUtil;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.ClearingSystemIdentification2Choice;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.ClearingSystemMemberIdentification2;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.CreditTransferTransactionInformation10CH;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.Document;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.GroupHeader32CH;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.ObjectFactory;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.PaymentInstructionInformation3CH;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.PaymentMethod3Code;
import org.jetbrains.annotations.Contract;
import org.xml.sax.SAXException;

import static ch.dvbern.oss.lib.iso20022.PainConstants.CCY;
import static ch.dvbern.oss.lib.iso20022.PainConstants.CTCTDTLS_OTHR;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * Service implementation to generate Payment-File Pain001 according to ISO20022 for a swiss bank
 */
@Stateless
@Local(Pain001Service.class)
public class Pain001V00103CH02Service implements Pain001Service {

	private static final PaymentMethod3Code PAYMENT_METHOD_3_CODE = PaymentMethod3Code.TRA;
	private static final Boolean BTCHBOOKG = true;
	private static final String CLRSYS_CD = "CHBCC"; // Code swiss bank
	private static final Pattern FIND_SPACES = Pattern.compile(SPACE);
	private static final Pattern NON_ASCII = Pattern.compile("[^\\p{ASCII}]");
	private static final int MAX_SIGNS = 35;

	@Override
	public byte[] getPainFileContent(Pain001DTO pain001DTO) {
		final Document document = createPain001Document(pain001DTO);

		return PainJaxbUtil.getXMLStringFromDocument(document, Document.class, SCHEMA_LOCATION).getBytes(StandardCharsets.UTF_8);
	}


	/**
	 * Validation and Schema not used at the moment
	 */
	protected Schema getSchema() throws SAXException {
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		final URL resourceURL = Document.class.getClassLoader().getResource(SCHEMA_LOCATION_LOCAL);
		if (resourceURL == null) {
			throw new Iso20022RuntimeException("Schema not found: " + SCHEMA_LOCATION_LOCAL);
		}

		return schemaFactory.newSchema(resourceURL);
	}

	/**
	 * Example:
	 * <pre>
	 * {@code
	 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	 * <Document xmlns="http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd"
	 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 * xsi:schemaLocation="http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd
	 * pain.001.001.03.ch.02.xsd">
	 * 	<CstmrCdtTrfInitn>
	 * 		<GrpHdr>
	 * 			<!--Group header-->
	 * 		</GrpHdr>
	 * 		<PmtInf>
	 * 			<!--Payment Information-->
	 * 		</PmtInf>
	 * 	</CstmrCdtTrfInitn>
	 * </Document>
	 * }
	 * </pre>
	 */
	private Document createPain001Document(Pain001DTO pain001DTO) {

		String debtorName = pain001DTO.getSchuldnerName();
		String debtorBic = pain001DTO.getSchuldnerBIC();
		String debtorIban = pain001DTO.getSchuldnerIBAN();
		String debtorIbanGebuehren = pain001DTO.getSchuldnerIBANGebuehren();

		if (debtorName == null) {
			throw new Iso20022RuntimeException("Empty deptor name: debtor_name is required");
		}
		if (debtorBic == null) {
			throw new Iso20022RuntimeException("Empty deptor Bank BIC Number: debtor_bic is required");
		}
		if (debtorIban == null) {
			throw new Iso20022RuntimeException("Empty IBAN: debtor_iban is required");
		}
		if (debtorIbanGebuehren == null) {
			debtorIbanGebuehren = debtorIban;
		}

		ObjectFactory objectFactory = new ObjectFactory();
		Document document = objectFactory.createDocument();
		document.setCstmrCdtTrfInitn(objectFactory.createCustomerCreditTransferInitiationV03CH());

		PaymentInstructionInformation3CH paymentInstructionInformation3CH = createPaymentInstructionInformation3CH(
			pain001DTO,
			objectFactory,
			debtorName,
			debtorIban,
			debtorBic,
			debtorIbanGebuehren);

		document.getCstmrCdtTrfInitn().getPmtInf().add(paymentInstructionInformation3CH);

		document.getCstmrCdtTrfInitn().getPmtInf().get(0).getCdtTrfTxInf().clear();

		int transaktion = 0;
		BigDecimal ctrlSum = BigDecimal.ZERO;
		for (AuszahlungDTO auszahlungDTO : pain001DTO.getAuszahlungen()) {
			Objects.requireNonNull(auszahlungDTO.getBetragTotalZahlung(), "Amount is required");
			transaktion++;

			ctrlSum = ctrlSum.add(auszahlungDTO.getBetragTotalZahlung());

			CreditTransferTransactionInformation10CH info = createCreditTransferTransactionInformation10CH(
				objectFactory,
				transaktion,
				auszahlungDTO,
				pain001DTO.getAuszahlungsDatum());

			document.getCstmrCdtTrfInitn().getPmtInf().get(0).getCdtTrfTxInf().add(info);
		}

		GroupHeader32CH groupHeader = createGroupHeader(pain001DTO, objectFactory, transaktion, ctrlSum, debtorName);
		document.getCstmrCdtTrfInitn().setGrpHdr(groupHeader);

		return document;
	}

	/**
	 * Example:
	 * <pre>
	 * {@code
	 * <CdtTrfTxInf>
	 * 	<PmtId>
	 * 		<InstrId>18</InstrId>
	 * 	</PmtId>
	 * 	<Amt>
	 * 		<InstdAmt Ccy="CHF">1175</InstdAmt>
	 * 	</Amt>
	 * 	<CdtrAgt>
	 * 		<FinInstnId>
	 * 			<ClrSysMmbId>
	 * 				<ClrSysId>
	 * 					<Cd>CHBCC</Cd>
	 * 				</ClrSysId>
	 * 				<MmbId>9000</MmbId>
	 * 			</ClrSysMmbId>
	 * 		</FinInstnId>
	 * 	</CdtrAgt>
	 * 	<Cdtr>
	 * 		<Nm>Tester-Ncbijgep Tim</Nm>
	 * 		<PstlAdr>
	 * 			<StrtNm>Thunstrasse 17</StrtNm>
	 * 			<PstCd>3000</PstCd>
	 * 			<TwnNm>Bern</TwnNm>
	 * 			<Ctry>CH</Ctry>
	 * 		</PstlAdr>
	 * 	</Cdtr>
	 * 	<CdtrAcct>
	 * 		<Id>
	 * 			<IBAN>CH3780817000000576623</IBAN>
	 * 		</Id>
	 * 	</CdtrAcct>
	 * 	<RmtInf>
	 * 		<Ustrd>some text...</Ustrd>
	 * 	</RmtInf>
	 * </CdtTrfTxInf>
	 * }
	 * </pre>
	 */
	private CreditTransferTransactionInformation10CH createCreditTransferTransactionInformation10CH(
		ObjectFactory objectFactory,
		int transaktion,
		AuszahlungDTO auszahlungDTO,
		LocalDate date) {

		Objects.requireNonNull(auszahlungDTO.getZahlungsempfaegerIBAN(), "IBAN is required");
		Objects.requireNonNull(auszahlungDTO.getBetragTotalZahlung(), "Amount is required");
		Objects.requireNonNull(auszahlungDTO.getZahlungsempfaegerBankClearingNumber(), "BIC is required");

		CreditTransferTransactionInformation10CH cTTI10CH = objectFactory
			.createCreditTransferTransactionInformation10CH();

		// structure
		cTTI10CH.setPmtId(objectFactory.createPaymentIdentification1());

		cTTI10CH.setAmt(objectFactory.createAmountType3Choice());
		cTTI10CH.getAmt().setInstdAmt(objectFactory.createActiveOrHistoricCurrencyAndAmount());

		cTTI10CH.setCdtr(objectFactory.createPartyIdentification32CHName());
		cTTI10CH.getCdtr().setPstlAdr(objectFactory.createPostalAddress6CH());

		cTTI10CH.setRmtInf(objectFactory.createRemittanceInformation5CH());

		cTTI10CH.setCdtrAgt(objectFactory.createBranchAndFinancialInstitutionIdentification4CH());
		cTTI10CH.getCdtrAgt().setFinInstnId(objectFactory.createFinancialInstitutionIdentification7CH());

		ClearingSystemMemberIdentification2 csmid2 = objectFactory.createClearingSystemMemberIdentification2();
		cTTI10CH.getCdtrAgt().getFinInstnId().setClrSysMmbId(csmid2);

		ClearingSystemIdentification2Choice csid2choice = objectFactory.createClearingSystemIdentification2Choice();
		cTTI10CH.getCdtrAgt().getFinInstnId().getClrSysMmbId().setClrSysId(csid2choice);

		// data
		String transaktionStr = String.valueOf(transaktion);
		cTTI10CH.getPmtId().setInstrId(Iso20022Util.replaceSwift(transaktionStr)); // 2.29 // SWIFT

		String zahlungsempfaegerName = auszahlungDTO.getZahlungsempfaegerName();
		// "{id}/{month number}/{normalized without umlauts (öäü)} => "1/2/Brunnen
		String endToEndId = transaktionStr + '/' + date.getMonthValue() + '/' + zahlungsempfaegerName; // SWIFT
		endToEndId = Iso20022Util.replaceSwift(endToEndId);
		// 2.30 max 35 signs
		cTTI10CH.getPmtId().setEndToEndId(endToEndId.substring(0, Math.min(endToEndId.length(), MAX_SIGNS)));

		// value
		cTTI10CH.getAmt().getInstdAmt().setCcy(CCY);// 2.43
		cTTI10CH.getAmt().getInstdAmt().setValue(auszahlungDTO.getBetragTotalZahlung());// 2.43

		//ClrSysMmbId
		cTTI10CH.getCdtrAgt().getFinInstnId().getClrSysMmbId().getClrSysId().setCd(CLRSYS_CD);
		String zempfBCN = auszahlungDTO.getZahlungsempfaegerBankClearingNumber();
		cTTI10CH.getCdtrAgt().getFinInstnId().getClrSysMmbId().setMmbId(zempfBCN);

		//IBAN
		cTTI10CH.setCdtrAcct(objectFactory.createCashAccount16CHId());
		cTTI10CH.getCdtrAcct().setId(objectFactory.createAccountIdentification4ChoiceCH()); // 2.80
		String iban = FIND_SPACES.matcher(auszahlungDTO.getZahlungsempfaegerIBAN()).replaceAll(EMPTY);
		cTTI10CH.getCdtrAcct().getId().setIBAN(iban); // 2.80

		cTTI10CH.setCdtr(objectFactory.createPartyIdentification32CHName());
		cTTI10CH.getCdtr().setNm(normalize(zahlungsempfaegerName)); // 2.79
		setPstlAdr(objectFactory, auszahlungDTO, cTTI10CH);

		cTTI10CH.setRmtInf(objectFactory.createRemittanceInformation5CH());

		if (auszahlungDTO.getZahlungText() == null) {
			String monat = date.format(DateTimeFormatter.ofPattern("MMM.yyyy", Locale.GERMAN));
			String ustrd = normalize(zahlungsempfaegerName + ", Monat " + monat);
			cTTI10CH.getRmtInf().setUstrd(ustrd);    // 2.99
		} else {
			cTTI10CH.getRmtInf().setUstrd(normalize(auszahlungDTO.getZahlungText()));
		}
		return cTTI10CH;
	}

	private void setPstlAdr(
		ObjectFactory objectFactory,
		AuszahlungDTO auszahlungDTO,
		CreditTransferTransactionInformation10CH cTTI10CH) {

		if (!hasAddressData(auszahlungDTO)) {
			return;
		}

		cTTI10CH.getCdtr().setPstlAdr(objectFactory.createPostalAddress6CH());
		if (auszahlungDTO.getZahlungsempfaegerStrasse() != null) {
			cTTI10CH.getCdtr().getPstlAdr().setStrtNm(normalize(auszahlungDTO.getZahlungsempfaegerStrasse())); // 2.79
		}
		if (auszahlungDTO.getZahlungsempfaegerHausnummer() != null) {
			cTTI10CH.getCdtr().getPstlAdr().setBldgNb(auszahlungDTO.getZahlungsempfaegerHausnummer()); // 2.79
		}
		if (auszahlungDTO.getZahlungsempfaegerPlz() != null) {
			cTTI10CH.getCdtr().getPstlAdr().setPstCd(auszahlungDTO.getZahlungsempfaegerPlz());// 2.79
		}
		if (auszahlungDTO.getZahlungsempfaegerOrt() != null) {
			cTTI10CH.getCdtr().getPstlAdr().setTwnNm(normalize(auszahlungDTO.getZahlungsempfaegerOrt()));// 2.79
		}
		if (auszahlungDTO.getZahlungsempfaegerLand() != null) {
			cTTI10CH.getCdtr().getPstlAdr().setCtry(auszahlungDTO.getZahlungsempfaegerLand());// 2.79
		}
	}

	@SuppressWarnings("checkstyle:BooleanExpressionComplexity")
	private boolean hasAddressData(AuszahlungDTO auszahlungDTO) {
		return auszahlungDTO.getZahlungsempfaegerStrasse() != null
			|| auszahlungDTO.getZahlungsempfaegerHausnummer() != null
			|| auszahlungDTO.getZahlungsempfaegerPlz() != null
			|| auszahlungDTO.getZahlungsempfaegerOrt() != null
			|| auszahlungDTO.getZahlungsempfaegerLand() != null;
	}

	@Contract("!null->!null; null->null;")
	@Nullable
	private String normalize(@Nullable String text) {
		if (null == text) {
			return null;
		}

		return NON_ASCII.matcher(Normalizer.normalize(text, Form.NFD)).replaceAll(EMPTY);
	}


	/**
	 * Example PaymentInformation:
	 * <pre>
	 * {@code
	 * <PmtInf>
	 * 	<PmtInfId>01-201611-01</PmtInfId>
	 * 	<PmtMtd>TRA</PmtMtd>
	 * 	<BtchBookg>true</BtchBookg>
	 * 	<PmtTpInf>
	 * 		<CtgyPurp>
	 * 			<Cd>PENS</Cd>
	 * 		</CtgyPurp>
	 * 	</PmtTpInf>
	 * 	<ReqdExctnDt>2017-01</ReqdExctnDt>
	 * 	<Dbtr>
	 * 		<Nm>Name of Deptor</Nm>
	 * 	</Dbtr>
	 * 	<DbtrAcct>
	 * 		<Id>
	 * 			<IBAN>CH0809000000300270001</IBAN>
	 * 		</Id>
	 * 	</DbtrAcct>
	 * 	<DbtrAgt>
	 * 		<FinInstnId>
	 * 			<BIC>POFICHBEXXX</BIC>
	 * 		</FinInstnId>
	 * 	</DbtrAgt>
	 * 	<ChrgsAcct>
	 * 		<Id>
	 * 			<IBAN>CH4709000000300003131</IBAN>
	 * 		</Id>
	 * 	</ChrgsAcct>
	 * 	<CdtTrfTxInf>
	 * 		<!--Payments-->
	 * 	</CdtTrfTxInf>
	 * </PmtInf>
	 * }
	 * </pre>
	 */

	private PaymentInstructionInformation3CH createPaymentInstructionInformation3CH(
		Pain001DTO pain001DTO,
		ObjectFactory objectFactory,
		String debtorName,
		String debtorIban,
		String debtorBic,
		String debtorIbanGebuehren) {

		PaymentInstructionInformation3CH paymentInstructionInformation3CH = objectFactory
			.createPaymentInstructionInformation3CH();
		paymentInstructionInformation3CH.setPmtInfId(Iso20022Util.replaceSwift(pain001DTO.getMsgId())); // SWIFT
		paymentInstructionInformation3CH.setPmtMtd(PAYMENT_METHOD_3_CODE);

		paymentInstructionInformation3CH.setBtchBookg(BTCHBOOKG);

		paymentInstructionInformation3CH.setPmtTpInf(objectFactory.createPaymentTypeInformation19CH());

		LocalDateTime localDateTime = pain001DTO.getAuszahlungsDatum().atStartOfDay();
		XMLGregorianCalendar reqdExctnDt = Iso20022Util.toXmlGregorianCalendar(localDateTime);
		paymentInstructionInformation3CH.setReqdExctnDt(reqdExctnDt);

		// Debtor name
		paymentInstructionInformation3CH.setDbtr(objectFactory.createPartyIdentification32CH());
		paymentInstructionInformation3CH.getDbtr().setNm(debtorName);

		// Debtor Iban
		paymentInstructionInformation3CH.setDbtrAcct(objectFactory.createCashAccount16CHIdTpCcy());
		paymentInstructionInformation3CH.getDbtrAcct().setId(objectFactory.createAccountIdentification4ChoiceCH());
		paymentInstructionInformation3CH.getDbtrAcct().getId().setIBAN(debtorIban);

		// Debtor BIC
		paymentInstructionInformation3CH.setDbtrAgt(objectFactory
			.createBranchAndFinancialInstitutionIdentification4CHBicOrClrId());
		paymentInstructionInformation3CH.getDbtrAgt().setFinInstnId(objectFactory
			.createFinancialInstitutionIdentification7CHBicOrClrId());
		paymentInstructionInformation3CH.getDbtrAgt().getFinInstnId().setBIC(debtorBic);

		// Debtor charge Iban
		paymentInstructionInformation3CH.setChrgsAcct(objectFactory.createCashAccount16CHIdAndCurrency());
		paymentInstructionInformation3CH.getChrgsAcct().setId(objectFactory.createAccountIdentification4ChoiceCH());
		paymentInstructionInformation3CH.getChrgsAcct().getId().setIBAN(debtorIbanGebuehren);

		return paymentInstructionInformation3CH;
	}

	/**
	 * Example:
	 * <pre>
	 * {@code <GrpHdr>
	 * 	<MsgId>01-201611-01</MsgId>
	 * 	<CreDtTm>2016-10-28T00:00:00.000+02:00</CreDtTm>
	 * 	<NbOfTxs>130</NbOfTxs>
	 * 	<CtrlSum>204013</CtrlSum>
	 * 	<InitgPty>
	 * 		<Nm>Name of Deptor</Nm>
	 * 		<CtctDtls>
	 * 			<Nm>Kitac</Nm>
	 * 			<Othr>V01</Othr>
	 * 		</CtctDtls>
	 * 	</InitgPty>
	 * </GrpHdr>
	 * }
	 * </pre>
	 */
	private GroupHeader32CH createGroupHeader(
		Pain001DTO pain001DTO,
		ObjectFactory objectFactory,
		int transaktion,
		BigDecimal ctrlSum,
		String debtorName) {
		// GroupHeader
		// structure
		GroupHeader32CH groupHeader32CH = objectFactory.createGroupHeader32CH();

		groupHeader32CH.setInitgPty(objectFactory.createPartyIdentification32CHNameAndId());
		groupHeader32CH.getInitgPty().setCtctDtls(objectFactory.createContactDetails2CH());

		// data
		groupHeader32CH.setMsgId(Iso20022Util.replaceSwift(pain001DTO.getMsgId())); // 1.1 // SWIFT

		groupHeader32CH.setNbOfTxs(Integer.toString(transaktion)); // 1.6
		groupHeader32CH.setCtrlSum(ctrlSum); // 1.7

		groupHeader32CH.getInitgPty().setNm(debtorName); // 1.8

		groupHeader32CH.getInitgPty().getCtctDtls().setNm(pain001DTO.getSoftwareName()); // 1.8
		groupHeader32CH.getInitgPty().getCtctDtls().setOthr(CTCTDTLS_OTHR); // 1.8

		groupHeader32CH.setCreDtTm(Iso20022Util.toXmlGregorianCalendar(pain001DTO.getGenerierungsDatum())); // 1.2

		return groupHeader32CH;
	}

}
