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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.camt.BAccountStatementDTO;
import ch.dvbern.oss.lib.iso20022.camt.CStatementEntryDTO;
import ch.dvbern.oss.lib.iso20022.camt.DEntryDetailsDTO;
import ch.dvbern.oss.lib.iso20022.camt.DTransactionDetailsDTO;
import ch.dvbern.oss.lib.iso20022.camt.DocumentDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Tests the Camt054V00104Service with the file camt_054_Beispiel_ZA1_ESR_ZE.xml from SIX Interbank Clearing (see
 * www.six-interbank-clearing.com/dam/downloads/de/standardization/iso/swiss-recommendations/swiss-usage-guide
 * -examples.zip)
 */
public class Camt054V00104ServiceEsrZa1Test extends Camt054V00104ServiceAbstractTest {

	private final DEntryDetailsDTO expDEntryDetailsDTO = new DEntryDetailsDTO();
	private final DTransactionDetailsDTO expDTxDetailsDTO = new DTransactionDetailsDTO();

	@Before
	public void setUp() throws IOException {

		setXmlAsBytes("ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_ZA1_ESR_ZE.xml");

		LocalDateTime creDtTm = LocalDateTime.of(2015, 1, 15, 9, 30, 47);

		// A-Level: Group Header, 'Meldungsebene'
		getaHeaderDTO().setMessageIdentification("MSGID-C053.01.00.10-110725163809-01");
		getaHeaderDTO().setCreationDateTime(creDtTm);

		// B-Level: Account Statement, 'Konto-Ebene' (CH Recommendations support only one account per 'camt.053')
		getbAccountStatementDTO().setIdentification("C054.01.00.10-110725163809-01");
		getbAccountStatementDTO().setCreationDateTime(creDtTm);
		getbAccountStatementDTO().setAccountIdentificationIBAN("CH160077401231234567");

		// C-Level: Statement Entry, 'Betrags-Ebene'
		getcStatementEntryDTO().setEntryReference("010391391");
		getcStatementEntryDTO().setAmountCurrency("CHF");
		getcStatementEntryDTO().setAmount("3949.75");
		getcStatementEntryDTO().setCreditDebitIndicator("CRDT");
		getcStatementEntryDTO().setStatus("BOOK");
		getcStatementEntryDTO().setBookingDate(LocalDate.parse("2015-01-07"));
		getcStatementEntryDTO().setValueDate(LocalDate.parse("2015-01-07"));
		getcStatementEntryDTO().setBankTransactionCodeDomainCode("PMNT");
		getcStatementEntryDTO().setBankTransactionCodeDomainFamilyCode("RCDT");
		getcStatementEntryDTO().setBankTransactionCodeDomainFamilySubFamilyCode("VCOM");

		// D-Level: Entry Details, 'Betrags-Details'
		List<DTransactionDetailsDTO> transactionDetailsDTOS = new ArrayList<>();
		expDTxDetailsDTO.setAmount(BigDecimal.valueOf(((Long) 394975L), 2));
		expDTxDetailsDTO.setAmountCurrency("CHF");
		expDTxDetailsDTO.setCreditDebitIndicator("CRDT");
		expDTxDetailsDTO.setRelatedPartiesDebitorName("Pia Rutschmann");
		expDTxDetailsDTO.setRemittanceCreditorReferenceInformation("210000000003139471430009017");
		transactionDetailsDTOS.add(expDTxDetailsDTO);
		expDEntryDetailsDTO.setTransactionDetailsDTOS(transactionDetailsDTOS);

		getcStatementEntryDTO().getdEntryDetailsDTOS().add(this.expDEntryDetailsDTO);
		getbAccountStatementDTO().getcStatementEntryDTOS().add(getcStatementEntryDTO());
		getDocumentDTO().getbAccountStatementsDTO().add(getbAccountStatementDTO());
		getDocumentDTO().setaGroupHeaderDTO(getaHeaderDTO());
	}

	@Test
	public void getDocumentALevelTest() throws Exception {

		aLevelTest();
	}

	@Test
	public void getDocumentBLevelTest() throws Exception {

		bLevelTest();
	}

	@Test
	public void getDocumentCLevelTest() throws Exception {

		cLevelTest();
	}

	@Test
	public void getDocumentDLevelTest() throws Exception {

		// run
		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
		List<BAccountStatementDTO> actBAccStatements = actDocDTO.getbAccountStatementsDTO();
		List<CStatementEntryDTO> actCEntryDTOS = actBAccStatements.get(0).getcStatementEntryDTOS();
		List<DEntryDetailsDTO> actDEntryDetailsDTOS = actCEntryDTOS.get(0).getdEntryDetailsDTOS();

		// test
		// D-Level: Entry Details, 'Betrags-Details'
		Assert.assertNotNull(actDEntryDetailsDTOS);
		Assert.assertEquals(getcStatementEntryDTO().getdEntryDetailsDTOS().size(), actDEntryDetailsDTOS.size());
		DEntryDetailsDTO actDEntryDetailsDTO = actDEntryDetailsDTOS.get(0);
		List<DTransactionDetailsDTO> actTransactionDetailsDTOS = actDEntryDetailsDTO.getTransactionDetailsDTOS();

		Assert.assertNotNull(actTransactionDetailsDTOS);
		Assert.assertEquals(expDEntryDetailsDTO.getTransactionDetailsDTOS().size(), actTransactionDetailsDTOS.size());
		final DTransactionDetailsDTO actDTxDetailsDTO = actTransactionDetailsDTOS.get(0);

		Assert.assertNotNull(actDTxDetailsDTO);
		Assert.assertEquals(this.expDTxDetailsDTO.getAmount(), actDTxDetailsDTO.getAmount());
		Assert.assertEquals(this.expDTxDetailsDTO.getAmountCurrency(), actDTxDetailsDTO.getAmountCurrency());
		Assert.assertEquals(this.expDTxDetailsDTO.getCreditDebitIndicator(), actDTxDetailsDTO.getCreditDebitIndicator());
		Assert.assertEquals(this.expDTxDetailsDTO.getRelatedPartiesDebitorName(), actDTxDetailsDTO
				.getRelatedPartiesDebitorName());
		Assert.assertEquals(this.expDTxDetailsDTO.getRemittanceCreditorReferenceInformation(), actDTxDetailsDTO
				.getRemittanceCreditorReferenceInformation());
	}

	@Test
	public void getBankToCustomerDebitCreditNotificationV04Test() throws Exception {

		// run
		final Object actObject = getCamtService().getNotificationFromXml(getXmlAsBytes());

		// test
		Assert.assertNotNull(actObject);
		Assert.assertThat(actObject, instanceOf(BankToCustomerDebitCreditNotificationV04.class));
	}
}