/*
 * Copyright (C) 2023 DV Bern AG, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.dvbern.oss.lib.iso20022.dtos.camt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * So called 'Entry' C-Level.
 * One booking can contain several transactions.
 */
public class Booking {

	@Nonnull
	private final Account account;

	@Nonnull
	private final LocalDateTime bookingDate;

	@Nonnull
	private final LocalDateTime valueDate;
	/**
	 * ISR participant number in the format 010001628 (Postfinance) or BISR/BESR-identification (Banks).
	 */
	@Nullable
	private final String isrCustomerNumber;

	@Nonnull
	private final List<IsrTransaction> transactions = new ArrayList<>();

	public Booking(
		@Nonnull Account account,
		@Nonnull LocalDateTime bookingDate,
		@Nonnull LocalDateTime valueDate,
		@Nullable String isrCustomerNumber) {
		this.account = account;
		this.bookingDate = bookingDate;
		this.valueDate = valueDate;
		this.isrCustomerNumber = isrCustomerNumber;
	}

	@Override
	@Nonnull
	public String toString() {
		return "StatementEntry{" + "bookingDate=" + bookingDate
			+ ", valueDate=" + valueDate
			+ ", isrCustomerNumber='" + isrCustomerNumber + '\''
			+ '}';
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o) {
			return true;

		}
		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}

		Booking that = (Booking) o;
		return Objects.equals(bookingDate, that.bookingDate) &&
			Objects.equals(valueDate, that.valueDate) &&
			Objects.equals(isrCustomerNumber, that.isrCustomerNumber) &&
			Objects.equals(transactions, that.transactions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookingDate, valueDate, isrCustomerNumber, transactions);
	}

	@Nonnull
	public Account getAccount() {
		return account;
	}

	@Nonnull
	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	@Nonnull
	public LocalDateTime getValueDate() {
		return valueDate;
	}

	@Nullable
	public String getIsrCustomerNumber() {
		return isrCustomerNumber;
	}

	@Nonnull
	public List<IsrTransaction> getTransactions() {
		return transactions;
	}
}
