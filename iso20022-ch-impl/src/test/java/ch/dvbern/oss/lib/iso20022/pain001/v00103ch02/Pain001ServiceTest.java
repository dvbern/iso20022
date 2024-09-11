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

import javax.xml.transform.stream.StreamSource;

import ch.dvbern.oss.lib.iso20022.dtos.pain.AuszahlungDTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain001DTO;
import com.six_interbank_clearing.de.pain_001_001_03_ch_02.Document;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests fuer die Klasse Pain001Service
 * Das generierte Resultat wird in resouces/pain001 gespeichert wenn writeToFile == true
 */
public class Pain001ServiceTest {

	// Das File sollte mit pain001TestReference.xml verglichen werden
	private static final String STORE_PATH = "target/pain001ExampleOutput.xml";

	private Pain001V00103CH02Service pain001Service = null;

	private Pain001DTO zahlungsauftrag = null;

	@BeforeEach
	public void init() {
		pain001Service = new Pain001V00103CH02Service();
		assertNotNull(pain001Service);

		List<AuszahlungDTO> auszahlungen = new ArrayList<>();

		AuszahlungDTO auszahlung1 = new AuszahlungDTO();
		auszahlung1.setBetragTotalZahlung(BigDecimal.TEN);
		auszahlung1.setZahlungsempfaengerBankClearingNumber("POFICHBEXXX");
		auszahlung1.setZahlungsempfaengerIBAN("CH9300762011623852957");
		auszahlung1.setZahlungsempfaengerLand("CH");
		auszahlung1.setZahlungsempfaengerName("Auszahlung 1");
		auszahlung1.setZahlungsempfaengerStrasse("Teststrasse");
		auszahlung1.setZahlungsempfaengerHausnummer("1");
		auszahlung1.setZahlungsempfaengerPlz("3000");
		auszahlung1.setZahlungsempfaengerOrt("Bern");
		auszahlung1.setZahlungText("Auszahlung 1");
		auszahlungen.add(auszahlung1);

		AuszahlungDTO auszahlung2 = new AuszahlungDTO();
		auszahlung2.setBetragTotalZahlung(new BigDecimal(1000));
		auszahlung2.setZahlungsempfaengerBankClearingNumber("POFICHBEXXX");
		auszahlung2.setZahlungsempfaengerIBAN("CH9300762011623852957");
		auszahlung2.setZahlungsempfaengerLand("CH");
		auszahlung2.setZahlungsempfaengerName("Auszahlung 2");
		auszahlung2.setZahlungsempfaengerStrasse("Teststrasse");
		auszahlung2.setZahlungsempfaengerHausnummer("2");
		auszahlung2.setZahlungsempfaengerPlz("4000");
		auszahlung2.setZahlungsempfaengerOrt("Zürich");
		auszahlungen.add(auszahlung2);

		// Auszahlung mit zu langem Kontoinhaber-Namen
		AuszahlungDTO auszahlung3 = new AuszahlungDTO();
		auszahlung3.setBetragTotalZahlung(new BigDecimal(1000));
		auszahlung3.setZahlungsempfaengerBankClearingNumber("POFICHBEXXX");
		auszahlung3.setZahlungsempfaengerIBAN("CH9300762011623852957");
		auszahlung3.setZahlungsempfaengerLand("CH");
		auszahlung3.setZahlungsempfaengerName(
			"Auszahlung 3 mit viiiiiel zu langem Namen, der ist sooo lang dass es gar nicht alles rein passt ins "
				+ "Feld");
		auszahlung3.setZahlungsempfaengerStrasse("Teststrasse");
		auszahlung3.setZahlungsempfaengerHausnummer("2");
		auszahlung3.setZahlungsempfaengerPlz("4000");
		auszahlung3.setZahlungsempfaengerOrt("Zürich");
		auszahlungen.add(auszahlung3);

		AuszahlungDTO auszahlungWithout = new AuszahlungDTO();
		auszahlungWithout.setBetragTotalZahlung(new BigDecimal(2000));
		auszahlungWithout.setZahlungsempfaengerBankClearingNumber("POFICHBEXXX");
		auszahlungWithout.setZahlungsempfaengerIBAN("CH9300762011623852957");
		auszahlungWithout.setZahlungsempfaengerName("Auszahlung Without address");
		auszahlungen.add(auszahlungWithout);

		zahlungsauftrag = new Pain001DTO();
		zahlungsauftrag.setMsgId("Test-ID");
		zahlungsauftrag.setSoftwareName("DVBern Payment Tool");
		zahlungsauftrag.setAuszahlungsDatum(LocalDate.of(2017, 6, 30));
		zahlungsauftrag.setGenerierungsDatum(LocalDateTime.of(2017, 8, 9, 10, 15).minusDays(5));
		zahlungsauftrag.setSchuldnerBIC("POFICHBEXXX");
		zahlungsauftrag.setSchuldnerIBAN("CH9300762011623852957");
		zahlungsauftrag.setSchuldnerName("Pestalozzi GMBH");

		zahlungsauftrag.setAuszahlungen(auszahlungen);
	}

	@Test
	public void getPainFileContentTestAndWriteToFile() throws JAXBException, IOException {

		final byte[] painFileContent = pain001Service.getPainFileContent(zahlungsauftrag);

		assertNotNull(painFileContent);
		writeResultsToFile(painFileContent);

		ByteArrayInputStream bis = new ByteArrayInputStream(painFileContent);
		final Document document = getDocumentFromInputStream(bis);
		assertNotNull(document);

		assertNotNull(document.getCstmrCdtTrfInitn().getGrpHdr());
		assertEquals("Test-ID", document.getCstmrCdtTrfInitn().getGrpHdr().getMsgId());

		LocalDateTime actualGenerierungsDatum = document.getCstmrCdtTrfInitn().getGrpHdr().getCreDtTm()
			.toGregorianCalendar()
			.toZonedDateTime()
			.toLocalDateTime();
		assertEquals(zahlungsauftrag.getGenerierungsDatum(), actualGenerierungsDatum);

		assertNotNull(document.getCstmrCdtTrfInitn().getPmtInf());
		assertEquals(1, document.getCstmrCdtTrfInitn().getPmtInf().size());

		LocalDate actualAuszahlungsDatum = document.getCstmrCdtTrfInitn().getPmtInf().get(0).getReqdExctnDt();
		assertEquals(zahlungsauftrag.getAuszahlungsDatum(), actualAuszahlungsDatum);
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
