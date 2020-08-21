package ch.dvbern.oss.lib.iso20022.pain008.v00102ch03;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.Valid;

import ch.dvbern.oss.lib.iso20022.Iso20022JaxbUtil;
import ch.dvbern.oss.lib.iso20022.Iso20022Util;
import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain008DTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.PaymentInformationDTO;
import ch.dvbern.oss.lib.iso20022.dtos.shared.TransactionInformationDTO;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.CashAccount16;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.DirectDebitTransactionInformation9;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.Document;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.GroupHeader39;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.ObjectFactory;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.PartyIdentification32;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.PaymentInstructionInformation4;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.PaymentMethod2Code;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.PaymentTypeInformation20;
import com.six_interbank_clearing.de.pain_008_001_02_ch_03.ServiceLevel8Choice;

import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.CCY;
import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.CTCTDTLS_OTHR;

@Stateless
@Local(Pain008Service.class)
public class Pain008V00102CH03Service implements Pain008Service {

	private static final String SERVICE_LEVEL_PROPRIETARY = "CHTA";
	private static final String LOCAL_INSTRUMENT_PROPRIETARY = "LSV+";
	private static final String SCHEMA_NAME_PROPRIETARY = "CHLS";
	private static final String END_TO_END_NOT_PROVIDED = "NOTPROVIDED";
	private static final String REFERENCE_NUMBER_TYPE = "ESR";

	@Override
	public byte[] getPainFileContent(@Valid Pain008DTO pain008DTO) {
		final Document document = createPain008Document(pain008DTO);

		return Iso20022JaxbUtil.getXMLStringFromDocument(document, Document.class, SCHEMA_LOCATION, SCHEMA_NAME)
			.getBytes(StandardCharsets.UTF_8);
	}

	private Document createPain008Document(Pain008DTO dto) {
		ObjectFactory objFactory = new ObjectFactory();
		Document document = objFactory.createDocument();
		document.setCstmrDrctDbtInitn(objFactory.createCustomerDirectDebitInitiationV02());

		int transactionCount = 0;
		BigDecimal controlSum = BigDecimal.ZERO;

		// create B-Level (usually a single one)
		for (PaymentInformationDTO paymentInfoDto : dto.getPaymentInfo()) {
			PaymentInstructionInformation4 paymentInfo = createPaymentInfo(paymentInfoDto, objFactory);

			// create C-Levels
			for (TransactionInformationDTO transactionInfoDto : paymentInfoDto.getTransactionInfo()) {
				paymentInfo.getDrctDbtTxInf().add(createTransactionInfo(transactionInfoDto, objFactory));
				transactionCount++;
				controlSum = controlSum.add(transactionInfoDto.getInstructedAmount());
			}

			document.getCstmrDrctDbtInitn().getPmtInf().add(paymentInfo);
		}

		document.getCstmrDrctDbtInitn().setGrpHdr(createGroupHeader(dto, controlSum, transactionCount, objFactory));

		return document;
	}

	/**
	 * @param dto the DTO
	 * @param controlSum sum of all C-Level transactions
	 * @param transactionCount count of C-Level transactions
	 * @param objFactory the factory
	 */
	private GroupHeader39 createGroupHeader(
		Pain008DTO dto,
		BigDecimal controlSum,
		int transactionCount,
		ObjectFactory objFactory) {

		GroupHeader39 header = objFactory.createGroupHeader39();

		// general message info
		header.setMsgId(Iso20022Util.replaceSwift(dto.getMsgId()));
		header.setCreDtTm(Iso20022Util.toXmlGregorianCalendar(dto.getCreationDateTime()));
		header.setNbOfTxs(Integer.toString(transactionCount));
		header.setCtrlSum(controlSum);

		// initiating party info
		PartyIdentification32 partyId = objFactory.createPartyIdentification32();
		partyId.setNm(dto.getInitiatingPartyName());

		// software info
		partyId.setId(objFactory.createParty6Choice());
		partyId.getId().setOrgId(objFactory.createOrganisationIdentification4());
		partyId.getId().getOrgId().setOthr(objFactory.createGenericOrganisationIdentification1());
		partyId.getId().getOrgId().getOthr().setId(dto.getInitiatingPartyId());
		partyId.setCtctDtls(objFactory.createContactDetails2());
		partyId.getCtctDtls().setNm(dto.getSoftwareName());
		partyId.getCtctDtls().setOthr(CTCTDTLS_OTHR);

		header.setInitgPty(partyId);

		return header;
	}

