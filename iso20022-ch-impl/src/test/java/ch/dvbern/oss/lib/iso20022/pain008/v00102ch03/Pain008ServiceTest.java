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

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain008DTO;
import ch.dvbern.oss.lib.iso20022.dtos.pain.PaymentInformationDTO;
import ch.dvbern.oss.lib.iso20022.dtos.shared.TransactionInformationDTO;
import org.junit.jupiter.api.Test;

import static ch.dvbern.oss.lib.iso20022.TestUtil.readXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;

public class Pain008ServiceTest {

	private static final String REFERENCE_XML = "ch/dvbern/oss/lib/iso20022/pain008/v00102ch03/pain008Reference.xml";
	private static final String STORE_PATH = "target/pain008TestOutput.xml";

	private final Pain008V00102CH03Service service = new Pain008V00102CH03Service();

	@Test
	public void getPainFileContentTest() throws Exception {

			final byte[] painFileContent = service.getPainFileContent(createDummyDto());

		Files.write(Paths.get(STORE_PATH), painFileContent);

		assertThat(painFileContent, isSimilarTo(readXml(REFERENCE_XML)));
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
		painDto.setSoftwareVersion("V01");
		painDto.setInitiatingPartyId("BillerID");
		painDto.setInitiatingPartyName("Test LSV Biller");

		painDto.getPaymentInfo().add(paymentInfo);

		return painDto;
	}
}
