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

package ch.dvbern.oss.lib.iso20022.dtos.shared;

import java.math.BigDecimal;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;

import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.MESSAGE_PART_ID_LENGTH;
import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.PARTY_NAME_LENGTH;

/**
 * C-Level, Direct debit transaction info (debitor).
 */
public class TransactionInformationDTO {

	/**
	 * {@link RandomStringUtils#random(int, boolean, boolean)} by default.
	 */
	@Nonnull
	@NotNull
	@Size(min = 1, max = MESSAGE_PART_ID_LENGTH)
	private String transactionId = RandomStringUtils.random(MESSAGE_PART_ID_LENGTH, true, true);

	@Nonnull
	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("999999999.99")
	private BigDecimal instructedAmount = BigDecimal.ZERO;

	/**
	 * Identification of debitors financial institution
	 */
	@Nonnull
	@NotNull
	@Pattern(regexp = "[0-9]{3,5}")
	private String debitorIID = "";

	@Nonnull
	@NotNull
	@Size(max = PARTY_NAME_LENGTH)
	private String debitorName = "";

	@Nullable
	private String debitorBuildingNumber = null;

	@Nullable
	private String debitorStreetName = null;

	@Nullable
	private String debitorPostCode = null;

	@Nullable
	private String debitorTownName = null;

	@Nullable
	@Pattern(regexp = "[A-Z]{2}")
	private String debitorCountry = null;

	@Nullable
	@NotNull
	@Pattern(regexp = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}")
	private String debitorIBAN = null;

	/**
	 * ESR reference number.
	 */
	@Nonnull
	@NotNull
	@Pattern(regexp = "[0-9]{27}")
	private String refNr = "";

	@Nonnull
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(@Nonnull String transactionId) {
		this.transactionId = transactionId;
	}

	@Nonnull
	public BigDecimal getInstructedAmount() {
		return instructedAmount;
	}

	public void setInstructedAmount(@Nonnull BigDecimal instructedAmount) {
		this.instructedAmount = instructedAmount;
	}

	@Nonnull
	public String getDebitorIID() {
		return debitorIID;
	}

	public void setDebitorIID(@Nonnull String debitorIID) {
		this.debitorIID = debitorIID;
	}

	@Nonnull
	public String getDebitorName() {
		return debitorName;
	}

	public void setDebitorName(@Nonnull String debitorName) {
		this.debitorName = debitorName;
	}

	@Nullable
	public String getDebitorBuildingNumber() {
		return debitorBuildingNumber;
	}

	public void setDebitorBuildingNumber(@Nullable String debitorBuildingNumber) {
		this.debitorBuildingNumber = debitorBuildingNumber;
	}

	@Nullable
	public String getDebitorStreetName() {
		return debitorStreetName;
	}

	public void setDebitorStreetName(@Nullable String debitorStreetName) {
		this.debitorStreetName = debitorStreetName;
	}

	@Nullable
	public String getDebitorPostCode() {
		return debitorPostCode;
	}

	public void setDebitorPostCode(@Nullable String debitorPostCode) {
		this.debitorPostCode = debitorPostCode;
	}

	@Nullable
	public String getDebitorTownName() {
		return debitorTownName;
	}

	public void setDebitorTownName(@Nullable String debitorTownName) {
		this.debitorTownName = debitorTownName;
	}

	@Nullable
	public String getDebitorCountry() {
		return debitorCountry;
	}

	public void setDebitorCountry(@Nullable String debitorCountry) {
		this.debitorCountry = debitorCountry;
	}

	@Nullable
	public String getDebitorIBAN() {
		return debitorIBAN;
	}

	public void setDebitorIBAN(@Nullable String debitorIBAN) {
		this.debitorIBAN = debitorIBAN;
	}

	@Nonnull
	public String getRefNr() {
		return refNr;
	}

	public void setRefNr(@Nonnull String refNr) {
		this.refNr = refNr;
	}
}
