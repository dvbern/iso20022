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

import java.time.LocalDateTime;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.TestUtil;
import ch.dvbern.oss.lib.iso20022.camt.CamtService;
import ch.dvbern.oss.lib.iso20022.camt.CamtServiceBean;
import ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion;
import ch.dvbern.oss.lib.iso20022.dtos.camt.Account;
import ch.dvbern.oss.lib.iso20022.dtos.camt.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.dtos.camt.MessageIdentifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Camt054V00104ServiceNoCrdtBookedNotReversedTest {

	private static final String PATH =
		"ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_NoCrdtBookedNotReversed.xml";

	private final CamtService service = new CamtServiceBean();
	private final DocumentDTO actual = service.getCreditingRecords(TestUtil.readXml(PATH));
	private final LocalDateTime creDtTm = LocalDateTime.of(2017, 7, 26, 14, 0, 30);

	@Test
	public void testDocument() {
		assertEquals(CamtTypeVersion.CAMT054V00104, actual.getCamtTypeVersion());
	}

	@Test
	public void testMessageIdentification() {
		MessageIdentifier messageIdentifier = actual.getMessageIdentifier();

		assertEquals("MSGID-201707261400", messageIdentifier.getMessageIdentification());
		assertEquals(creDtTm, messageIdentifier.getCreationDateTime());
		assertEquals("1", messageIdentifier.getPageNumber());
		assertTrue(messageIdentifier.isLastPage());
	}

	@Test
	public void testAccounts() {
		List<Account> accounts = actual.getAccounts();
		assertEquals(1, accounts.size());

		Account account = accounts.get(0);
		assertEquals("NtfctnId20170726", account.getIdentification());
		assertEquals(creDtTm, account.getCreationDateTime());
		assertEquals("CH160077401231234567", account.getAccountIdentificationIBAN());
	}

	@Test
	public void testBooking() {
		assertEquals(0, actual.getAccounts().get(0).getBookings().size());
	}
}
