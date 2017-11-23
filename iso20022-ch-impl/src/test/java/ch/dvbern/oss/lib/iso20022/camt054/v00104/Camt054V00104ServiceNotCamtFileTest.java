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

import java.time.LocalDateTime;

import ch.dvbern.oss.lib.iso20022.camt.CStatementEntryDTO;
import ch.dvbern.oss.lib.iso20022.camt.DEntryDetailsDTO;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Camt054V00104Service with a file that does not contain a camt message: pain001ExampleOutput.xml
 */
public class Camt054V00104ServiceNotCamtFileTest extends Camt054V00104ServiceAbstractTest {

	private final CStatementEntryDTO expCStatementEntryDTO = new CStatementEntryDTO();
	private final DEntryDetailsDTO expDEntryDetailsDTO = new DEntryDetailsDTO();

	@Before
	public void setUp() throws Exception {

		setXmlAsBytes("ch/dvbern/oss/lib/iso20022/pain001/v00103ch02/pain001ExampleOutput.xml");

		LocalDateTime creDtTm = LocalDateTime.of(2015, 1, 5, 7, 30, 0);

		// A-Level: Group Header, 'Meldungsebene'
		getaHeaderDTO().setMessageIdentification("MSG-01");
		getaHeaderDTO().setCreationDateTime(creDtTm);

		expCStatementEntryDTO.getdEntryDetailsDTOS().add(this.expDEntryDetailsDTO);
		getbAccountStatementDTO().getcStatementEntryDTOS().add(expCStatementEntryDTO);
		getDocumentDTO().getbAccountStatementsDTO().add(getbAccountStatementDTO());
		getDocumentDTO().setaGroupHeaderDTO(getaHeaderDTO());
	}

	@Test(expected = Iso20022RuntimeException.class)
	public void getDocumentAsDtoALevelTest() throws Exception {

		aLevelTest();
	}

	@Test(expected = Iso20022RuntimeException.class)
	public void getDocumentAsDtoBLevelTest() throws Exception {

		bLevelTest();
	}

	@Test(expected = Iso20022RuntimeException.class)
	public void getDocumentWithBookedEsrPaymentsFromXmlTest() throws Exception {

		// run
		getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
	}

	@Test(expected = Iso20022RuntimeException.class)
	public void getBankToCustomerDebitCreditNotificationV04Test() throws Exception {

		// run
		getCamtService().getNotificationFromXml(getXmlAsBytes());
	}
}