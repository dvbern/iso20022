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

package ch.dvbern.oss.lib.iso20022.pain001.v00103ch02;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Pain001DTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pain001DTO {

	private String msgID = UUID.randomUUID().toString(); // by default just in case it is not set

	private String softwareName = "DVBern Payment Tool";

	private String schuldnerName = null;

	private String schuldnerBIC = null;

	private String schuldnerIBAN = null;

	private String schuldnerIBAN_gebuehren = null;

	private LocalDate auszahlungsDatum = null;

	private LocalDateTime generierungsDatum = LocalDateTime.now(); // by default just in case it is not set

	private List<AuszahlungDTO> auszahlungen = new ArrayList<>();



	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(@Nonnull String msgID) {
		this.msgID = msgID;
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

	public String getSchuldnerIBAN_gebuehren() {
		return schuldnerIBAN_gebuehren;
	}

	public void setSchuldnerIBAN_gebuehren(String schuldnerIBAN_gebuehren) {
		this.schuldnerIBAN_gebuehren = schuldnerIBAN_gebuehren;
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
