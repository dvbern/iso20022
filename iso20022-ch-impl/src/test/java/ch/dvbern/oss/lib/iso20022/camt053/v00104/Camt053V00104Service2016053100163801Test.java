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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.camt.BAccountStatementDTO;
import ch.dvbern.oss.lib.iso20022.camt.CStatementEntryDTO;
import ch.dvbern.oss.lib.iso20022.camt.DEntryDetailsDTO;
import ch.dvbern.oss.lib.iso20022.camt.DTransactionDetailsDTO;
import ch.dvbern.oss.lib.iso20022.camt.DocumentDTO;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Tests the Camt053V00104Service with the file camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml
 * from PostFinance (see https://www.postfinance.ch/content/dam/pfch/doc/zv/beispiele_testfiles.zip)
 */
public class Camt053V00104Service2016053100163801Test extends Camt053V00104ServiceAbstractTest {

	private final DEntryDetailsDTO expDEntryDetailsDTO = new DEntryDetailsDTO();
	private final DTransactionDetailsDTO expDTxDetailsDTO = new DTransactionDetailsDTO();

	@Before
	public void setUp() throws IOException {

		InputStream xmlAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
				("ch/dvbern/oss/lib/iso20022/camt053/v00104"
						+ "/camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml");
		setXmlAsBytes(IOUtils.toByteArray(xmlAsStream));

		LocalDateTime creDtTm = LocalDateTime.of(2016, 4, 30, 11, 50, 0);

		// A-Level: Group Header, 'Meldungsebene'
		getaHeaderDTO().setMessageIdentification("20160430375204000008573");
		getaHeaderDTO().setCreationDateTime(creDtTm);

		// B-Level: Account Statement, 'Konto-Ebene' (CH Recommendations support only one account per 'camt.053')
		getbAccountStatementDTO().setIdentification("20160430375204000008574");
		getbAccountStatementDTO().setCreationDateTime(creDtTm);
		getbAccountStatementDTO().setAccountIdentificationIBAN("CH0309000000250090342");

		// C-Level: Statement Entry, 'Betrags-Ebene'
		getcStatementEntryDTO().setEntryReference("010000004");
		getcStatementEntryDTO().setAmountCurrency("CHF");
		getcStatementEntryDTO().setAmount("1000.00");
		getcStatementEntryDTO().setCreditDebitIndicator("CRDT");
		getcStatementEntryDTO().setStatus("BOOK");
		getcStatementEntryDTO().setBookingDate(LocalDate.parse("2016-05-27"));
		getcStatementEntryDTO().setValueDate(LocalDate.parse("2016-05-30"));
		getcStatementEntryDTO().setBankTransactionCodeDomainCode("PMNT");
		getcStatementEntryDTO().setBankTransactionCodeDomainFamilyCode("RCDT");
		getcStatementEntryDTO().setBankTransactionCodeDomainFamilySubFamilyCode("VCOM");

		// D-Level: Entry Details, 'Betrags-Details'
		expDTxDetailsDTO.setAmount(BigDecimal.valueOf(((Long) 10000L), 2));
		expDTxDetailsDTO.setAmountCurrency("CHF");
		expDTxDetailsDTO.setCreditDebitIndicator("CRDT");
		expDTxDetailsDTO.setRemittanceCreditorReferenceInformation("000000000002015110002913192");
		expDEntryDetailsDTO.getTransactionDetailsDTOS().add(expDTxDetailsDTO);

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

		// run
		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
		List<BAccountStatementDTO> actBAccStatements = actDocDTO.getbAccountStatementsDTO();
		List<CStatementEntryDTO> actFirstCEntryDTOS = actBAccStatements.get(0).getcStatementEntryDTOS();

		// test
		// 13 entries, but skipped 8 with Credit Debit Indicator DBIT
		Assert.assertEquals(5 , actFirstCEntryDTOS.size());

		CStatementEntryDTO actFirstCEntryDTO = actFirstCEntryDTOS.get(0);
		Assert.assertNotNull(actFirstCEntryDTO);
		testAssertionsCLevel(getcStatementEntryDTO(), actFirstCEntryDTO);
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
		Assert.assertEquals(1, actDEntryDetailsDTOS.size());
		DEntryDetailsDTO actDEntryDetailsDTO = actDEntryDetailsDTOS.get(0);
		List<DTransactionDetailsDTO> actTransactionDetailsDTOS = actDEntryDetailsDTO.getTransactionDetailsDTOS();

		Assert.assertNotNull(actTransactionDetailsDTOS);
		Assert.assertEquals(10, actTransactionDetailsDTOS.size());
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
	public void getBankToCustomerStatementV04Test() throws Exception {

		// run
		final Object actObject = getCamtService().getNotificationFromXml(getXmlAsBytes());

		// test
		Assert.assertNotNull(actObject);
		Assert.assertThat(actObject, instanceOf(BankToCustomerStatementV04.class));
	}
}