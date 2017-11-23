///*
// * Copyright 2017 DV Bern AG
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * limitations under the License.
// */
//
//package ch.dvbern.oss.lib.iso20022.camt053.v00104;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import ch.dvbern.oss.lib.iso20022.camt.dtos.Account;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.CStatementEntryDTO;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.DEntryDetailsDTO;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.DTransactionDetailsDTO;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.DocumentDTO;
//import iso.std.iso._20022.tech.xsd.camt_053_001.BankToCustomerStatementV04;
//import org.apache.commons.io.IOUtils;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.hamcrest.core.IsInstanceOf.instanceOf;
//
///**
// * Tests the Camt053V00104Service with the file Business Sample camt.053.001.04.xml
// * from iso20022.org (see https://www.iso20022.org/documents/messages/camt/instances/camt.053.001.04.zip)
// */
//public class Camt053V00104ServiceBusinessSampleTest extends Camt053V00104ServiceAbstractTest {
//
//	private final CStatementEntryDTO expCEntryDTO0 = new CStatementEntryDTO();
//	private final CStatementEntryDTO expCEntryDTO1 = new CStatementEntryDTO();
//
//	@Before
//	public void setUp() throws IOException {
//
//		InputStream xmlAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
//				("ch/dvbern/oss/lib/iso20022/camt053/v00104/Business Sample camt.053.001.04.xml");
//		setXmlAsBytes(IOUtils.toByteArray(xmlAsStream));
//
//		LocalDateTime creDtTm = LocalDateTime.of(2010, 10, 18, 17, 0, 0);
//
//		// A-Level: Group Header, 'Meldungsebene'
//		getaHeaderDTO().setMessageIdentification("AAAASESS-FP-STAT001");
//		getaHeaderDTO().setCreationDateTime(creDtTm);
//
//		// B-Level: Account Statement, 'Konto-Ebene' (CH Recommendations support only one account per 'camt.053')
//		getbAccountStatementDTO().setIdentification("AAAASESS-FP-STAT001");
//		getbAccountStatementDTO().setCreationDateTime(creDtTm);
//
//		// C-Level: Statement Entry, 'Betrags-Ebene'
//		expCEntryDTO0.setAmountCurrency("SEK");
//		expCEntryDTO0.setAmount("105678.50");
//		expCEntryDTO0.setCreditDebitIndicator("CRDT");
//		expCEntryDTO0.setStatus("BOOK");
//		expCEntryDTO0.setBookingDate(LocalDate.parse("2010-10-18"));
//		expCEntryDTO0.setValueDate(LocalDate.parse("2010-10-18"));
//		expCEntryDTO0.setBankTransactionCodeDomainCode("PAYM");
//		expCEntryDTO0.setBankTransactionCodeDomainFamilyCode("0001");
//		expCEntryDTO0.setBankTransactionCodeDomainFamilySubFamilyCode("0005");
//
//		expCEntryDTO1.setAmountCurrency("SEK");
//		expCEntryDTO1.setAmount("30000");
//		expCEntryDTO1.setCreditDebitIndicator("CRDT");
//		expCEntryDTO1.setStatus("BOOK");
//		expCEntryDTO1.setBookingDate(LocalDate.parse("2010-10-18"));
//		expCEntryDTO1.setValueDate(LocalDate.parse("2010-10-18"));
//		expCEntryDTO1.setBankTransactionCodeDomainCode("TREA");
//		expCEntryDTO1.setBankTransactionCodeDomainFamilyCode("0002");
//		expCEntryDTO1.setBankTransactionCodeDomainFamilySubFamilyCode("0000");
//
//		// D-Level: Entry Details, 'Betrags-Details'
//		// none with reference Id etc.
//
//		getbAccountStatementDTO().getBookings().add(expCEntryDTO0);
//		getbAccountStatementDTO().getBookings().add(expCEntryDTO1);
//		getDocumentDTO().getbAccountStatementsDTO().add(getbAccountStatementDTO());
//		getDocumentDTO().setMessageIdentifier(getaHeaderDTO());
//	}
//
//	@Test
//	public void getDocumentALevelTest() {
//
//		aLevelTest();
//	}
//
//	@Test
//	public void getDocumentBLevelTest() {
//
//		bLevelTest();
//	}
//
//	@Test
//	public void getDocumentCLevelTest() {
//
//		// run
//		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
//		List<Account> actBAccStatements = actDocDTO.getbAccountStatementsDTO();
//		List<CStatementEntryDTO> actCStatementEntryDTOS = actBAccStatements.get(0).getBookings();
//
//		// test
//		// 3 entries, but skipped 1 with Credit Debit Indicator DBIT
//		Assert.assertEquals(2 , actCStatementEntryDTOS.size());
//		testAssertionsCLevel(expCEntryDTO0, actCStatementEntryDTOS.get(0));
//		testAssertionsCLevel(expCEntryDTO1, actCStatementEntryDTOS.get(1));
//
//	}
//
//
//	@Test
//	public void getDocumentDLevelTest() {
//
//		// run
//		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
//		List<Account> actBAccStatements = actDocDTO.getbAccountStatementsDTO();
//		List<CStatementEntryDTO> actCEntryDTOS = actBAccStatements.get(0).getBookings();
//		List<DEntryDetailsDTO> actDEntryDetailsDTOS = actCEntryDTOS.get(0).getdEntryDetailsDTOS();
//
//		// test
//		// D-Level: Entry Details, 'Betrags-Details'
//		Assert.assertNotNull(actDEntryDetailsDTOS);
//		DEntryDetailsDTO actDEntryDetailsDTO = actDEntryDetailsDTOS.get(0);
//		List<DTransactionDetailsDTO> actTransactionDetailsDTOS = actDEntryDetailsDTO.getTransactionDetailsDTOS();
//
//		Assert.assertNotNull(actTransactionDetailsDTOS);
//		Assert.assertEquals(0, actTransactionDetailsDTOS.size());
//	}
//
//	@Test
//	public void getBankToCustomerStatementV04Test() {
//
//		// run
//		final Object actObject = getCamtService().getNotificationFromXml(getXmlAsBytes());
//
//		// test
//		Assert.assertNotNull(actObject);
//		Assert.assertThat(actObject, instanceOf(BankToCustomerStatementV04.class));
//	}
//}
