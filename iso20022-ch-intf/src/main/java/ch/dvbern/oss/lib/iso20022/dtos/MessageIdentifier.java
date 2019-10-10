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

import java.time.LocalDateTime;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A-Level: Meldungsebene, «Group Header»
 */
public class MessageIdentifier {

	@Nonnull
	private final String messageIdentification;

	@Nonnull
	private final LocalDateTime creationDateTime;

	@Nonnull
	private final String pageNumber;

	private final boolean isLastPage;

	public MessageIdentifier(
		@Nonnull String messageIdentification,
		@Nonnull LocalDateTime creationDateTime) {
		this(messageIdentification, creationDateTime, "1", true);
	}

	public MessageIdentifier(
		@Nonnull String messageIdentification,
		@Nonnull LocalDateTime creationDateTime,
		@Nonnull String pageNumber,
		boolean isLastPage) {
		this.messageIdentification = messageIdentification;
		this.creationDateTime = creationDateTime;
		this.pageNumber = pageNumber;
		this.isLastPage = isLastPage;
	}

	@Nonnull
	public String getMessageIdentification() {
		return messageIdentification;
	}

	@Nonnull
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	@Nonnull
	public String getPageNumber() {
		return pageNumber;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}

		MessageIdentifier that = (MessageIdentifier) o;

		return isLastPage == that.isLastPage &&
			Objects.equals(messageIdentification, that.messageIdentification) &&
			Objects.equals(creationDateTime, that.creationDateTime) &&
			Objects.equals(pageNumber, that.pageNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(messageIdentification, creationDateTime, pageNumber, isLastPage);
	}

	@Override
	@Nonnull
	public String toString() {
		return "MessageIdentifier{" + "messageIdentification='" + messageIdentification + '\''
			+ ", creationDateTime=" + creationDateTime
			+ ", pageNumber='" + pageNumber + '\''
			+ ", isLastPage=" + isLastPage
			+ '}';
	}
}
