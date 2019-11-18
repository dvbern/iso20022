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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.DateAndDateTimeChoice;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import org.jetbrains.annotations.Contract;

public final class Iso20022Util {

	private static final String[][] SWIFT_REPLACEMENTS = { { "!", "." },
		{ "\"", "." },
		{ "#", "." },
		{ "%", "." },
		{ "&", "+" },
		{ "*", "." },
		{ ";", "." },
		{ ">", "." },
		{ "÷", "." },
		{ "=", "." },
		{ "@", "." },
		{ "_", "." },
		{ "$", "." },
		{ "£", "." },
		{ "[", "." },
		{ "]", "." },
		{ "{", "." },
		{ "}", "." },
		{ "\\", "." },
		{ "`", "." },
		{ "´", "." },
		{ "~", "." },
		{ "à", "a" },
		{ "á", "a" },
		{ "â", "a" },
		{ "ä", "ae" },
		{ "ç", "c" },
		{ "è", "e" },
		{ "é", "e" },
		{ "ê", "e" },
		{ "ë", "e" },
		{ "ì", "i" },
		{ "í", "i" },
		{ "î", "i" },
		{ "ï", "i" },
		{ "ñ", "n" },
		{ "ò", "o" },
		{ "ó", "o" },
		{ "ô", "o" },
		{ "ö", "oe" },
		{ "ù", "u" },
		{ "ú", "u" },
		{ "û", "u" },
		{ "ü", "ue" },
		{ "ý", "Y" },
		{ "ß", "ss" },
		{ "À", "A" },
		{ "Á", "A" },
		{ "Â", "A" },
		{ "Ä", "AE" },
		{ "Ç", "C" },
		{ "È", "E" },
		{ "É", "E" },
		{ "Ê", "E" },
		{ "Ë", "E" },
		{ "Ì", "I" },
		{ "Í", "I" },
		{ "Î", "I" },
		{ "Ï", "I" },
		{ "Ò", "O" },
		{ "Ó", "O" },
		{ "Ô", "O" },
		{ "Ö", "OE" },
		{ "Ù", "U" },
		{ "Ú", "U" },
		{ "Û", "U" },
		{ "Ü", "UE" },
		{ "Ñ", "N" } };

	private static final Pattern SWIFT_EXCLUDE_PATTERN = Pattern.compile("[^A-Za-z0-9+?/\\-:().,' ]");
	private static final Pattern DOUBLE_QUOTE_PATTERN = Pattern.compile("//", Pattern.LITERAL);

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

	/**
	 * SWIFT-Zeichensatz
	 * Folgende, dem SWIFT-Zeichensatz entsprechende Zeichen werden analog den EPCGuidelines
	 * ohne Umwandlung akzeptiert:
	 * a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
	 * A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
	 * 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
	 * . (Punkt)
	 * , (Komma)
	 * : (Doppelpunkt)
	 * ' (Hochkomma)
	 * + (Plus)
	 * - (Minus)
	 * / (Slash)
	 * ( (runde Klammer auf)
	 * ) (runde Klammer zu)
	 * ? (Fragezeichen)
	 * space (Leerzeichen)
	 *
	 * Zeichen mit Umwandlung
	 * Zusätzlich werden für die Schweiz ausgewählte weitere Zeichen zugelassen (spezifiziert
	 * im Anhang C). Diese Zeichen können allenfalls für die nachfolgende Weiterverarbeitung
	 * umgewandelt werden. Werden Zeichen übermittelt, welche im Anhang C nicht
	 * spezifiziert sind, wird die Meldung abgewiesen.
	 *
	 * Zeichensatz für Referenzen
	 * Für gewisse Referenzen sind nur Zeichen aus dem SWIFT-Zeichensatz zugelassen:
	 * - Message Identification (A-Level)
	 * - Payment Information Identification (B-Level)
	 * - Creditor Scheme Identification (Creditor Identifier, B-Level)
	 * - Instruction Identification (C-Level)
	 * - End To End Identification (C-Level)
	 *
	 * Diese Referenzen dürfen zudem nicht mit «/» beginnen und dürfen an keiner Stelle
	 * «//» enthalten.
	 *
	 * @param text to replace by SWIFT
	 * @return text to replaced by SWIFT
	 * @see "https://www.six-group.com/interbank-clearing/dam/downloads/de/standardization/iso/swiss-recommendations
	 * /implementation-guidelines-swiss-dd.pdf"
	 */
	@Nullable
	@Contract("!null->!null; null->null;")
	public static String replaceSwift(@Nullable String text) {
		if (null == text) {
			return null;
		}

		String result = text;

		// Zeichen aus dem Anhang
		for (String[] swiftReplacement : SWIFT_REPLACEMENTS) {
			result = result.replace(swiftReplacement[0], swiftReplacement[1]);
		}

		// Alle anderen Zeichen welche nicht aus REGEX sind werden durch . ersetzt.
		result = SWIFT_EXCLUDE_PATTERN.matcher(result).replaceAll(".");

		// dürfen an keiner Stelle «//» enthalten
		result = DOUBLE_QUOTE_PATTERN.matcher(result).replaceAll(Matcher.quoteReplacement("/"));

		// dürfen zudem nicht mit «/» beginnen
		if (result.startsWith("/")) {
			result = result.substring(1);
		}

		return result;
	}
}
