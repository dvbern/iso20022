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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.header;

import jakarta.annotation.Nonnull;

@SuppressWarnings({ "override", "NullableProblems" })
public interface MessagePagination {

	/**
	 * The page number, beginning with "1", is used to count the number of messages
	 * in a statement.
	 */
	@Nonnull
	String getPgNb();

	/**
	 * This element indicates whether the message is the last in the statement. If, on
	 * account of size restrictions, a statement must be divided into more than one
	 * message, this element is marked FALSE in the first messages and TRUE in the last
	 * one. The individual messages belonging to a single "Electronic Sequence
	 * Number" are enumerated using the "Page Number" element (see {@link #getPgNb()}).
	 */
	boolean isLastPgInd();
}
