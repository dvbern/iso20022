/*
 * Copyright (C) 2019 DV Bern AG, Switzerland
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

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ch.dvbern.oss.lib.iso20022.dtos.shared.TransactionInformationDTO;

public class IsrTransaction {

	@Nonnull
	private final Booking booking;

	@Nonnull
	private final String amountCurrency;

	@Nonnull
	private final BigDecimal amount;

	@Nonnull
	private final String referenceNumber;

	@Nullable
	private TransactionInformationDTO transactionDetails = null;

	public IsrTransaction(
		@Nonnull Booking booking,
		@Nonnull String amountCurrency,
		@Nonnull BigDecimal amount,
		@Nonnull String referenceNumber,
		@Nullable TransactionInformationDTO transactionInformationDTO) {
		this.booking = booking;
		this.amountCurrency = amountCurrency;
		this.amount = amount;
		this.referenceNumber = referenceNumber;
		this.transactionDetails = transactionInformationDTO;
	}

	@Override
	@Nonnull
	public String toString() {
		return "Transaction{" + "amountCurrency='" + amountCurrency + '\''
			+ ", amount=" + amount
			+ ", referenceNumber='" + referenceNumber + '\''
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

		IsrTransaction that = (IsrTransaction) o;

		return Objects.equals(amountCurrency, that.amountCurrency) &&
			Objects.equals(amount, that.amount) &&
			Objects.equals(referenceNumber, that.referenceNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountCurrency, amount, referenceNumber);
	}

	@Nonnull
	public Booking getBooking() {
		return booking;
	}

	@Nonnull
	public String getAmountCurrency() {
		return amountCurrency;
	}

	@Nonnull
	public BigDecimal getAmount() {
		return amount;
	}

	@Nonnull
	public String getReferenceNumber() {
		return referenceNumber;
	}

	@Nullable
	public TransactionInformationDTO getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(@Nullable TransactionInformationDTO transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
}
