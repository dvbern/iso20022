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
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IsrTransaction {

	@Nonnull
	private final String amountCurrency;
	@Nonnull
	private final BigDecimal amount;
	@Nonnull
	private final String referenceNumber;

	public IsrTransaction(
		@Nonnull String amountCurrency,
		@Nonnull BigDecimal amount,
		@Nonnull String referenceNumber) {
		this.amountCurrency = amountCurrency;
		this.amount = amount;
		this.referenceNumber = referenceNumber;
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
}
