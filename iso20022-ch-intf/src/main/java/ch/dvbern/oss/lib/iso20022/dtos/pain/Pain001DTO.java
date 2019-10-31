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

package ch.dvbern.oss.lib.iso20022.dtos.pain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

public class Pain001DTO {

	private String msgId = UUID.randomUUID().toString(); // by default just in case it is not set

	private String softwareName = null;

	private String schuldnerName = null;

	private String schuldnerBIC = null;

	private String schuldnerIBAN = null;

	private String schuldnerIBANGebuehren = null;

	private LocalDate auszahlungsDatum = null;

	private LocalDateTime generierungsDatum = LocalDateTime.now(); // by default just in case it is not set

	private List<AuszahlungDTO> auszahlungen = new ArrayList<>();

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(@Nonnull String msgId) {
		this.msgId = msgId;
	}

	public String getSchuldnerName() {
		return schuldnerName;
	}

	public void setSchuldnerName(String schuldnerName) {
		this.schuldnerName = schuldnerName;
	}

	public String getSchuldnerBIC() {
		return schuldnerBIC;
	}

	public void setSchuldnerBIC(String schuldnerBIC) {
		this.schuldnerBIC = schuldnerBIC;
	}

	public String getSchuldnerIBAN() {
		return schuldnerIBAN;
	}

	public void setSchuldnerIBAN(String schuldnerIBAN) {
		this.schuldnerIBAN = schuldnerIBAN;
	}

	public String getSchuldnerIBANGebuehren() {
		return schuldnerIBANGebuehren;
	}

	public void setSchuldnerIBANGebuehren(String schuldnerIBANGebuehren) {
		this.schuldnerIBANGebuehren = schuldnerIBANGebuehren;
	}

	public LocalDate getAuszahlungsDatum() {
		return auszahlungsDatum;
	}

	public void setAuszahlungsDatum(LocalDate auszahlungsDatum) {
		this.auszahlungsDatum = auszahlungsDatum;
	}

	public LocalDateTime getGenerierungsDatum() {
		return generierungsDatum;
	}

	public void setGenerierungsDatum(@Nonnull LocalDateTime generierungsDatum) {
		this.generierungsDatum = generierungsDatum;
	}

	public List<AuszahlungDTO> getAuszahlungen() {
		return auszahlungen;
	}

	public void setAuszahlungen(List<AuszahlungDTO> auszahlungen) {
		this.auszahlungen = auszahlungen;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
}
