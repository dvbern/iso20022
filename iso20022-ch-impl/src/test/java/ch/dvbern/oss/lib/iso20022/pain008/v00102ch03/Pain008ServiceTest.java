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

package ch.dvbern.oss.lib.iso20022.pain008.v00102ch03;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.six_interbank_clearing.de.pain_008_001_02_ch_03.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link Pain008Service}
 */
public class Pain008ServiceTest {

	private static final String STORE_PATH = "target/pain008ExampleOutput.xml";

	private Pain008V00102CH03Service pain008Service = new Pain008V00102CH03Service();

	private Pain008DTO painDto;

	@Before
	public void init() {

		List<TransactionInformationDTO> paymentRequests = new ArrayList<>();

		TransactionInformationDTO request1 = new TransactionInformationDTO();
		request1.setTransactionId("ID-1");
		request1.setInstructedAmount(BigDecimal.TEN);
		request1.setDebitorIID("01234");
		request1.setDebitorIBAN("CH9300762011623852957");
		request1.setDebitorName("Zahlung 1");
		request1.setDebitorCountry("CH");
		request1.setDebitorStreetName("Teststrasse");
		request1.setDebitorPostCode("3000");
		request1.setDebitorTown("Bern");
		request1.setRefNr("000123456789012345678901234");
		paymentRequests.add(request1);


		TransactionInformationDTO request2 = new TransactionInformationDTO();
		request2.setTransactionId("ID-2");
		request2.setInstructedAmount(new BigDecimal(1000));
		request2.setDebitorIID("05678");
		request2.setDebitorIBAN("CH9300762011623852957");
		request2.setDebitorName("Zahlung 2");
		request2.setDebitorCountry("CH");
		request2.setDebitorStreetName("Teststrasse");
		request2.setDebitorPostCode("4000");
		request2.setDebitorTown("Zurich");
		request2.setRefNr("100123456789012345678901234");
		paymentRequests.add(request2);


		TransactionInformationDTO requestWithouAddr = new TransactionInformationDTO();
		requestWithouAddr.setTransactionId("ID-3");
		requestWithouAddr.setInstructedAmount(new BigDecimal(1000));
		requestWithouAddr.setDebitorIID("15678");
		requestWithouAddr.setDebitorIBAN("CH9300762011623852957");
		requestWithouAddr.setDebitorName("no address");
		requestWithouAddr.setRefNr("100123456789012345678901234");
		paymentRequests.add(requestWithouAddr);


		PaymentInformationDTO paymentInfo = new PaymentInformationDTO();
		paymentInfo.setPaymentInfoId("Test-ID");
		paymentInfo.setRequestedCollectionDate(LocalDate.of(2017, 7, 10));
		paymentInfo.setInstitutionEsr("CreditorEsrNr");
		paymentInfo.setCreditorId("CreditorLsvID");
		paymentInfo.setCreditorIID("CreditorInstId");
		paymentInfo.setCreditorName("Pestalozzi GMBH");
		paymentInfo.setCreditorIBAN("CH9300762011623852957");
		paymentInfo.setTransactionInfo(paymentRequests);


		painDto = new Pain008DTO();
		painDto.setMsgId("Test-ID");
		painDto.setCreationDateTime(LocalDateTime.of(2017, 6, 30, 15, 0));
		painDto.setSoftwareName("DVBern Payment Tool");
		painDto.setInitiatingPartyId("BillerID");
		painDto.setInitiatingPartyName("Test LSV Biller");

		painDto.getPaymentInfo().add(paymentInfo);
	}

	@Test
	public void getPainFileContentTestAndWriteToFile() throws JAXBException, IOException {

		final byte[] painFileContent = pain008Service.getPainFileContent(painDto);

		assertNotNull(painFileContent);
		writeResultsToFile(painFileContent);

		ByteArrayInputStream bis = new ByteArrayInputStream(painFileContent);
		final Document document = getDocumentFromInputStream(bis);
		assertNotNull(document);

		assertNotNull(document.getCstmrDrctDbtInitn().getGrpHdr());
		assertEquals("Test-ID", document.getCstmrDrctDbtInitn().getGrpHdr().getMsgId());
		assertEquals("3", document.getCstmrDrctDbtInitn().getGrpHdr().getNbOfTxs());
		assertTrue(new BigDecimal(2010).compareTo(document.getCstmrDrctDbtInitn().getGrpHdr().getCtrlSum()) == 0);
		assertEquals(1, document.getCstmrDrctDbtInitn().getPmtInf().size());
		assertEquals(3, document.getCstmrDrctDbtInitn().getPmtInf().get(0).getDrctDbtTxInf().size());
	}

	private Document getDocumentFromInputStream(ByteArrayInputStream bis) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);

		final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		JAXBElement<Document> documentJAXBElement = jaxbUnmarshaller.unmarshal(new StreamSource(bis), Document.class);
		return documentJAXBElement.getValue();
	}

	/**
	 * Write date to File
	 */
	private void writeResultsToFile(byte[] data) throws IOException {
		FileOutputStream fos = new FileOutputStream(STORE_PATH);
		fos.write(data);
		fos.close();
	}

}
