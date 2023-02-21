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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details;

import java.util.List;

import jakarta.annotation.Nonnull;

@SuppressWarnings({ "override", "NullableProblems" })
public interface EntryDetails {

	/**
	 * Contains booking details for the entry, e.g. the end-to-end identification and
	 * remittance information.
	 * For descriptions, see chapter "Transaction Details (TxDtls, D-Level)".
	 */
	@Nonnull
	<T extends TransactionDetails> List<T> getTxDtls();
}
