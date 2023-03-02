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

package ch.dvbern.oss.lib.iso20022.pain001.v00103ch02;

import javax.validation.Valid;

import ch.dvbern.oss.lib.iso20022.dtos.pain.Pain001DTO;

/**
 * Service to generate Payment-File Pain001 according to ISO20022 for a swiss bank
 */
public interface Pain001Service {

	String SCHEMA_NAME = "pain.001.001.03.ch.02.xsd";
	String SCHEMA_LOCATION_LOCAL = "ch.dvbern.oss.lib.iso20022.pain001.v00103ch02/" + SCHEMA_NAME;
	String SCHEMA_LOCATION = "http://www.six-interbank-clearing.com/de/" + SCHEMA_NAME;

	byte[] getPainFileContent(@Valid Pain001DTO pain001DTO);

}
