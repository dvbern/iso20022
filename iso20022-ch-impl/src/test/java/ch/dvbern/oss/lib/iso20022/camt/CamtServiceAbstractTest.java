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

package ch.dvbern.oss.lib.iso20022.camt;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

public abstract class CamtServiceAbstractTest {

	private byte[] xmlAsBytes = null;

	private final DocumentDTO documentDTO = new DocumentDTO();
	private final AGroupHeaderDTO aHeaderDTO = new AGroupHeaderDTO();
	private final BAccountStatementDTO bAccountStatementDTO = new BAccountStatementDTO();
	private final CStatementEntryDTO cStatementEntryDTO = new CStatementEntryDTO();

	private final AbstractCamtService camtService = null;

	protected void setXmlAsBytes(String filePath) throws IOException {
		InputStream xmlAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		setXmlAsBytes(IOUtils.toByteArray(xmlAsStream));
		xmlAsStream.close();
	}

	protected void aLevelTest() {

		// run
		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());

		// test
		// A-Level: Group Header, 'Meldungsebene' (1..1)
		Assert.assertNotNull(actDocDTO);
		Assert.assertNotNull(actDocDTO.getaGroupHeaderDTO());

		AGroupHeaderDTO actHeader = actDocDTO.getaGroupHeaderDTO();
		Assert.assertEquals(aHeaderDTO.getMessageIdentification(), actHeader.getMessageIdentification());
		Assert.assertEquals(aHeaderDTO.getCreationDateTime(), actHeader.getCreationDateTime());
	}

	protected void bLevelTest() {
		// prepare
		List<BAccountStatementDTO> expAccStatements = documentDTO.getbAccountStatementsDTO();

		// run
		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
		List<BAccountStatementDTO> actBAccStatements = actDocDTO.getbAccountStatementsDTO();

		// test
		// B-Level: Account Statement, 'Konto-Ebene' (1..n)
		Assert.assertNotNull(actBAccStatements);
		Assert.assertEquals(expAccStatements.size(), actBAccStatements.size());
		Assert.assertNotNull(actBAccStatements.get(0));

		final BAccountStatementDTO actBAccountStatementDTO = actBAccStatements.get(0);
		Assert.assertEquals(bAccountStatementDTO.getIdentification(), actBAccountStatementDTO.getIdentification());
		Assert.assertEquals(bAccountStatementDTO.getCreationDateTime(), actBAccountStatementDTO
				.getCreationDateTime());
		Assert.assertEquals(bAccountStatementDTO.getAccountIdentificationIBAN(), actBAccountStatementDTO
				.getAccountIdentificationIBAN());
	}

	protected void cLevelTest() {
		// run
		final DocumentDTO actDocDTO = getCamtService().getDocumentWithBookedEsrPaymentsFromXml(getXmlAsBytes());
		List<BAccountStatementDTO> actBAccStatements = actDocDTO.getbAccountStatementsDTO();
		List<CStatementEntryDTO> actCStatementEntryDTOS = actBAccStatements.get(0).getcStatementEntryDTOS();

		// test
		/* C-Level: Betrags-Ebene, 'Statement Entry' */
		Assert.assertNotNull(actCStatementEntryDTOS);
		Assert.assertEquals(getbAccountStatementDTO().getcStatementEntryDTOS().size(), actCStatementEntryDTOS.size());

		if (!getbAccountStatementDTO().getcStatementEntryDTOS().isEmpty()) {
			CStatementEntryDTO actEntryDTO = actCStatementEntryDTOS.get(0);
			testAssertionsCLevel(cStatementEntryDTO, actEntryDTO);
		}
	}

	protected void testAssertionsCLevel(CStatementEntryDTO expCEntryDTO, CStatementEntryDTO actCEntryDTO) {
		Assert.assertEquals(expCEntryDTO.getEntryReference(), actCEntryDTO.getEntryReference());
		Assert.assertEquals(expCEntryDTO.getAmountCurrency(), actCEntryDTO.getAmountCurrency());
		Assert.assertEquals(expCEntryDTO.getAmount(), actCEntryDTO.getAmount());
		Assert.assertEquals(expCEntryDTO.getCreditDebitIndicator(), actCEntryDTO.getCreditDebitIndicator());
		Assert.assertEquals(expCEntryDTO.getStatus(), actCEntryDTO.getStatus());
		Assert.assertEquals(expCEntryDTO.getBankTransactionCodeDomainCode(), actCEntryDTO
				.getBankTransactionCodeDomainCode());
		Assert.assertEquals(expCEntryDTO.getBankTransactionCodeDomainFamilyCode(), actCEntryDTO
				.getBankTransactionCodeDomainFamilyCode());
		Assert.assertEquals(expCEntryDTO.getBankTransactionCodeDomainFamilySubFamilyCode(), actCEntryDTO
				.getBankTransactionCodeDomainFamilySubFamilyCode());
		if (actCEntryDTO.getBookingDate() != null) {
			Assert.assertEquals(expCEntryDTO.getBookingDate(), actCEntryDTO.getBookingDate());
		}
		if (actCEntryDTO.getValueDate() != null) {
			Assert.assertEquals(expCEntryDTO.getValueDate(), actCEntryDTO.getValueDate());
		}
	}


	public byte[] getXmlAsBytes() {
		return xmlAsBytes;
	}

	public void setXmlAsBytes(byte[] xmlAsBytes) {
		this.xmlAsBytes = xmlAsBytes;
	}

	public DocumentDTO getDocumentDTO() {
		return documentDTO;
	}

	public AGroupHeaderDTO getaHeaderDTO() {
		return aHeaderDTO;
	}

	public BAccountStatementDTO getbAccountStatementDTO() {
		return bAccountStatementDTO;
	}

	public CStatementEntryDTO getcStatementEntryDTO() {
		return cStatementEntryDTO;
	}

	public AbstractCamtService getCamtService() {
		return camtService;
	}
}
