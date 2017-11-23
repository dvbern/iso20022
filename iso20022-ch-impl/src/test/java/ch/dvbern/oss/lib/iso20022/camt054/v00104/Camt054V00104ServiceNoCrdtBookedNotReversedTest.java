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

import java.util.List;

import ch.dvbern.oss.lib.iso20022.camt.BAccountStatementDTO;
import ch.dvbern.oss.lib.iso20022.camt.DocumentDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Camt054V00104Service with the file camt_054_Beispiel_ZA1_ESR_ZE.xml from SIX Interbank Clearing (see
 * www.six-interbank-clearing.com/dam/downloads/de/standardization/iso/swiss-recommendations/swiss-usage-guide
 * -examples.zip)
 */
public class Camt054V00104ServiceNoCrdtBookedNotReversedTest extends Camt054V00104ServiceAbstractTest {

	@Before
	public void setUp() throws Exception {

		setXmlAsBytes("ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_NoCrdtBookedNotReversed.xml");

		// A-Level: Group Header, 'Meldungsebene'
		// not used in this test

		// B-Level: Account Statement, 'Konto-Ebene' (CH Recommendations support only one account per 'camt.053')
		// not used in this test

		// C-Level: Statement Entry, 'Betrags-Ebene'
		// none

		// D-Level: Entry Details, 'Betrags-Details'
		// none

		getDocumentDTO().getbAccountStatementsDTO().add(getbAccountStatementDTO());
		getDocumentDTO().setaGroupHeaderDTO(getaHeaderDTO());
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

		// test
		Assert.assertNotNull(actBAccStatements);
		Assert.assertEquals(getDocumentDTO().getbAccountStatementsDTO().size(), actBAccStatements.size());

		final BAccountStatementDTO actBAccountStatementDTO = actBAccStatements.get(0);
		Assert.assertNotNull(actBAccountStatementDTO);
		Assert.assertEquals(0 , actBAccountStatementDTO.getcStatementEntryDTOS().size());
	}
}