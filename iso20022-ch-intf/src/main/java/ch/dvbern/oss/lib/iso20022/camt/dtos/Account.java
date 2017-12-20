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

package ch.dvbern.oss.lib.iso20022.camt.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * B-Level: so called 'Notification' in camt054 ('Statement' in camt053)
 * (According to the CH implementation guidelines there should be only one account per «camt.053»,
 * we are supporting a collection of accounts - just in case)
 */
public class Account {

	@Nonnull
	private final DocumentDTO document;

	@Nonnull
	private final String identification;

	@Nonnull
	private final List<Booking> bookings = new ArrayList<>();

	/**
	 * According to the implementation guidelines the electroinc sequence number should be defined.
	 * However, there are sample files, where it is NULL. Thus, we allow NULL as well.
	 */
	@Nullable
	private final BigDecimal electronicSequenceNumber;

	@Nonnull
	private final String accountIdentificationIBAN;

	@Nonnull
	private final LocalDateTime creationDateTime;

	public Account(
		@Nonnull DocumentDTO document,
		@Nonnull String identification,
		@Nullable BigDecimal electronicSequenceNumber,
		@Nonnull String accountIdentificationIBAN,
		@Nonnull LocalDateTime creationDateTime) {
		this.document = document;
		this.identification = identification;
		this.electronicSequenceNumber = electronicSequenceNumber;
		this.accountIdentificationIBAN = accountIdentificationIBAN;
		this.creationDateTime = creationDateTime;
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}

		Account that = (Account) o;

		return Objects.equals(identification, that.identification) &&
			Objects.equals(electronicSequenceNumber, that.electronicSequenceNumber) &&
			Objects.equals(accountIdentificationIBAN, that.accountIdentificationIBAN) &&
			Objects.equals(creationDateTime, that.creationDateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identification, electronicSequenceNumber, accountIdentificationIBAN, creationDateTime);
	}

	@Nonnull
	public List<Booking> getBookings() {
		return bookings;
	}

	@Nonnull
	public DocumentDTO getDocument() {
		return document;
	}

	@Nonnull
	public String getIdentification() {
		return identification;
	}

	@Nullable
	public BigDecimal getElectronicSequenceNumber() {
		return electronicSequenceNumber;
	}

	@Nonnull
	public String getAccountIdentificationIBAN() {
		return accountIdentificationIBAN;
	}

	@Nonnull
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
}
