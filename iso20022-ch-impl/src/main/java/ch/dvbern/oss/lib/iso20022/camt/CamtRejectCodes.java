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

public enum CamtRejectCodes {
	NO_REJECT("?REJECT?0"),
	REJECT("?REJECT?1"),
	MASS_REJECT("?REJECT?5");

	@Nonnull
	private final String code;

	CamtRejectCodes(@Nonnull String value) {
		this.code = value;
	}

	@Nonnull
	public String getCode() {
		return code;
	}
}
