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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.Entry;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.account.Account;

/**
 * Only one instance will be provided, one account per "camt" message. Details
 * about the statement for which the following information is being delivered. This
 * level is described as follows in the various other "camt" message types:
 * <ul>
 * <li>camt.053: Element name is "Statement", &lt;Stmt&gt;
 * <li>camt.052: Element name is "Report", &lt;Rpt&gt;
 * <li>camt.054: Element name is "Notification", &lt;Ntfcn&gt;
 * </ul>
 * The message type determines what this element contains:
 * <ul>
 * <li>camt.053: Report on balances and transactions on an account
 * <li>camt.052: Report on movement within a particular period
 * <li>camt.054: Notification of credits and debits and batch booking breakdown
 * </ul>
 * Sub-elements also apply to "camt.052" (Report) and "camt.054" (Notification),
 * unless mentioned explicitly.
 */
@SuppressWarnings({ "override", "NullableProblems" })
public interface StatementOrNotification {

	/**
	 * Unique Statement Identification. This ID is unique for a period of at least one calendar year.
	 */
	@Nonnull
	String getId();

	/**
	 * This field is mandatory for camt.052/camt.053 and shows the current statement
	 * number for each message type and each account. It begins each year with 1 and
	 * always increments in ascending sequence.
	 */
	@Nullable
	BigDecimal getElctrncSeqNb();

	/**
	 * Information about the account, its owner and the financial institution.
	 */
	@Nonnull
	Account getAcct();

	/**
	 * Date and time when the message was created
	 */
	@Nonnull
	XMLGregorianCalendar getCreDtTm();

	/**
	 * Detailed information about a single entry
	 * Is always sent, provided at least 1 account movement has taken place. If there
	 * has been no account movement and only account balances are being reported,
	 * this element is not sent.
	 * <ul>
	 * <li>camt.052/053: This element is optional.
	 * <li>camt.054: This element is always sent.
	 * </ul>
	 * Description of the sub-elements as per chapter 4.1.4
	 * CH-Definition adjusted to reference new chapter number (textual change only)
	 */
	@Nonnull
	<T extends Entry> List<T> getNtry();
}
