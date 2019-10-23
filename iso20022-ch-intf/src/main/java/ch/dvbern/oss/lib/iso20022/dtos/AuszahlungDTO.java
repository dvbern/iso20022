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

public class AuszahlungDTO {

	private BigDecimal betragTotalZahlung = null;

	private String zahlungsempfaegerName = null;

	private String zahlungsempfaegerStrasse = null;

	private String zahlungsempfaegerHausnummer = null;

	private String zahlungsempfaegerPlz = null;

	private String zahlungsempfaegerOrt = null;

	private String zahlungsempfaegerLand = null;

	private String zahlungsempfaegerIBAN = null;

	private String zahlungsempfaegerBankClearingNumber = null;

	private String zahlungText = null;

	public BigDecimal getBetragTotalZahlung() {
		return betragTotalZahlung;
	}

	public void setBetragTotalZahlung(BigDecimal betragTotalZahlung) {
		this.betragTotalZahlung = betragTotalZahlung;
	}

	public String getZahlungsempfaegerName() {
		return zahlungsempfaegerName;
	}

	public void setZahlungsempfaegerName(String zahlungsempfaegerName) {
		this.zahlungsempfaegerName = zahlungsempfaegerName;
	}

	public String getZahlungsempfaegerStrasse() {
		return zahlungsempfaegerStrasse;
	}

	public void setZahlungsempfaegerStrasse(String zahlungsempfaegerStrasse) {
		this.zahlungsempfaegerStrasse = zahlungsempfaegerStrasse;
	}

	public String getZahlungsempfaegerHausnummer() {
		return zahlungsempfaegerHausnummer;
	}

	public void setZahlungsempfaegerHausnummer(String zahlungsempfaegerHausnummer) {
		this.zahlungsempfaegerHausnummer = zahlungsempfaegerHausnummer;
	}

	public String getZahlungsempfaegerPlz() {
		return zahlungsempfaegerPlz;
	}

	public void setZahlungsempfaegerPlz(String zahlungsempfaegerPlz) {
		this.zahlungsempfaegerPlz = zahlungsempfaegerPlz;
	}

	public String getZahlungsempfaegerOrt() {
		return zahlungsempfaegerOrt;
	}

	public void setZahlungsempfaegerOrt(String zahlungsempfaegerOrt) {
		this.zahlungsempfaegerOrt = zahlungsempfaegerOrt;
	}

	public String getZahlungsempfaegerLand() {
		return zahlungsempfaegerLand;
	}

	public void setZahlungsempfaegerLand(String zahlungsempfaegerLand) {
		this.zahlungsempfaegerLand = zahlungsempfaegerLand;
	}

	public String getZahlungsempfaegerIBAN() {
		return zahlungsempfaegerIBAN;
	}

	public void setZahlungsempfaegerIBAN(String zahlungsempfaegerIBAN) {
		this.zahlungsempfaegerIBAN = zahlungsempfaegerIBAN;
	}

	public String getZahlungsempfaegerBankClearingNumber() {
		return zahlungsempfaegerBankClearingNumber;
	}

	public void setZahlungsempfaegerBankClearingNumber(String zahlungsempfaegerBankClearingNumber) {
		this.zahlungsempfaegerBankClearingNumber = zahlungsempfaegerBankClearingNumber;
	}

	public String getZahlungText() {
		return zahlungText;
	}

	public void setZahlungText(String zahlungText) {
		this.zahlungText = zahlungText;
	}
}
