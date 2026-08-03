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

package ch.dvbern.oss.lib.iso20022.pain001.v00109ch03;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.dtos.pain.AuszahlungDTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain001DTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain001SoftwareDTO;
import org.junit.jupiter.api.Test;

import static ch.dvbern.oss.lib.iso20022.TestUtil.readXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;

public class Pain001ServiceTest {

	private static final String REFERENCE_XML = "ch/dvbern/oss/lib/iso20022/pain001/v00109ch03/pain001TestReference.xml";
	private static final String STORE_PATH = "target/pain001TestOutput.xml";

	private final Pain001V00109CH03Service service = new Pain001V00109CH03Service();

	/**
	 * <h4>How to validate the reference file on official test platforms</h4>
	 *
	 * <h5>General requirements</h5>
	 * <ul>
	 *     <li>Register at the test platform</li>
	 *     <li>Configure the debtor account, matching {@link AuszahlungDTO::setSchuldnerIBAN}</li>
	 *     <li>Set a payment date {@link AuszahlungDTO::setAuszahlungsdatum} in the future</li>
	 * </ul>
	 *
	 * <h5>Postfinance</h5>
	 * <ol>
	 *     <li>
	 *         Register at
	 *         <a href="https://isotest.postfinance.ch/corporates/">https://isotest.postfinance.ch/corporates/</a></li>
	 *     <li>Under <strong>Settings -> Accounts</strong>, configure the debtor account (CH9300762011623852957)</li>
	 *     <li>Under <strong>Product Use</strong> set offer 2 (pain V09, camt V08, SPS 2025)</li>
	 *     <li>Under <strong>Payment files</strong> upload the file {@code target/pain001TestOutput.xml} to the
	 *     platform</li>
	 *     <li>Download the ZIP result and study the protocol file.</li>
	 * </ol>
	 *
	 * <h5>ZKB</h5>
	 * <ol>
	 *     <li>Register at <a href="https://testplattform.zkb.ch/">https://testplattform.zkb.ch/</a></li>
	 *     <li>Under <strong>Settings -> Accounts</strong>, configure the debtor account (CH9300762011623852957)</li>
	 *     <li>Under <strong>Validation/simulation</strong> select the file {@code target/pain001TestOutput.xml} and
	 *     upload</li>
	 *     <li>Download the ZIP result and study the protocol file.</li>
	 * </ol>
	 */
	@Test
	public void generated_file_should_match_with_validated_template() throws Exception {

		final byte[] painFileContent = service.getPainFileContent(createDummyDto());

		Files.write(Paths.get(STORE_PATH), painFileContent);

		assertThat(painFileContent, isSimilarTo(readXml(REFERENCE_XML)));
	}

	private Pain001DTO createDummyDto() {
		List<AuszahlungDTO> auszahlungen = new ArrayList<>();

		AuszahlungDTO auszahlung1 = new AuszahlungDTO();
		auszahlung1.setBetragTotalZahlung(BigDecimal.TEN);
		auszahlung1.setZahlungsempfaengerBIC("ZKBKCHZZ80A");
		auszahlung1.setZahlungsempfaengerIBAN("CH4821966000009613388");
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
		auszahlung2.setZahlungsempfaengerBankClearingNumber("700");
		auszahlung2.setZahlungsempfaengerIBAN("CH4821966000009613388");
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
		auszahlung3.setZahlungsempfaengerBIC("POFICHBEXXX");
		auszahlung3.setZahlungsempfaengerIBAN("CH7280005000088877766");
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
		auszahlungWithout.setZahlungsempfaengerBIC("POFICHBEXXX");
		auszahlungWithout.setZahlungsempfaengerIBAN("CH7280005000088877766");
		auszahlungWithout.setZahlungsempfaengerName("Auszahlung Without address");
		auszahlungen.add(auszahlungWithout);

		Pain001DTO zahlungsauftrag = new Pain001DTO();
		zahlungsauftrag.setMsgId("Test-ID");
		zahlungsauftrag.setSoftwareDetails(new Pain001SoftwareDTO(
			"DVBern Payment Tool",
			"DV Bern AG",
			"V01"
		));
		zahlungsauftrag.setAuszahlungsDatum(LocalDate.of(2026, 8, 28));
		zahlungsauftrag.setGenerierungsDatum(LocalDateTime.of(2026, 7, 28, 10, 15).minusDays(5));
		zahlungsauftrag.setSchuldnerBIC("ZKBKCHZZ80A");
		zahlungsauftrag.setSchuldnerIBAN("CH9300762011623852957");
		zahlungsauftrag.setSchuldnerName("Pestalozzi GMBH");

		zahlungsauftrag.setAuszahlungen(auszahlungen);

		return zahlungsauftrag;
	}

}
