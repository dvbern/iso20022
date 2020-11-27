/*
 * Copyright 2020. DV Bern AG
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
package ch.dvbern.oss.lib.iso20022.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

/**
 * Konvertiert ein LocalDate Java 8 Objekt in einen String fuer XML
 */
public final class LocalDateXMLConverter  {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private LocalDateXMLConverter() {
	}

	@Nullable
	public static LocalDate parse(String v) {
		return isEmpty(v) ? null : LocalDate.parse(v);
	}

	@Nullable
	public static String print(LocalDate v) {
		return v == null ? null : v.format(DATE_TIME_FORMATTER);
	}

	private static boolean isEmpty(String v) {
		return (v == null || v.isEmpty());
	}
}
