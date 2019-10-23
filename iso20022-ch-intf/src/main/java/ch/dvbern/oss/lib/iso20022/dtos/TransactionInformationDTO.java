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

package ch.dvbern.oss.lib.iso20022.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@NotNull
	@Size(min = 1, max = MESSAGE_PART_ID_LENGTH)
	private String transactionId = RandomStringUtils.random(MESSAGE_PART_ID_LENGTH, true, true);

	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("999999999.99")
	private BigDecimal instructedAmount = null;

	/**
	 * Identification of debitors financial institution
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{3,5}")
	private String debitorIID = null;

	@NotNull
	@Size(max = PARTY_NAME_LENGTH)
	private String debitorName = null;

	private String debitorBuildingNumber = null;

	private String debitorStreetName = null;

	private String debitorPostCode = null;

	private String debitorTownName = null;

	@Pattern(regexp = "[A-Z]{2}")
	private String debitorCountry = null;

	@NotNull
	@Pattern(regexp = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}")
	private String debitorIBAN = null;

	/**
	 * ESR reference number.
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{27}")
	private String refNr = null;

	public BigDecimal getInstructedAmount() {
		return instructedAmount;
	}

	public void setInstructedAmount(BigDecimal instructedAmount) {
		this.instructedAmount = instructedAmount;
	}

	public String getDebitorIID() {
		return debitorIID;
	}

	public void setDebitorIID(String debitorIID) {
		this.debitorIID = debitorIID;
	}

	public String getDebitorName() {
		return debitorName;
	}

	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}

	public String getDebitorIBAN() {
		return debitorIBAN;
	}

	public void setDebitorIBAN(String debitorIBAN) {
		this.debitorIBAN = debitorIBAN;
	}

	public String getRefNr() {
		return refNr;
	}

	public void setRefNr(String refNr) {
		this.refNr = refNr;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDebitorStreetName() {
		return debitorStreetName;
	}

	public void setDebitorStreetName(String debitorStreetName) {
		this.debitorStreetName = debitorStreetName;
	}

	public String getDebitorPostCode() {
		return debitorPostCode;
	}

	public void setDebitorPostCode(String debitorPostCode) {
		this.debitorPostCode = debitorPostCode;
	}

	public String getDebitorTownName() {
		return debitorTownName;
	}

	public void setDebitorTownName(String debitorTownName) {
		this.debitorTownName = debitorTownName;
	}

	public String getDebitorCountry() {
		return debitorCountry;
	}

	public void setDebitorCountry(String debitorCountry) {
		this.debitorCountry = debitorCountry;
	}

	public String getDebitorBuildingNumber() {
		return debitorBuildingNumber;
	}

	public void setDebitorBuildingNumber(String debitorBuildingNumber) {
		this.debitorBuildingNumber = debitorBuildingNumber;
	}
}
