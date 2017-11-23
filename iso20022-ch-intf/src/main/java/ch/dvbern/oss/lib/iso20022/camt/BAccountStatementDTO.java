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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * So called Notification or B-Level (called 'Statement' in camt053)
 * B-Level: Konto-Ebene, «Account Statement» (CH Empfehlungen unterstützen nur ein Konto pro «camt.053»)
 *
 * camt.054: Benachrichtigung von Gutschriften und Belastungen und Sammelbuchungsaufloesung
 */
public class BAccountStatementDTO {

	private final List<CStatementEntryDTO> cStatementEntryDTOS;

	private String identification = null;
	private LocalDateTime creationDateTime = null;
	private String accountIdentificationIBAN = null;


	public BAccountStatementDTO() {
		cStatementEntryDTOS = new ArrayList<>();
	}

	public List<CStatementEntryDTO> getcStatementEntryDTOS() {
		return cStatementEntryDTOS;
	}

	public String getIdentification() {
		return identification;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public String getAccountIdentificationIBAN() {
		return accountIdentificationIBAN;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public void setAccountIdentificationIBAN(String accountIdentificationIBAN) {
		this.accountIdentificationIBAN = accountIdentificationIBAN;
	}

}
