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

import javax.annotation.Nonnull;

import ch.dvbern.oss.lib.iso20022.camt.dtos.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;

public interface CamtService {

	/**
	 * Service to read ESR payments to swiss financial institutes (so called 'Zahlungsart 1') from an ISO20022
	 * Camt054 or Camt053 XML file, only CREDIT payments with status 'BOOK' that are not reversals are considerd
	 */
	@Nonnull
	DocumentDTO getDocumentWithBookedEsrPaymentsFromXml(@Nonnull byte[] xmlAsBytes) throws Iso20022RuntimeException;
}
