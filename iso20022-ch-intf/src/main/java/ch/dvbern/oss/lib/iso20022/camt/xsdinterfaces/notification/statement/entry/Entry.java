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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.EntryDetails;

@SuppressWarnings({ "override", "NullableProblems" })
public interface Entry {

	/**
	 * Standardised procedure:
	 * For ISR/LSV, CH-DD and QR-IBAN entries and for entries with reference type
	 * SCOR, a value is always sent and differs in the kind of batch booking logic that is
	 * applied (for a description of the versions, see Swiss Business Rules [6], chapter
	 * 1.2):
	 * <ul>
	 * <li>Version 1: ISR participant number in the format 010001628
	 * <li>Version 2: ISR participant number and BISR-ID (example: 010001628/123456)
	 * <li>Version 3: RS-PID in the format 41100000000872800
	 * <li>Version 4: QR-IBAN in the format CH4431999123000889012
	 * <li>Version 5: QR-IBAN and the first 6 characters of the QR reference (example: CH4431999123000889012/123456)
	 * <li>Version 6: IBAN in the format CH4412345123000889012
	 * <li>Version 7: IBAN and positions 5 – 10 of the ISO Creditor Reference, capital/lower case lettering is not
	 * relevant for collection (example: CH4412345123000889012/ 123ABC)
	 * </ul>
	 * Non-standardised procedure:
	 * In other cases the “Reference for the account owner” can be sent.
	 *
	 * @since camt.054.001.08.ch.02 Removed ISR specific information, modified QR
	 */
	@Nullable
	String getNtryRef();

	/**
	 * Indicator of credit or debit entry
	 */
	@Nullable
	SharedCreditDebitCode getCdtDbtInd();

	@Nullable
	EntryStatus getSts();

	/**
	 * Corresponds to the booking date.
	 * <ul>
	 * <li>camt.053: Element is always sent.
	 * <li>camt.052: Element may be sent.
	 * <li>camt.054: Element may be sent.
	 * </ul>
	 */
	@Nullable
	DateAndDateTimeChoice getBookgDt();

	/**
	 * Corresponds to the value date.
	 */
	@Nullable
	DateAndDateTimeChoice getValDt();

	@Nonnull
	<T extends EntryDetails> List<T> getNtryDtls();

	/**
	 * Indicator shows whether the entry is a return. It should only be present for a
	 * transaction (Entry) in the following cases:
	 * <ol>
	 * <li>Reversal after SDD (Return/Refund).
	 * <li>Reversal after CH-DD or CH-TA.
	 * <li>Reversal after transfers (refund because a payment could not be credited to the creditor’s financial
	 * institution).
	 * <li>Bank internal cancellation.
	 * </ol>
	 * If the "Credit Debit Indicator" is CRDT and the "Reversal Indicator" is TRUE, then
	 * the original entry was a debit entry.
	 * <p>
	 * If the "Credit Debit Indicator" is DBIT and the "Reversal Indicator" is TRUE, then
	 * the original entry was a credit entry.
	 */
	Boolean isRvslInd();

	default boolean isCreditingEntry() {
		return getCdtDbtInd() != null && getCdtDbtInd().isCreditingEntry();
	}

	default boolean isBookedEntry() {
		return getSts() != null && getSts().isBooked();
	}

	default boolean isReversal() {
		return isRvslInd() != null && isRvslInd();
	}
}
