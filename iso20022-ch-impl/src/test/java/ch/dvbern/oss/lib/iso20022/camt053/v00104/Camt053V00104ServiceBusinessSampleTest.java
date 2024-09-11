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

package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import java.time.LocalDateTime;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.TestUtil;
import ch.dvbern.oss.lib.iso20022.camt.CamtService;
import ch.dvbern.oss.lib.iso20022.camt.CamtServiceBean;
import ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion;
import ch.dvbern.oss.lib.iso20022.dtos.camt.Account;
import ch.dvbern.oss.lib.iso20022.dtos.camt.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.dtos.camt.MessageIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Camt053V00104Service with the file Business Sample camt.053.001.04.xml
 * from iso20022.org (see
 * <a href="https://www.iso20022.org/documents/messages/camt/instances/camt.053.001.04.zip">camt.053.001.04.zip</a>)
 */
public class Camt053V00104ServiceBusinessSampleTest {

	private static final String PATH = "ch/dvbern/oss/lib/iso20022/camt053/v00104/Business Sample camt.053.001.04.xml";

	private final CamtService service = new CamtServiceBean();

	private final LocalDateTime creDtTm = LocalDateTime.of(2010, 10, 18, 17, 0, 0);
	private final DocumentDTO actual = service.getCreditingRecords(TestUtil.readXml(PATH));

	@Test
	public void testDocument() {
		assertEquals(CamtTypeVersion.CAMT053V00104, actual.getCamtTypeVersion());
	}

	@Test
	public void testMessageIdentification() {
		MessageIdentifier messageIdentifier = actual.getMessageIdentifier();

		assertEquals("AAAASESS-FP-STAT001", messageIdentifier.getMessageIdentification());
		assertEquals(creDtTm, messageIdentifier.getCreationDateTime());
		assertEquals("1", messageIdentifier.getPageNumber());
		assertTrue(messageIdentifier.isLastPage());
	}

	@Test
	public void testAccounts() {
		List<Account> accounts = actual.getAccounts();
		assertTrue(accounts.isEmpty());
	}
}
