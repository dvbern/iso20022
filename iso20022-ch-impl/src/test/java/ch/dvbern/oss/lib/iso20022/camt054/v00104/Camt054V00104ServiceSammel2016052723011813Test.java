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
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.camt.BAccountStatementDTO;
import ch.dvbern.oss.lib.iso20022.camt.CStatementEntryDTO;
import ch.dvbern.oss.lib.iso20022.camt.DEntryDetailsDTO;
import ch.dvbern.oss.lib.iso20022.camt.DocumentDTO;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Camt054V00104Service with the file camt.054-ES_P_CH0309000000250090342_38000000_0_2016052723011813.xml
 * from PostFinance (see https://www.postfinance.ch/content/dam/pfch/doc/zv/beispiele_testfiles.zip)
 *
 * Sammelbuchungen are ignored as their transaction details have no reference number
 */
public class Camt054V00104ServiceSammel2016052723011813Test extends Camt054V00104ServiceAbstractTest {

	@Before
	public void setUp() throws IOException {

		InputStream xmlAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
				("ch/dvbern/oss/lib/iso20022/camt054/v00104"
						+ "/camt.054-ES_P_CH0309000000250090342_38000000_0_2016052723011813.xml");
		setXmlAsBytes(IOUtils.toByteArray(xmlAsStream));

		LocalDateTime creDtTm = LocalDateTime.of(2016, 5, 27, 23, 0, 57);

		// A-Level: Group Header, 'Meldungsebene'
		getaHeaderDTO().setMessageIdentification("20160527375204000015644");
		getaHeaderDTO().setCreationDateTime(creDtTm);

		// B-Level: Account Statement, 'Konto-Ebene' (CH Recommendations support only one account per 'camt.054')
		getbAccountStatementDTO().setIdentification("20160527375204000015643");
		getbAccountStatementDTO().setCreationDateTime(creDtTm);
		getbAccountStatementDTO().setAccountIdentificationIBAN("CH0309000000250090342");

//		getcStatementEntryDTO().getdEntryDetailsDTOS().add(this.expDEntryDetailsDTO);
//		getbAccountStatementDTO().getcStatementEntryDTOS().add(getcStatementEntryDTO());
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
		Assert.assertEquals(1 , actFirstCEntryDTOS.size());

		CStatementEntryDTO actFirstCEntryDTO = actFirstCEntryDTOS.get(0);
		Assert.assertNotNull(actFirstCEntryDTO);
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

		Assert.assertNotNull(actDEntryDetailsDTO);
		// TransactionDetails without ReferenceNumber are skipped
		Assert.assertEquals(0, actDEntryDetailsDTO.getTransactionDetailsDTOS().size());
	}
}