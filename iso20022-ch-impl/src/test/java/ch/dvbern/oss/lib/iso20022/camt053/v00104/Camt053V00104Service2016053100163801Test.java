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
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import ch.dvbern.oss.lib.iso20022.camt.dtos.Account;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.Booking;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.CStatementEntryDTO;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.DocumentDTO;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.MessageIdentifier;
//import ch.dvbern.oss.lib.iso20022.camt.dtos.IsrTransaction;
//import iso.std.iso._20022.tech.xsd.camt_053_001.BankToCustomerStatementV04;
//import org.apache.commons.io.IOUtils;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.hamcrest.core.IsInstanceOf.instanceOf;
//
///**
// * Tests the Camt053V00104Service with the file camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml
// * from PostFinance (see https://www.postfinance.ch/content/dam/pfch/doc/zv/beispiele_testfiles.zip)
// */
//public class Camt053V00104Service2016053100163801Test extends Camt053V00104ServiceAbstractTest {
//
//	@Before
//	public void setUp() throws IOException {
//
//		InputStream xmlAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
//			("ch/dvbern/oss/lib/iso20022/camt053/v00104"
//				+ "/camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml");
//		setXmlAsBytes(IOUtils.toByteArray(xmlAsStream));
//
//		LocalDateTime creDtTm = LocalDateTime.of(2016, 4, 30, 11, 50, 0);
//
//		MessageIdentifier messageIdentifier = new MessageIdentifier("20160430375204000008573", creDtTm, "1", true);
//
//		Account account = new Account(
//			"20160430375204000008574",
//			BigDecimal.valueOf(85),
//			"CH0309000000250090342",
//			creDtTm
//		);
//
//		// C-Level: Statement Entry, 'Betrags-Ebene'
//		getcStatementEntryDTO().setEntryReference("010000004");
//		getcStatementEntryDTO().setAmountCurrency("CHF");
//		getcStatementEntryDTO().setAmount("1000.00");
//		getcStatementEntryDTO().setCreditDebitIndicator("CRDT");
//		getcStatementEntryDTO().setStatus("BOOK");
//		getcStatementEntryDTO().setBookingDate(LocalDate.parse("2016-05-27"));
//		getcStatementEntryDTO().setValueDate(LocalDate.parse("2016-05-30"));
//		getcStatementEntryDTO().setBankTransactionCodeDomainCode("PMNT");
//		getcStatementEntryDTO().setBankTransactionCodeDomainFamilyCode("RCDT");
//		getcStatementEntryDTO().setBankTransactionCodeDomainFamilySubFamilyCode("VCOM");
//
//		// D-Level: Entry Details, 'Betrags-Details'
//		IsrTransaction expectedTransaction = new IsrTransaction(
//			"CHF",
//			BigDecimal.valueOf(10000L, 2),
//			"000000000002015110002913192"
//		);
//		expDTxDetailsDTO.setAmount();
//		expDTxDetailsDTO.setAmountCurrency("CHF");
//		expDTxDetailsDTO.setCreditDebitIndicator("CRDT");
//		expDTxDetailsDTO.setRemittanceCreditorReferenceInformation("000000000002015110002913192");
//		expDEntryDetailsDTO.getTransactionDetailsDTOS().add(expDTxDetailsDTO);
//
//		getcStatementEntryDTO().getdEntryDetailsDTOS().add(this.expDEntryDetailsDTO);
//		getbAccountStatementDTO().getBookings().add(getcStatementEntryDTO());
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
//		List<CStatementEntryDTO> actFirstCEntryDTOS = actBAccStatements.get(0).getBookings();
//
//		// test
//		// 13 entries, but skipped 8 with Credit Debit Indicator DBIT
//		Assert.assertEquals(5, actFirstCEntryDTOS.size());
//
//		CStatementEntryDTO actFirstCEntryDTO = actFirstCEntryDTOS.get(0);
//		Assert.assertNotNull(actFirstCEntryDTO);
//		testAssertionsCLevel(getcStatementEntryDTO(), actFirstCEntryDTO);
//	}
//
//	@Test
//	public void getDocumentDLevelTest() {
//		DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
//		List<Account> actBAccStatements = actDocDTO.getAccounts();
//		List<Booking> actCEntryDTOS = actBAccStatements.get(0).getBookings();
//		List<IsrTransaction> actDEntryDetailsDTOS = actCEntryDTOS.get(0).getTransactions();
//
//		Assert.assertNotNull(actDEntryDetailsDTOS);
//		Assert.assertEquals(10, actDEntryDetailsDTOS.size());
//		IsrTransaction actDTxDetailsDTO = actDEntryDetailsDTOS.get(0);
//
//		Assert.assertNotNull(actDTxDetailsDTO);
//		Assert.assertEquals(this.expDTxDetailsDTO.getAmount(), actDTxDetailsDTO.getAmount());
//		Assert.assertEquals(this.expDTxDetailsDTO.getAmountCurrency(), actDTxDetailsDTO.getAmountCurrency());
//		Assert.assertEquals(this.expDTxDetailsDTO.getRe(), actDTxDetailsDTO
//			.getReferenceNumber());
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