	private PaymentInstructionInformation4 createPaymentInfo(
		PaymentInformationDTO dto,
		ObjectFactory objFactory) {

		PaymentInstructionInformation4 paymentInfo =
			objFactory.createPaymentInstructionInformation4();

		// general info
		paymentInfo.setPmtInfId(Iso20022Util.replaceSwift(dto.getPaymentInfoId()));
		paymentInfo.setPmtMtd(PaymentMethod2Code.DD);
		paymentInfo.setReqdColltnDt(
			Iso20022Util.toXmlGregorianCalendar(dto.getRequestedCollectionDate().atStartOfDay())
		);

		// LSV+ CH-TA info
		ServiceLevel8Choice serviceLevel = objFactory.createServiceLevel8Choice();
		serviceLevel.setPrtry(SERVICE_LEVEL_PROPRIETARY);
		PaymentTypeInformation20 paymentTypeInfo = objFactory.createPaymentTypeInformation20();
		paymentTypeInfo.setSvcLvl(serviceLevel);

		paymentTypeInfo.setLclInstrm(objFactory.createLocalInstrument2Choice());
		paymentTypeInfo.getLclInstrm().setPrtry(LOCAL_INSTRUMENT_PROPRIETARY);

		paymentInfo.setPmtTpInf(paymentTypeInfo);

		// creditors name
		PartyIdentification32 creditor = objFactory.createPartyIdentification32();
		creditor.setNm(dto.getCreditorName());
		paymentInfo.setCdtr(creditor);

		// creditors IBAN
		CashAccount16 creditorAccount = objFactory.createCashAccount16();
		creditorAccount.setId(objFactory.createAccountIdentification4Choice());
		creditorAccount.getId().setIBAN(dto.getCreditorIBAN());
		paymentInfo.setCdtrAcct(creditorAccount);

		// creditors financial institutions IID and ESR number
		paymentInfo.setCdtrAgt(objFactory.createBranchAndFinancialInstitutionIdentification4());
		paymentInfo.getCdtrAgt().setFinInstnId(objFactory.createFinancialInstitutionIdentification7());
		paymentInfo.getCdtrAgt().getFinInstnId().setClrSysMmbId(objFactory.createClearingSystemMemberIdentification2());
		paymentInfo.getCdtrAgt().getFinInstnId().getClrSysMmbId().setMmbId(dto.getCreditorIID());
		paymentInfo.getCdtrAgt().getFinInstnId().setOthr(objFactory.createGenericFinancialIdentification1());
		paymentInfo.getCdtrAgt().getFinInstnId().getOthr().setId(dto.getInstitutionEsr());

		// creditors LSV+ identification
		paymentInfo.setCdtrSchmeId(objFactory.createPartyIdentification32());
		paymentInfo.getCdtrSchmeId().setId(objFactory.createParty6Choice());
		paymentInfo.getCdtrSchmeId().getId().setPrvtId(objFactory.createPersonIdentification5());
		paymentInfo.getCdtrSchmeId().getId().getPrvtId().setOthr(
			objFactory.createGenericPersonIdentification1()
		);
		paymentInfo.getCdtrSchmeId().getId().getPrvtId().getOthr().setId(
			Iso20022Util.replaceSwift(dto.getCreditorId())
		);

		//mandatory value for CH-TA documents
		paymentInfo.getCdtrSchmeId().getId().getPrvtId().getOthr().setSchmeNm(
			objFactory.createPersonIdentificationSchemeName1Choice()
		);
		paymentInfo.getCdtrSchmeId().getId().getPrvtId().getOthr().getSchmeNm().setPrtry(SCHEMA_NAME_PROPRIETARY);

		return paymentInfo;
	}

