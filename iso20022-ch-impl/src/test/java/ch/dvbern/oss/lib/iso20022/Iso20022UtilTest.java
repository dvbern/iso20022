/*
 * Copyright (c) 2019 DV Bern AG, Switzerland
 *
 * Das vorliegende Dokument, einschliesslich aller seiner Teile, ist urheberrechtlich
 * geschuetzt. Jede Verwertung ist ohne Zustimmung der DV Bern AG unzulaessig. Dies gilt
 * insbesondere fuer Vervielfaeltigungen, die Einspeicherung und Verarbeitung in
 * elektronischer Form. Wird das Dokument einem Kunden im Rahmen der Projektarbeit zur
 * Ansicht uebergeben ist jede weitere Verteilung durch den Kunden an Dritte untersagt.
 *
 */

package ch.dvbern.oss.lib.iso20022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests fuer die Klasse Pain001Service
 * Das generierte Resultat wird in resouces/pain001 gespeichert wenn writeToFile == true
 */
public class Iso20022UtilTest {

	@Test
	public void swiftTest() {

		//String without replacement
		String withoutReplacement =
			"a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F, G, H, I, "
				+ "J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9.,:'+-/()?";
		String replaceSwift = Iso20022Util.replaceSwift(withoutReplacement);
		assertEquals(withoutReplacement, replaceSwift);

		//String with speceial characters replacement -> .
		String withReplacement = "asdfA SDF++* \"13 2456?=)(_:;; M@|#@#|@||@#¼¼½¬|||]¢}";
		String expectedAfterReplacement = "asdfA SDF++. .13 2456?.)(.:.. M.....................";
		replaceSwift = Iso20022Util.replaceSwift(withReplacement);
		assertEquals(expectedAfterReplacement, replaceSwift);

		//String with speceial characters from Switzerland replacement -> .
		String withReplacementCH = "!;>÷=àäöü&";
		String expectedAfterReplacementCH = ".....aaeoeue+";
		replaceSwift = Iso20022Util.replaceSwift(withReplacementCH);
		assertEquals(expectedAfterReplacementCH, replaceSwift);

		//Test with real example
		String withReplacementCH2 = "51/1/Chindä & Co. Luzärn";
		String expectedAfterReplacementCH2 = "51/1/Chindae + Co. Luzaern";
		replaceSwift = Iso20022Util.replaceSwift(withReplacementCH2);
		assertEquals(expectedAfterReplacementCH2, replaceSwift);

		//Test with Slash
		String withReplacementDoubleSlash = "/Nicht mit / beginnen und  an keiner Stelle // enthalten.";
		String expectedAfterReplacementDoubleSlash = "Nicht mit / beginnen und  an keiner Stelle / enthalten.";
		replaceSwift = Iso20022Util.replaceSwift(withReplacementDoubleSlash);
		assertEquals(expectedAfterReplacementDoubleSlash, replaceSwift);

	}

}
