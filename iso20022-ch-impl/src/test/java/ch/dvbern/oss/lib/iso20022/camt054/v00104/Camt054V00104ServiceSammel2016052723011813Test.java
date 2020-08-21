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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.TestUtil;
import ch.dvbern.oss.lib.iso20022.camt.CamtService;
import ch.dvbern.oss.lib.iso20022.camt.CamtServiceBean;
import ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion;
import ch.dvbern.oss.lib.iso20022.dtos.camt.Account;
import ch.dvbern.oss.lib.iso20022.dtos.camt.Booking;
import ch.dvbern.oss.lib.iso20022.dtos.camt.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.dtos.camt.IsrTransaction;
import ch.dvbern.oss.lib.iso20022.dtos.camt.MessageIdentifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the CamtServiceBean with the file camt.054-ES_P_CH0309000000250090342_38000000_0_2016052723011813.xml
 * from PostFinance (see https://www.postfinance.ch/content/dam/pfch/doc/zv/beispiele_testfiles.zip)
 *
 * Sammelbuchungen are ignored as their transaction details have no reference number
 */
public class Camt054V00104ServiceSammel2016052723011813Test {

	private static final String PATH = "ch/dvbern/oss/lib/iso20022/camt054/v00104/"
		+ "camt.054-ES_P_CH0309000000250090342_38000000_0_2016052723011813.xml";

	private final CamtService service = new CamtServiceBean();

	private final LocalDateTime creDtTm = LocalDateTime.of(2016, 5, 27, 23, 0, 57);
	private final DocumentDTO actual = service.getCreditingRecords(TestUtil.readXml(PATH));

	@Test
	public void testDocument() {
		assertEquals(CamtTypeVersion.CAMT054V00104, actual.getCamtTypeVersion());
	}

	@Test
	public void testMessageIdentification() {
		MessageIdentifier messageIdentifier = actual.getMessageIdentifier();

		assertEquals("20160527375204000015644", messageIdentifier.getMessageIdentification());
		assertEquals(creDtTm, messageIdentifier.getCreationDateTime());
		assertEquals("1", messageIdentifier.getPageNumber());
		assertTrue(messageIdentifier.isLastPage());
	}

	@Test
	public void testAccounts() {
		List<Account> accounts = actual.getAccounts();
		assertEquals(1, accounts.size());

		Account account = accounts.get(0);
		assertEquals("20160527375204000015643", account.getIdentification());
		assertEquals(creDtTm, account.getCreationDateTime());
		assertEquals("CH0309000000250090342", account.getAccountIdentificationIBAN());
	}

	@Test
	public void testBooking() {
		List<Booking> bookings = actual.getAccounts().get(0).getBookings();
		assertEquals(1, bookings.size());

		Booking booking = bookings.get(0);
		assertNull(booking.getIsrCustomerNumber());
		assertEquals(LocalDate.of(2016, 5, 27).atStartOfDay(), booking.getBookingDate());
		assertEquals(LocalDate.of(2016, 5, 27).atStartOfDay(), booking.getValueDate());
	}

	@Test
	public void testIsrTransactions() {
		List<IsrTransaction> transactions = actual.getAccounts().get(0).getBookings().get(0).getTransactions();
		assertEquals(0, transactions.size());
	}
}
