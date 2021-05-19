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

package ch.dvbern.oss.lib.iso20022.camt;

import org.junit.jupiter.api.Test;

import static ch.dvbern.oss.lib.iso20022.TestUtil.readXml;
import static ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion.CAMT053V00104;
import static ch.dvbern.oss.lib.iso20022.camt.CamtTypeVersion.CAMT054V00104;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the validation of an xml with its xsd
 */
public class CamtUtilTest {

	private static final String VALID_CAMT54_XML =
		"ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_ZA1_ESR_ZE.xml";
	private static final String VALID_CAMT53_XML =
		"ch/dvbern/oss/lib/iso20022/camt053/v00104/camt.053_P_CH0309000000250090342_380000000_0_2016053100163801.xml";
	private static final String INVALID_XML =
		"ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_NotificationMissing.xml";

	@Test
	public void validateXmlWithXsdSuccessTest() {
		assertTrue(CamtUtil.isMatchingXsdSchema(readXml(VALID_CAMT53_XML), CAMT053V00104.getXsdPath()));
		assertTrue(CamtUtil.isMatchingXsdSchema(readXml(VALID_CAMT54_XML), CAMT054V00104.getXsdPath()));
	}

	@Test
	public void validateXmlWithXsdFailureTest() {
		byte[] bytes = readXml(INVALID_XML);

		assertFalse(CamtUtil.isMatchingXsdSchema(bytes, CAMT054V00104.getXsdPath()));
		assertFalse(CamtUtil.isMatchingXsdSchema(readXml(VALID_CAMT53_XML), CAMT054V00104.getXsdPath()));
		assertFalse(CamtUtil.isMatchingXsdSchema(readXml(VALID_CAMT54_XML), CAMT053V00104.getXsdPath()));
	}

	@Test
	public void detectDetectCamtTypeVersionTest() {
		assertEquals(CAMT053V00104, CamtUtil.detectCamtTypeVersion(readXml(VALID_CAMT53_XML)));
		assertEquals(CAMT054V00104, CamtUtil.detectCamtTypeVersion(readXml(VALID_CAMT54_XML)));
	}
}
