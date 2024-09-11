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

package ch.dvbern.oss.lib.iso20022.dtos.pain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ch.dvbern.oss.lib.iso20022.dtos.shared.TransactionInformationDTO;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;

import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.MESSAGE_PART_ID_LENGTH;
import static ch.dvbern.oss.lib.iso20022.Iso2022ConstantsUtil.PARTY_NAME_LENGTH;

/**
 * B-Level, payment info (creditor).
 */
public class PaymentInformationDTO {

	/**
	 * {@link RandomStringUtils#random(int, boolean, boolean)} by default.
	 */
	@NotNull
	@Nonnull
	@Size(min = 1, max = MESSAGE_PART_ID_LENGTH)
	private String paymentInfoId = RandomStringUtils.random(MESSAGE_PART_ID_LENGTH, true, true);

	@NotNull
	@Nonnull
	private LocalDate requestedCollectionDate = LocalDate.now();

	@NotNull
	@Nonnull
	@Size(min = 1, max = PARTY_NAME_LENGTH)
	private String creditorName = "";

	@NotNull
	@Nonnull
	@Pattern(regexp = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}")
	private String creditorIBAN = "";

	/**
	 * Identification of creditors financial institution
	 */
	@NotNull
	@Nonnull
	@Pattern(regexp = "[0-9]{3,5}")
	private String creditorIID = "";

	/**
	 * ESR participation number of creditors financial institution
	 */
	@NotNull
	@Nonnull
	@Pattern(regexp = "[0-9]{9}")
	private String institutionEsr = "";

	/**
	 * Creditor LSV+ identification.
	 */
	@NotNull
	@Nonnull
	private String creditorId = "";

	@Valid
	@NotNull
	@Nonnull
	private List<TransactionInformationDTO> transactionInfo = new ArrayList<>();

	@Nonnull
	public String getPaymentInfoId() {
		return paymentInfoId;
	}

	public void setPaymentInfoId(@Nonnull String paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}

	@Nonnull
	public LocalDate getRequestedCollectionDate() {
		return requestedCollectionDate;
	}

	public void setRequestedCollectionDate(@Nonnull LocalDate requestedCollectionDate) {
		this.requestedCollectionDate = requestedCollectionDate;
	}

	@Nonnull
	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(@Nonnull String creditorName) {
		this.creditorName = creditorName;
	}

	@Nonnull
	public String getCreditorIBAN() {
		return creditorIBAN;
	}

	public void setCreditorIBAN(@Nonnull String creditorIBAN) {
		this.creditorIBAN = creditorIBAN;
	}

	@Nonnull
	public String getCreditorIID() {
		return creditorIID;
	}

	public void setCreditorIID(@Nonnull String creditorIID) {
		this.creditorIID = creditorIID;
	}

	@Nonnull
	public String getInstitutionEsr() {
		return institutionEsr;
	}

	public void setInstitutionEsr(@Nonnull String institutionEsr) {
		this.institutionEsr = institutionEsr;
	}

	@Nonnull
	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(@Nonnull String creditorId) {
		this.creditorId = creditorId;
	}

	@Nonnull
	public List<TransactionInformationDTO> getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(@Nonnull List<TransactionInformationDTO> transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
