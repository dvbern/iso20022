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

package ch.dvbern.oss.lib.iso20022.pain001.v00103ch02;

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests fuer die Klasse Pain001Service
 * Das generierte Resultat wird in resouces/pain001 gespeichert wenn writeToFile == true
 */
public class Pain001ServiceTest {

	/**
	 * Wenn true werden die Testergebnisse neu in die Testfiles geschrieben.
	 */
	private static final boolean writeToFile = false;

	private static final String storePath = "./src/test/resources/pain001/v00103ch02/pain001ExampleOutput.xml";

	private Pain001V00103CH02Service pain001Service = null;

	private Pain001DTO zahlungsauftrag = null;

	@Before
	public void init() throws JAXBException {
		pain001Service = new Pain001V00103CH02Service();
		Assert.assertNotNull(pain001Service);

		List<AuszahlungDTO> auszahlungen = new ArrayList<>();

		AuszahlungDTO auszahlung1 = new AuszahlungDTO();
		auszahlung1.setBetragTotalZahlung(BigDecimal.TEN);
		auszahlung1.setZahlungsempfaegerBankClearingNumber("POFICHBEXXX");
		auszahlung1.setZahlungsempfaegerIBAN("CH3309000000300008231");
		auszahlung1.setZahlungsempfaegerLand("CH");
		auszahlung1.setZahlungsempfaegerName("Auszahlung 1");
		auszahlung1.setZahlungsempfaegerStrasse("Teststrasse");
		auszahlung1.setZahlungsempfaegerHausnummer("1");
		auszahlung1.setZahlungsempfaegerPlz("3000");
		auszahlung1.setZahlungsempfaegerOrt("Bern");
		auszahlung1.setZahlungText("Auszahlung 1");
		auszahlungen.add(auszahlung1);

		AuszahlungDTO auszahlung2 = new AuszahlungDTO();
		auszahlung2.setBetragTotalZahlung(new BigDecimal(1000));
		auszahlung2.setZahlungsempfaegerBankClearingNumber("POFICHBEXXX");
		auszahlung2.setZahlungsempfaegerIBAN("CH3309000000300008232");
		auszahlung2.setZahlungsempfaegerLand("CH");
		auszahlung2.setZahlungsempfaegerName("Auszahlung 2");
		auszahlung2.setZahlungsempfaegerStrasse("Teststrasse");
		auszahlung2.setZahlungsempfaegerHausnummer("2");
		auszahlung2.setZahlungsempfaegerPlz("4000");
		auszahlung2.setZahlungsempfaegerOrt("Zürich");
		auszahlung2.setZahlungText("Auszahlung 2");
		auszahlungen.add(auszahlung2);

		zahlungsauftrag = new Pain001DTO();
		zahlungsauftrag.setMsgID("Test-ID");
		zahlungsauftrag.setAuszahlungsDatum(LocalDate.now().plusMonths(1));
		zahlungsauftrag.setGenerierungsDatum(LocalDateTime.now().minusDays(5));
		zahlungsauftrag.setSchuldnerBIC("POFICHBEXXX");
		zahlungsauftrag.setSchuldnerIBAN("CH3309000000300008233");
		zahlungsauftrag.setSchuldnerName("Pestalozzi GMBH");

		zahlungsauftrag.setAuszahlungen(auszahlungen);
	}

	@Test
	public void getPainFileContentTest() throws JAXBException {

		final byte[] painFileContent = pain001Service.getPainFileContent(zahlungsauftrag);

		Assert.assertNotNull(painFileContent);
		writeResultsToFile(painFileContent);

		ByteArrayInputStream bis = new ByteArrayInputStream(painFileContent);
		final Document document = getDocumentFromInputStream(bis);
		Assert.assertNotNull(document);

		Assert.assertNotNull(document.getCstmrCdtTrfInitn().getGrpHdr());
		Assert.assertEquals("Test-ID", document.getCstmrCdtTrfInitn().getGrpHdr().getMsgId());
		Assert.assertEquals(zahlungsauftrag.getGenerierungsDatum(), document
				.getCstmrCdtTrfInitn().getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime()
				.toLocalDateTime());
		Assert.assertNotNull(document.getCstmrCdtTrfInitn().getPmtInf());
		Assert.assertEquals(1, document.getCstmrCdtTrfInitn().getPmtInf().size());
		Assert.assertEquals(zahlungsauftrag.getAuszahlungsDatum(), document.getCstmrCdtTrfInitn().getPmtInf().get(0)
				.getReqdExctnDt()
				.toGregorianCalendar().toZonedDateTime().toLocalDate());
	}

	private Document getDocumentFromInputStream(ByteArrayInputStream bis) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);

		final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		JAXBElement<Document> documentJAXBElement = (JAXBElement<Document>) jaxbUnmarshaller.unmarshal(new
				StreamSource(bis), Document.class);
		return documentJAXBElement.getValue();
	}

	/**
	 * Schreibt die berechneten Werte in die Files, wenn writeToFile true ist
	 */
	private void writeResultsToFile(byte[] data) {
		if (writeToFile) {
			try {
				FileOutputStream fos = new FileOutputStream(storePath);
				fos.write(data);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
