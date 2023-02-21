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
import ch.dvbern.oss.lib.iso20022.dtos.shared.TransactionInformationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Camt053V00104Service with the file camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml
 * from PostFinance (see <a href="https://www.postfinance.ch/content/dam/pfch/doc/zv/beispiele_testfiles.zip">beispiele_testfiles.zip</a>)
 */
public class Camt053V00104Service2016053100163801Test {

	private static final String PATH =
		"ch/dvbern/oss/lib/iso20022/camt053/v00104/camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml";

	private final CamtService service = new CamtServiceBean();

	private final LocalDateTime creDtTm = LocalDateTime.of(2016, 4, 30, 11, 50, 0);
	private final DocumentDTO actual = service.getCreditingRecords(TestUtil.readXml(PATH));

	@Test
	public void testDocument() {
		assertEquals(CamtTypeVersion.CAMT053V00104, actual.getCamtTypeVersion());
	}

	@Test
	public void testMessageIdentification() {
		MessageIdentifier messageIdentifier = actual.getMessageIdentifier();

		assertEquals("20160430375204000008573", messageIdentifier.getMessageIdentification());
		assertEquals(creDtTm, messageIdentifier.getCreationDateTime());
		assertEquals("1", messageIdentifier.getPageNumber());
		assertTrue(messageIdentifier.isLastPage());
	}

	@Test
	public void testAccounts() {
		List<Account> accounts = actual.getAccounts();
		assertEquals(1, accounts.size());

		Account account = accounts.get(0);
		assertEquals("20160430375204000008574", account.getIdentification());
		assertEquals(creDtTm, account.getCreationDateTime());
		assertEquals("CH0309000000250090342", account.getAccountIdentificationIBAN());
	}

	@Test
	public void testBooking() {
		List<Booking> bookings = actual.getAccounts().get(0).getBookings();
		assertEquals(5, bookings.size());

		Booking booking1 = bookings.get(0);
		assertEquals("010000004", booking1.getIsrCustomerNumber());
		assertEquals(LocalDate.of(2016, 5, 27).atStartOfDay(), booking1.getBookingDate());
		assertEquals(LocalDate.of(2016, 5, 30).atStartOfDay(), booking1.getValueDate());

		Booking booking2 = bookings.get(1);
		assertNull(booking2.getIsrCustomerNumber());
		assertEquals(LocalDate.of(2016, 5, 27).atStartOfDay(), booking2.getBookingDate());
		assertEquals(LocalDate.of(2016, 5, 27).atStartOfDay(), booking2.getValueDate());

		Booking booking3 = bookings.get(2);
		assertNull(booking2.getIsrCustomerNumber());
		assertEquals(LocalDate.of(2016, 4, 6).atStartOfDay(), booking3.getBookingDate());
		assertEquals(LocalDate.of(2016, 3, 23).atStartOfDay(), booking3.getValueDate());

		Booking booking4 = bookings.get(3);
		assertEquals("41107767420881932", booking4.getIsrCustomerNumber());
		assertEquals(LocalDate.of(2016, 4, 10).atStartOfDay(), booking4.getBookingDate());
		assertEquals(LocalDate.of(2016, 4, 10).atStartOfDay(), booking4.getValueDate());

		Booking booking5 = bookings.get(4);
		assertEquals("41107767420881932", booking5.getIsrCustomerNumber());
		assertEquals(LocalDate.of(2016, 4, 10).atStartOfDay(), booking5.getBookingDate());
		assertEquals(LocalDate.of(2016, 4, 10).atStartOfDay(), booking5.getValueDate());
	}

	@Test
	public void testIsrTransactions() {
		List<Booking> bookings = actual.getAccounts().get(0).getBookings();

		assertEquals(10, bookings.get(0).getTransactions().size());
		assertTrue(bookings.get(1).getTransactions().isEmpty());
		assertTrue(bookings.get(2).getTransactions().isEmpty());
		assertTrue(bookings.get(3).getTransactions().isEmpty());
		assertTrue(bookings.get(4).getTransactions().isEmpty());
	}

	@Test
	public void testTransactionDetails() {
		List<IsrTransaction> transactions = actual.getAccounts().get(0).getBookings().get(0).getTransactions();
		long transactionDetailsCount = transactions.stream()
			.filter(isrTransaction -> isrTransaction.getTransactionDetails() != null)
			.count();

		assertEquals(4, transactionDetailsCount);

		TransactionInformationDTO transactionInformationDTO = transactions.get(5).getTransactionDetails();

		assertNotNull(transactionInformationDTO);
		assertEquals("Bernasconi Maria", transactionInformationDTO.getDebitorName());
		assertEquals("12", transactionInformationDTO.getDebitorBuildingNumber());
		assertEquals("Place de la Gare", transactionInformationDTO.getDebitorStreetName());
		assertEquals("2502", transactionInformationDTO.getDebitorPostCode());
		assertEquals("Biel/Bienne", transactionInformationDTO.getDebitorTownName());
		assertEquals("CH", transactionInformationDTO.getDebitorCountry());
		assertEquals("CH4444444444444444444", transactionInformationDTO.getDebitorIBAN());
	}
}
