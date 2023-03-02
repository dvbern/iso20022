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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * The "Group Header" (A-Level of the message) contains information about the
 * message. It occurs once.
 */
@SuppressWarnings({ "override", "NullableProblems" })
public interface GroupHeader {

	/**
	 * Unique message reference which is assigned by the sender of the message.
	 */
	@Nonnull
	String getMsgId();

	@Nullable
	MessagePagination getMsgPgntn();

	/**
	 * Date and time when the message was created
	 */
	@Nonnull
	XMLGregorianCalendar getCreDtTm();
}
