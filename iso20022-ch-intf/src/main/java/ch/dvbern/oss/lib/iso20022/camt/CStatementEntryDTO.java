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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * So called 'Entry' C-Level
 * One 'buchung' can contain several transactions
 * camt054: always present
 */
public class CStatementEntryDTO {

	private final List<DEntryDetailsDTO> dEntryDetailsDTOS;

	private String entryReference = null; // optional, ISR participant number or ISR-IBAN or BISR-Id
	private String amount = null; // mandatory
	private String amountCurrency = null; // mandatory
	private String creditDebitIndicator = null; // mandatory
	private String status = null; // mandatory
	// Unique reference for the entry, assigned by the financial institution
	private LocalDate bookingDate = null; // optional
	private LocalDate valueDate = null; // Swiss ISO 20022 Payments Standard: Always used. Type3 / Type4: Credit date.
	// mandatory:
	private String bankTransactionCodeDomainCode = null;
	private String bankTransactionCodeDomainFamilyCode = null;
	private String bankTransactionCodeDomainFamilySubFamilyCode = null;

	public List<DEntryDetailsDTO> getdEntryDetailsDTOS() {
		return dEntryDetailsDTOS;
	}

	public CStatementEntryDTO() {
		dEntryDetailsDTOS = new ArrayList<>();
	}

	public String getEntryReference() {
		return entryReference;
	}

	public void setEntryReference(String entryReference) {
		this.entryReference = entryReference;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public String getCreditDebitIndicator() {
		return creditDebitIndicator;
	}

	public void setCreditDebitIndicator(String creditDebitIndicator) {
		this.creditDebitIndicator = creditDebitIndicator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBankTransactionCodeDomainCode() {
		return bankTransactionCodeDomainCode;
	}

	public void setBankTransactionCodeDomainCode(String bankTransactionCodeDomainCode) {
		this.bankTransactionCodeDomainCode = bankTransactionCodeDomainCode;
	}

	public String getBankTransactionCodeDomainFamilyCode() {
		return bankTransactionCodeDomainFamilyCode;
	}

	public void setBankTransactionCodeDomainFamilyCode(String bankTransactionCodeDomainFamilyCode) {
		this.bankTransactionCodeDomainFamilyCode = bankTransactionCodeDomainFamilyCode;
	}

	public String getBankTransactionCodeDomainFamilySubFamilyCode() {
		return bankTransactionCodeDomainFamilySubFamilyCode;
	}

	public void setBankTransactionCodeDomainFamilySubFamilyCode(String bankTransactionCodeDomainFamilySubFamilyCode) {
		this.bankTransactionCodeDomainFamilySubFamilyCode = bankTransactionCodeDomainFamilySubFamilyCode;
	}

	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}

	public LocalDate getValueDate() {
		return valueDate;
	}

	public String toString() {

		return "Currency: " + getAmountCurrency() + ", Amount: " + getAmount() + ", CreditDebit Indicator: " +
				getCreditDebitIndicator();
	}
}
