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

package ch.dvbern.oss.lib.iso20022.camt;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DTransactionDetailsDTO {

	private String amountCurrency = null;
	private BigDecimal amount = null;
	private String creditDebitIndicator = null;
	private String relatedPartiesDebitorName = null;
	private String remittanceCreditorReferenceInformation = null;
	private LocalDate acceptanceDateTime = null;
	private String remittanceInformationAdditional = null;

	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreditDebitIndicator() {
		return creditDebitIndicator;
	}

	public void setCreditDebitIndicator(String creditDebitIndicator) {
		this.creditDebitIndicator = creditDebitIndicator;
	}

	public String getRelatedPartiesDebitorName() {
		return relatedPartiesDebitorName;
	}

	public void setRelatedPartiesDebitorName(String relatedPartiesDebitorName) {
		this.relatedPartiesDebitorName = relatedPartiesDebitorName;
	}

	public String getRemittanceCreditorReferenceInformation() {
		return remittanceCreditorReferenceInformation;
	}

	public void setRemittanceCreditorReferenceInformation(String remittanceCreditorReferenceInformation) {
		this.remittanceCreditorReferenceInformation = remittanceCreditorReferenceInformation;
	}

	public LocalDate getAcceptanceDateTime() {
		return acceptanceDateTime;
	}

	public void setAcceptanceDateTime(LocalDate acceptanceDateTime) {
		this.acceptanceDateTime = acceptanceDateTime;
	}

	public String getRemittanceInformationAdditional() {
		return remittanceInformationAdditional;
	}

	public void setRemittanceInformationAdditional(String remittanceInformationAdditional) {
		this.remittanceInformationAdditional = remittanceInformationAdditional;
	}

	@Override
	public String toString() {
		return "Currency: " + getAmountCurrency() + ", Amount: " + getAmount() + ", CreditDebit Indicator: " +
				getCreditDebitIndicator();
	}
}
