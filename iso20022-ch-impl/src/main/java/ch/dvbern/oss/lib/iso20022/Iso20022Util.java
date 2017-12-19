/*
 * Copyright 2017 DV Bern AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * limitations under the License.
 */

package ch.dvbern.oss.lib.iso20022;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.DateAndDateTimeChoice;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;

public final class Iso20022Util {

	private Iso20022Util() {
		// utility
	}

	/**
	 * Uses the time zone Europe/Paris for the conversion
	 */
	@Nonnull
	public static XMLGregorianCalendar toXmlGregorianCalendar(@Nonnull LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.of("Europe/Paris");

		return toXmlGregorianCalendar(localDateTime.atZone(zoneId));
	}

	@Nonnull
	public static XMLGregorianCalendar toXmlGregorianCalendar(@Nonnull ZonedDateTime zonedDateTime) {
		GregorianCalendar gc = GregorianCalendar.from(zonedDateTime);

		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			throw new Iso20022RuntimeException("Unexpected error while converting to XMLGregorianCalendar", e);
		}
	}

	@Nonnull
	public static LocalDateTime from(@Nonnull XMLGregorianCalendar calendar) {
		return calendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
	}

	@Nullable
	public static LocalDateTime from(@Nullable DateAndDateTimeChoice choice) {
		if (choice == null) {
			return null;
		}

		if (choice.getDtTm() != null) {
			return from(choice.getDtTm());
		}

		if (choice.getDt() != null) {
			return from(choice.getDt());
		}

		return null;
	}
}
