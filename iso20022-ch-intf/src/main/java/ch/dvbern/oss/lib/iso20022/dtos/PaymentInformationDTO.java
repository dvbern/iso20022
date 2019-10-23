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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ch.dvbern.oss.lib.iso20022.dtos.TransactionInformationDTO;
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
	@Size(min = 1, max = MESSAGE_PART_ID_LENGTH)
	private String paymentInfoId = RandomStringUtils.random(MESSAGE_PART_ID_LENGTH, true, true);

	@NotNull
	private LocalDate requestedCollectionDate = null;

	@NotNull
	@Size(min = 1, max = PARTY_NAME_LENGTH)
	private String creditorName = null;

	@NotNull
	@Pattern(regexp = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}")
	private String creditorIBAN = null;

	/**
	 * Identification of creditors financial institution
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{3,5}")
	private String creditorIID = null;

	/**
	 * ESR participation number of creditors financial institution
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{9}")
	private String institutionEsr = null;

	/**
	 * Creditor LSV+ identification.
	 */
	@NotNull
	private String creditorId = null;

	@Valid
	private List<TransactionInformationDTO> transactionInfo = new ArrayList<>();

	public String getPaymentInfoId() {
		return paymentInfoId;
	}

	public void setPaymentInfoId(String paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}

	public LocalDate getRequestedCollectionDate() {
		return requestedCollectionDate;
	}

	public void setRequestedCollectionDate(LocalDate requestedCollectionDate) {
		this.requestedCollectionDate = requestedCollectionDate;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorIBAN() {
		return creditorIBAN;
	}

	public void setCreditorIBAN(String creditorIBAN) {
		this.creditorIBAN = creditorIBAN;
	}

	public String getCreditorIID() {
		return creditorIID;
	}

	public void setCreditorIID(String creditorIID) {
		this.creditorIID = creditorIID;
	}

	public String getInstitutionEsr() {
		return institutionEsr;
	}

	public void setInstitutionEsr(String institutionEsr) {
		this.institutionEsr = institutionEsr;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public List<TransactionInformationDTO> getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(List<TransactionInformationDTO> transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
