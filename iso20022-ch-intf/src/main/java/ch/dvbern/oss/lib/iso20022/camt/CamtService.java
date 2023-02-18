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

package ch.dvbern.oss.lib.iso20022.camt;

import javax.annotation.Nonnull;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.CamtDocument;
import ch.dvbern.oss.lib.iso20022.dtos.camt.DocumentDTO;
import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;

public interface CamtService {

	String INVALID_NAME = "NOTPROVIDED";

	/**
	 * Service to read ESR payments to swiss financial institutes from an ISO20022.
	 * Camt054 or Camt053 XML file, only CREDIT payments with status 'BOOK', that are not reversals, are considered.
	 *
	 * @param xmlAsBytes a Camt053 or Camt054 XML in byte array form.
	 * @return a Document containing payments
	 * @throws Iso20022RuntimeException when parsing fails (e.g. invalid input document)
	 */
	@Nonnull
	DocumentDTO getCreditingRecords(@Nonnull byte[] xmlAsBytes) throws Iso20022RuntimeException;

	/**
	 * Converts a Camt053 or Camt054 file to an instance of
	 * {@link iso.std.iso._20022.tech.xsd.camt_053_001_04.Document},
	 * {@link iso.std.iso._20022.tech.xsd.camt_053_001_08.Document},
	 * {@link iso.std.iso._20022.tech.xsd.camt_054_001_04.Document},
	 * {@link iso.std.iso._20022.tech.xsd.camt_054_001_08.Document} respectively.
	 *
	 * @param xmlAsBytes a Camt053 or Camt054 XML in byte array form.
	 * @return a Java representation of the parsed XML
	 * @throws Iso20022RuntimeException when parsing fails (e.g. invalid input document)
	 */
	@Nonnull
	CamtDocument getNotificationFromXml(@Nonnull byte[] xmlAsBytes) throws Iso20022RuntimeException;
}
