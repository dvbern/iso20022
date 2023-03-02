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

package ch.dvbern.oss.lib.iso20022.pain008.v00102ch03;

import javax.validation.Valid;

import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain008DTO;

/**
 * Service to generate Payment-File Pain008 according to ISO20022 for a swiss bank
 */
public interface Pain008Service {

	String SCHEMA_NAME = "pain.008.001.02.ch.03.xsd";
	String SCHEMA_LOCATION = "http://www.six-interbank-clearing.com/de/" + SCHEMA_NAME;

	/**
	 * Creates an UTF-8 pain file for LSV+ (CH-TA), based on the given DTO.
	 *
	 * @param pain008DTO the payment information in Java format
	 * @return the payment information in XML format
	 */
	byte[] getPainFileContent(@Valid Pain008DTO pain008DTO);

}
