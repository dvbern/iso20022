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

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.dtos.Pain008DTO;
import ch.dvbern.oss.lib.iso20022.dtos.PaymentInformationDTO;
import ch.dvbern.oss.lib.iso20022.dtos.TransactionInformationDTO;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link Pain008Service}.<br>
 *
 * The resulting XML will be saved to STORE_PATH if WRITE_TO_FILE is set to true.
 */
public class Pain008ServiceTest {

	private static final boolean WRITE_TO_FILE = true;
	private static final String STORE_PATH = "target/pain008Reference.xml";

	private final Pain008V00102CH03Service pain008Service = new Pain008V00102CH03Service();

	@Test
	public void getPainFileContentTest() throws Exception {

		final byte[] painFileContent = pain008Service.getPainFileContent(createDummyDto());

		writeResultsToFile(painFileContent);

		Diff diff = DiffBuilder.compare(getClass().getResource("pain008Reference.xml"))
			.withTest(painFileContent)
			.build();

		assertFalse("Unexpected differences found: " + diff.getDifferences(), diff.hasDifferences());
	}

	private Pain008DTO createDummyDto() {

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
		request1.setDebitorTownName("Bern");
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
		request2.setDebitorTownName("Zurich");
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

		Pain008DTO painDto = new Pain008DTO();
		painDto.setMsgId("Test-ID");
		painDto.setCreationDateTime(LocalDateTime.of(2017, 6, 30, 15, 0));
		painDto.setSoftwareName("DVBern Payment Tool");
		painDto.setInitiatingPartyId("BillerID");
		painDto.setInitiatingPartyName("Test LSV Biller");

		painDto.getPaymentInfo().add(paymentInfo);

		return painDto;
	}

	private void writeResultsToFile(byte[] data) throws IOException {
		if (!WRITE_TO_FILE) {
			return;
		}

		FileOutputStream fos = new FileOutputStream(STORE_PATH);
		fos.write(data);
		fos.close();
	}
}
