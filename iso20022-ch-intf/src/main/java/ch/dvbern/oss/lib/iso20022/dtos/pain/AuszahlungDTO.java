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

import java.math.BigDecimal;

import javax.annotation.Nullable;

public class AuszahlungDTO {

	@Nullable
	private BigDecimal betragTotalZahlung = null;

	@Nullable
	private String zahlungsempfaegerName = null;

	@Nullable
	private String zahlungsempfaegerStrasse = null;

	@Nullable
	private String zahlungsempfaegerHausnummer = null;

	@Nullable
	private String zahlungsempfaegerPlz = null;

	@Nullable
	private String zahlungsempfaegerOrt = null;

	@Nullable
	private String zahlungsempfaegerLand = null;

	@Nullable
	private String zahlungsempfaegerIBAN = null;

	@Nullable
	private String zahlungsempfaegerBankClearingNumber = null;

	@Nullable
	private String zahlungsempfaegerBIC = null;

	@Nullable
	private String zahlungText = null;

	@Nullable
	public BigDecimal getBetragTotalZahlung() {
		return betragTotalZahlung;
	}

	public void setBetragTotalZahlung(@Nullable BigDecimal betragTotalZahlung) {
		this.betragTotalZahlung = betragTotalZahlung;
	}

	@Nullable
	public String getZahlungsempfaegerName() {
		return zahlungsempfaegerName;
	}

	public void setZahlungsempfaegerName(@Nullable String zahlungsempfaegerName) {
		this.zahlungsempfaegerName = zahlungsempfaegerName;
	}

	@Nullable
	public String getZahlungsempfaegerStrasse() {
		return zahlungsempfaegerStrasse;
	}

	public void setZahlungsempfaegerStrasse(@Nullable String zahlungsempfaegerStrasse) {
		this.zahlungsempfaegerStrasse = zahlungsempfaegerStrasse;
	}

	@Nullable
	public String getZahlungsempfaegerHausnummer() {
		return zahlungsempfaegerHausnummer;
	}

	public void setZahlungsempfaegerHausnummer(@Nullable String zahlungsempfaegerHausnummer) {
		this.zahlungsempfaegerHausnummer = zahlungsempfaegerHausnummer;
	}

	@Nullable
	public String getZahlungsempfaegerPlz() {
		return zahlungsempfaegerPlz;
	}

	public void setZahlungsempfaegerPlz(@Nullable String zahlungsempfaegerPlz) {
		this.zahlungsempfaegerPlz = zahlungsempfaegerPlz;
	}

	@Nullable
	public String getZahlungsempfaegerOrt() {
		return zahlungsempfaegerOrt;
	}

	public void setZahlungsempfaegerOrt(@Nullable String zahlungsempfaegerOrt) {
		this.zahlungsempfaegerOrt = zahlungsempfaegerOrt;
	}

	@Nullable
	public String getZahlungsempfaegerLand() {
		return zahlungsempfaegerLand;
	}

	public void setZahlungsempfaegerLand(@Nullable String zahlungsempfaegerLand) {
		this.zahlungsempfaegerLand = zahlungsempfaegerLand;
	}

	@Nullable
	public String getZahlungsempfaegerIBAN() {
		return zahlungsempfaegerIBAN;
	}

	public void setZahlungsempfaegerIBAN(@Nullable String zahlungsempfaegerIBAN) {
		this.zahlungsempfaegerIBAN = zahlungsempfaegerIBAN;
	}

	@Nullable
	public String getZahlungsempfaegerBankClearingNumber() {
		return zahlungsempfaegerBankClearingNumber;
	}

	public void setZahlungsempfaegerBankClearingNumber(@Nullable String zahlungsempfaegerBankClearingNumber) {
		this.zahlungsempfaegerBankClearingNumber = zahlungsempfaegerBankClearingNumber;
	}

	@Nullable
	public String getZahlungsempfaegerBIC() {
		return zahlungsempfaegerBIC;
	}

	public void setZahlungsempfaegerBIC(@Nullable String zahlungsempfaegerBIC) {
		this.zahlungsempfaegerBIC = zahlungsempfaegerBIC;
	}

	@Nullable
	public String getZahlungText() {
		return zahlungText;
	}

	public void setZahlungText(@Nullable String zahlungText) {
		this.zahlungText = zahlungText;
	}
}
