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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Pain001DTO {

	@Nonnull
	private String msgId = UUID.randomUUID().toString(); // by default just in case it is not set

	@Nullable
	private String softwareName = null;

	@Nullable
	private String schuldnerName = null;

	@Nullable
	private String schuldnerBIC = null;

	@Nullable
	private String schuldnerIBAN = null;

	@Nullable
	private String schuldnerIBANGebuehren = null;

	@Nullable
	private LocalDate auszahlungsDatum = null;

	@Nullable
	private LocalDateTime generierungsDatum = LocalDateTime.now(); // by default just in case it is not set

	@Nonnull
	private List<AuszahlungDTO> auszahlungen = new ArrayList<>();

	@Nonnull
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(@Nonnull String msgId) {
		this.msgId = msgId;
	}

	@Nullable
	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(@Nullable String softwareName) {
		this.softwareName = softwareName;
	}

	@Nullable
	public String getSchuldnerName() {
		return schuldnerName;
	}

	public void setSchuldnerName(@Nullable String schuldnerName) {
		this.schuldnerName = schuldnerName;
	}

	@Nullable
	public String getSchuldnerBIC() {
		return schuldnerBIC;
	}

	public void setSchuldnerBIC(@Nullable String schuldnerBIC) {
		this.schuldnerBIC = schuldnerBIC;
	}

	@Nullable
	public String getSchuldnerIBAN() {
		return schuldnerIBAN;
	}

	public void setSchuldnerIBAN(@Nullable String schuldnerIBAN) {
		this.schuldnerIBAN = schuldnerIBAN;
	}

	@Nullable
	public String getSchuldnerIBANGebuehren() {
		return schuldnerIBANGebuehren;
	}

	public void setSchuldnerIBANGebuehren(@Nullable String schuldnerIBANGebuehren) {
		this.schuldnerIBANGebuehren = schuldnerIBANGebuehren;
	}

	@Nullable
	public LocalDate getAuszahlungsDatum() {
		return auszahlungsDatum;
	}

	public void setAuszahlungsDatum(@Nullable LocalDate auszahlungsDatum) {
		this.auszahlungsDatum = auszahlungsDatum;
	}

	@Nullable
	public LocalDateTime getGenerierungsDatum() {
		return generierungsDatum;
	}

	public void setGenerierungsDatum(@Nullable LocalDateTime generierungsDatum) {
		this.generierungsDatum = generierungsDatum;
	}

	@Nonnull
	public List<AuszahlungDTO> getAuszahlungen() {
		return auszahlungen;
	}

	public void setAuszahlungen(@Nonnull List<AuszahlungDTO> auszahlungen) {
		this.auszahlungen = auszahlungen;
	}
}
