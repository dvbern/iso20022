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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.camt.BAccountStatementDTO;
import ch.dvbern.oss.lib.iso20022.camt.CStatementEntryDTO;
import ch.dvbern.oss.lib.iso20022.camt.DEntryDetailsDTO;
import ch.dvbern.oss.lib.iso20022.camt.DocumentDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Camt054V00104Service with the file camt_054_Beispiel_SammelbuchungEs.xml which contains 'Sammelbuchungen
 * ES'
 * Sammelbuchungen are ignored as their transaction details have no reference number
 */
public class Camt054V00104ServiceSammelEsTest extends Camt054V00104ServiceAbstractTest {

	private final DEntryDetailsDTO dEntryDetailsDTO = new DEntryDetailsDTO();

	@Before
	public void setUp() throws IOException {

		setXmlAsBytes("ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_SammelbuchungEs.xml");

		LocalDateTime creDtTm = LocalDateTime.of(2017, 7, 27, 14, 0, 40);

		// A-Level: Group Header, 'Meldungsebene'
		getaHeaderDTO().setMessageIdentification("MSGID-201707271400");
		getaHeaderDTO().setCreationDateTime(creDtTm);

		// B-Level: Account Statement, 'Konto-Ebene' (CH Recommendations support only one account per 'camt.053')
		getbAccountStatementDTO().setIdentification("NtfctnId20170727");
		getbAccountStatementDTO().setCreationDateTime(creDtTm);
		getbAccountStatementDTO().setAccountIdentificationIBAN("CH1600774012312345678");

		// C-Level: Statement Entry, 'Betrags-Ebene'
		getcStatementEntryDTO().setEntryReference(null);
		getcStatementEntryDTO().setAmountCurrency("CHF");
		getcStatementEntryDTO().setAmount("113.00");
		getcStatementEntryDTO().setCreditDebitIndicator("CRDT");
		getcStatementEntryDTO().setStatus("BOOK");
		getcStatementEntryDTO().setBookingDate(LocalDate.parse("2017-07-27"));
		getcStatementEntryDTO().setValueDate(LocalDate.parse("2017-07-27"));
		getcStatementEntryDTO().setBankTransactionCodeDomainCode("PMNT");
		getcStatementEntryDTO().setBankTransactionCodeDomainFamilyCode("RCDT");
		getcStatementEntryDTO().setBankTransactionCodeDomainFamilySubFamilyCode("AUTT");

		// D-Level: Entry Details, 'Betrags-Details'
		// none
		dEntryDetailsDTO.setTransactionDetailsDTOS(new ArrayList<>());

		getcStatementEntryDTO().getdEntryDetailsDTOS().add(dEntryDetailsDTO);
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
		List<CStatementEntryDTO> actCStmtEntryDTOS = actBAccStatements.get(0).getcStatementEntryDTOS();
		List<DEntryDetailsDTO> actDEntryDetailsDTOS = actCStmtEntryDTOS.get(0).getdEntryDetailsDTOS();

		// test
		// D-Level: Entry Details, 'Betrags-Details'
		Assert.assertNotNull(actDEntryDetailsDTOS);
		Assert.assertEquals(1, actDEntryDetailsDTOS.size());

		Assert.assertNotNull(actDEntryDetailsDTOS.get(0));
		final DEntryDetailsDTO actDEntryDetailsDTO = actDEntryDetailsDTOS.get(0);
		Assert.assertNotNull(actDEntryDetailsDTO.getTransactionDetailsDTOS());
		Assert.assertEquals(0, actDEntryDetailsDTO.getTransactionDetailsDTOS().size());
	}
}