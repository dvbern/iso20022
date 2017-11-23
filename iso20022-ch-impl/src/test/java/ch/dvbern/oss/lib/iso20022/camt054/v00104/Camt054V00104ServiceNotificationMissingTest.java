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

package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Camt054V00104Service with an xml file that does not contain a notification
 */
public class Camt054V00104ServiceNotificationMissingTest extends Camt054V00104ServiceAbstractTest {

	@Before
	public void setUp() throws Exception {

		setXmlAsBytes("ch/dvbern/oss/lib/iso20022/camt054/v00104/camt_054_Beispiel_NotificationMissing.xml");
	}

	@Test(expected = Iso20022RuntimeException.class)
	public void getDocumentALevelTest() throws Exception {

		aLevelTest();
	}
}