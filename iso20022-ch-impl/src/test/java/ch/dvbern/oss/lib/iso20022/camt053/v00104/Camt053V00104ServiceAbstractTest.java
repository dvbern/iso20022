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
 *
 */

package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import ch.dvbern.oss.lib.iso20022.camt.CamtServiceAbstractTest;

public abstract class Camt053V00104ServiceAbstractTest extends CamtServiceAbstractTest{

	private final Camt053V00104Service camt053V00104Service = new Camt053V00104Service();

	@Override
	public Camt053V00104Service getCamtService() {
		return camt053V00104Service;
	}
}