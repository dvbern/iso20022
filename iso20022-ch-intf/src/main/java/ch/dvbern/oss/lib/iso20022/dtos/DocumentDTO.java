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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion;

public class DocumentDTO {

	@Nonnull
	private final CamtTypeVersion camtTypeVersion;
	@Nonnull
	private final MessageIdentifier messageIdentifier;
	@Nonnull
	private final List<Account> accounts = new ArrayList<>();

	public DocumentDTO(
		@Nonnull CamtTypeVersion camtTypeVersion,
		@Nonnull MessageIdentifier messageIdentifier) {
		this.camtTypeVersion = camtTypeVersion;
		this.messageIdentifier = messageIdentifier;
	}

	@Override
	@Nonnull
	public String toString() {
		return "DocumentDTO{" + "camtTypeVersion=" + camtTypeVersion
			+ ", messageIdentifier=" + messageIdentifier
			+ '}';
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}

		DocumentDTO that = (DocumentDTO) o;

		return camtTypeVersion == that.camtTypeVersion &&
			Objects.equals(messageIdentifier, that.messageIdentifier) &&
			Objects.equals(accounts, that.accounts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(camtTypeVersion, messageIdentifier, accounts);
	}

	@Nonnull
	public CamtTypeVersion getCamtTypeVersion() {
		return camtTypeVersion;
	}

	@Nonnull
	public MessageIdentifier getMessageIdentifier() {
		return messageIdentifier;
	}

	@Nonnull
	public List<Account> getAccounts() {
		return accounts;
	}
}
