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
	private String zahlungsempfaengerName = null;

	@Nullable
	private String zahlungsempfaengerStrasse = null;

	@Nullable
	private String zahlungsempfaengerHausnummer = null;

	@Nullable
	private String zahlungsempfaengerPlz = null;

	@Nullable
	private String zahlungsempfaengerOrt = null;

	@Nullable
	private String zahlungsempfaengerLand = null;

	@Nullable
	private String zahlungsempfaengerIBAN = null;

	@Nullable
	private String zahlungsempfaengerBankClearingNumber = null;

	@Nullable
	private String zahlungsempfaengerBIC = null;

	@Nullable
	private String zahlungText = null;

	@Nullable
	private String endToEndId = null;

	@Nullable
	public BigDecimal getBetragTotalZahlung() {
		return betragTotalZahlung;
	}

	public void setBetragTotalZahlung(@Nullable BigDecimal betragTotalZahlung) {
		this.betragTotalZahlung = betragTotalZahlung;
	}

	@Nullable
	public String getZahlungsempfaengerName() {
		return zahlungsempfaengerName;
	}

	public void setZahlungsempfaengerName(@Nullable String zahlungsempfaengerName) {
		this.zahlungsempfaengerName = zahlungsempfaengerName;
	}

	@Nullable
	public String getZahlungsempfaengerStrasse() {
		return zahlungsempfaengerStrasse;
	}

	public void setZahlungsempfaengerStrasse(@Nullable String zahlungsempfaengerStrasse) {
		this.zahlungsempfaengerStrasse = zahlungsempfaengerStrasse;
	}

	@Nullable
	public String getZahlungsempfaengerHausnummer() {
		return zahlungsempfaengerHausnummer;
	}

	public void setZahlungsempfaengerHausnummer(@Nullable String zahlungsempfaengerHausnummer) {
		this.zahlungsempfaengerHausnummer = zahlungsempfaengerHausnummer;
	}

	@Nullable
	public String getZahlungsempfaengerPlz() {
		return zahlungsempfaengerPlz;
	}

	public void setZahlungsempfaengerPlz(@Nullable String zahlungsempfaengerPlz) {
		this.zahlungsempfaengerPlz = zahlungsempfaengerPlz;
	}

	@Nullable
	public String getZahlungsempfaengerOrt() {
		return zahlungsempfaengerOrt;
	}

	public void setZahlungsempfaengerOrt(@Nullable String zahlungsempfaengerOrt) {
		this.zahlungsempfaengerOrt = zahlungsempfaengerOrt;
	}

	@Nullable
	public String getZahlungsempfaengerLand() {
		return zahlungsempfaengerLand;
	}

	public void setZahlungsempfaengerLand(@Nullable String zahlungsempfaengerLand) {
		this.zahlungsempfaengerLand = zahlungsempfaengerLand;
	}

	@Nullable
	public String getZahlungsempfaengerIBAN() {
		return zahlungsempfaengerIBAN;
	}

	public void setZahlungsempfaengerIBAN(@Nullable String zahlungsempfaengerIBAN) {
		this.zahlungsempfaengerIBAN = zahlungsempfaengerIBAN;
	}

	@Nullable
	public String getZahlungsempfaengerBankClearingNumber() {
		return zahlungsempfaengerBankClearingNumber;
	}

	public void setZahlungsempfaengerBankClearingNumber(@Nullable String zahlungsempfaengerBankClearingNumber) {
		this.zahlungsempfaengerBankClearingNumber = zahlungsempfaengerBankClearingNumber;
	}

	@Nullable
	public String getZahlungsempfaengerBIC() {
		return zahlungsempfaengerBIC;
	}

	public void setZahlungsempfaengerBIC(@Nullable String zahlungsempfaengerBIC) {
		this.zahlungsempfaengerBIC = zahlungsempfaengerBIC;
	}

	@Nullable
	public String getZahlungText() {
		return zahlungText;
	}

	public void setZahlungText(@Nullable String zahlungText) {
		this.zahlungText = zahlungText;
	}

	@Nullable
	public String getEndToEndId() {
		return endToEndId;
	}

	public void setEndToEndId(@Nullable String endToEndId) {
		this.endToEndId = endToEndId;
	}
}
