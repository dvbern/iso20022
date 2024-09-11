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

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.parties.RelatedParties;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.remittenceinformation.RemittanceInformation;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@SuppressWarnings({ "override", "NullableProblems" })
public interface TransactionDetails {

	/**
	 * References to the original transaction for which these details are being sent.
	 */
	@Nullable
	References getRefs();

	/**
	 * Transaction amount
	 * Note: the currency is always sent as an attribute of the “Amount” element.
	 */
	@Nonnull
	Amount getAmt();

	/**
	 * Related parties, where known, can be shown on the statement.
	 * Sub-elements are as per the ISO standard. Listed below are those elements
	 * which are uniformly understood and sent by all Swiss financial institutions.
	 * <p>
	 * In the case of R-transactions, the parties involved (Creditor/Debtor, Ultimate
	 * Creditor/Ultimate Debtor) retain their roles from the original transaction.
	 */
	@Nullable
	RelatedParties getRltdPties();

	@Nullable
	RemittanceInformation getRmtInf();
}
