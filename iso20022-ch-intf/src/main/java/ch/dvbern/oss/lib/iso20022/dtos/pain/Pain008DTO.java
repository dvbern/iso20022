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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Pain008DTO {

	/**
	 * {@link UUID#randomUUID()} by default.
	 */
	@NotNull
	@Nonnull
	private String msgId = UUID.randomUUID().toString();

	/**
	 * {@link LocalDateTime#now()} by default.
	 */
	@NotNull
	@Nonnull
	private LocalDateTime creationDateTime = LocalDateTime.now();

	private String initiatingPartyName = null;

	/**
	 * LSV+ ID of the initiating party.
	 */
	@NotNull
	@Size(min = 1)
	@Nonnull
	private String initiatingPartyId = "";

	private String softwareName = null;

	@Valid
	@NotNull
	@Nonnull
	private List<PaymentInformationDTO> paymentInfo = new ArrayList<>();

	@Nonnull
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(@Nonnull String msgId) {
		this.msgId = msgId;
	}

	@Nonnull
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(@Nonnull LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public String getInitiatingPartyName() {
		return initiatingPartyName;
	}

	public void setInitiatingPartyName(String initiatingPartyName) {
		this.initiatingPartyName = initiatingPartyName;
	}

	@Nonnull
	public String getInitiatingPartyId() {
		return initiatingPartyId;
	}

	public void setInitiatingPartyId(@Nonnull String initiatingPartyId) {
		this.initiatingPartyId = initiatingPartyId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	@Nonnull
	public List<PaymentInformationDTO> getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(@Nonnull List<PaymentInformationDTO> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
}
