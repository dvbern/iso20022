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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Schema 2019: Content Model changed
 */
public interface EntryStatus {

	String BOOK = "BOOK";

	/**
	 * Status of an entry
	 * Swiss financial institutions offer the following codes:
	 * <ul>
	 * <li>BOOK (Booked)
	 * <li>PDNG (Pending)
	 * </ul>
	 * camt.053: Only BOOK is sent.
	 * camt.052/054: BOOK and PDNG may be sent.
	 *
	 * @since camt.054.001.08.ch.02
	 */
	@Nullable
	default String getCd() {
		return null;
	}

	/**
	 * Same as {@link #getCd()}, but implemented as enum.
	 */
	@Nullable
	default String value() {
		return null;
	}

	@Nonnull
	default Boolean isBooked() {
		return BOOK.equalsIgnoreCase(getCd()) || BOOK.equals(value());
	}
}
