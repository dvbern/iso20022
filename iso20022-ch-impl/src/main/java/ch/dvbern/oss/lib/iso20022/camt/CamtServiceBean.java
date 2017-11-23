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

import ch.dvbern.oss.lib.iso20022.camt053.v00104.Camt053Service;
import ch.dvbern.oss.lib.iso20022.camt053.v00104.Camt053V00104Service;
import ch.dvbern.oss.lib.iso20022.camt054.v00104.Camt054Service;
import ch.dvbern.oss.lib.iso20022.camt054.v00104.Camt054V00104Service;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;

import static ch.dvbern.oss.lib.iso20022.camt.CamtValidateUtility.*;
import static ch.dvbern.oss.lib.iso20022.camt053.v00104.Camt053V00104Service.PATH_CAMT_053_001_04_XSD;
import static ch.dvbern.oss.lib.iso20022.camt054.v00104.Camt054V00104Service.PATH_CAMT_054_001_04_XSD;

public class CamtServiceBean implements CamtService {

	private final Camt053Service camt053V00104Service = new Camt053V00104Service();

	private final Camt054Service camt054V00104Service = new Camt054V00104Service();

	@Override
	public DocumentDTO getDocumentWithBookedEsrPaymentsFromXml(byte[] xmlAsBytes) throws Iso20022RuntimeException {

		if (validateXmlWithXsd(xmlAsBytes, PATH_CAMT_054_001_04_XSD)) {
			return camt054V00104Service.getDocumentWithBookedEsrPaymentsFromXml(xmlAsBytes);
		} else if (validateXmlWithXsd(xmlAsBytes, PATH_CAMT_053_001_04_XSD)) {
			return camt053V00104Service.getDocumentWithBookedEsrPaymentsFromXml(xmlAsBytes);
		} else 	{
			throw new Iso20022RuntimeException("Cannot process input");
		}
	}
}
