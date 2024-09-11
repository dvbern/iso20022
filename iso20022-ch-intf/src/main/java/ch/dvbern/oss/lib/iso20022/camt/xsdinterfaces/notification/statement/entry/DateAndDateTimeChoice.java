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

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.annotation.Nullable;

@SuppressWarnings("override")
public interface DateAndDateTimeChoice {

	/**
	 * If used, then "Date" must not be present.
	 */
	@Nullable
	XMLGregorianCalendar getDtTm();

	/**
	 * If used, then "Date Time" must not be present.
	 */
	@Nullable
	XMLGregorianCalendar getDt();
}