	private DirectDebitTransactionInformation9 createTransactionInfo(
		TransactionInformationDTO dto,
		ObjectFactory objFactory) {

		DirectDebitTransactionInformation9 transactionInfo =
			objFactory.createDirectDebitTransactionInformation9();

		transactionInfo.setPmtId(objFactory.createPaymentIdentification1());
		transactionInfo.getPmtId().setInstrId(Iso20022Util.replaceSwift(dto.getTransactionId()));
		transactionInfo.getPmtId().setEndToEndId(END_TO_END_NOT_PROVIDED);

		transactionInfo.setInstdAmt(objFactory.createActiveOrHistoricCurrencyAndAmount());
		transactionInfo.getInstdAmt().setCcy(CCY);
		transactionInfo.getInstdAmt().setValue(dto.getInstructedAmount());

		// debitors financial institutions IID
		transactionInfo.setDbtrAgt(objFactory.createBranchAndFinancialInstitutionIdentification4());
		transactionInfo.getDbtrAgt().setFinInstnId(objFactory.createFinancialInstitutionIdentification7());
		transactionInfo.getDbtrAgt()
			.getFinInstnId()
			.setClrSysMmbId(objFactory.createClearingSystemMemberIdentification2());
		transactionInfo.getDbtrAgt().getFinInstnId().getClrSysMmbId().setMmbId(dto.getDebitorIID());

		// debitor name and IBAN
		transactionInfo.setDbtr(objFactory.createPartyIdentification32());
		transactionInfo.getDbtr().setNm(dto.getDebitorName());
		transactionInfo.setDbtrAcct(objFactory.createCashAccount16());
		transactionInfo.getDbtrAcct().setId(objFactory.createAccountIdentification4Choice());
		//noinspection ConstantConditions
		transactionInfo.getDbtrAcct().getId().setIBAN(dto.getDebitorIBAN());

		setOptionalDebitorAddress(dto, objFactory, transactionInfo);

		// reference number
		transactionInfo.setRmtInf(objFactory.createRemittanceInformation5());
		transactionInfo.getRmtInf().setStrd(objFactory.createStructuredRemittanceInformation7());
		transactionInfo.getRmtInf().getStrd().setCdtrRefInf(objFactory.createCreditorReferenceInformation2());
		transactionInfo.getRmtInf().getStrd().getCdtrRefInf().setTp(objFactory.createCreditorReferenceType2());
		transactionInfo.getRmtInf().getStrd().getCdtrRefInf().getTp().setCdOrPrtry(
			objFactory.createCreditorReferenceType1Choice()
		);
		transactionInfo.getRmtInf().getStrd().getCdtrRefInf().getTp().getCdOrPrtry().setPrtry(REFERENCE_NUMBER_TYPE);
		transactionInfo.getRmtInf().getStrd().getCdtrRefInf().setRef(dto.getRefNr());

		return transactionInfo;
	}

	@SuppressWarnings("ConstantConditions")
	private void setOptionalDebitorAddress(
		TransactionInformationDTO dto,
		ObjectFactory objFactory,
		DirectDebitTransactionInformation9 transactionInfo) {

		// optional debtor address
		if (dto.getDebitorStreetName() != null
			|| dto.getDebitorPostCode() != null
			|| dto.getDebitorTownName() != null
			|| dto.getDebitorCountry() != null) {

			transactionInfo.getDbtr().setPstlAdr(objFactory.createPostalAddress6());
			transactionInfo.getDbtr().getPstlAdr().setStrtNm(dto.getDebitorStreetName());
			transactionInfo.getDbtr().getPstlAdr().setPstCd(dto.getDebitorPostCode());
			transactionInfo.getDbtr().getPstlAdr().setTwnNm(dto.getDebitorTownName());
			transactionInfo.getDbtr().getPstlAdr().setCtry(dto.getDebitorCountry());
		}
	}
}
