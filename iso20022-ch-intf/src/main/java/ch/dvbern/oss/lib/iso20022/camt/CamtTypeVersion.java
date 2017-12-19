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

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.Document;

public enum CamtTypeVersion {
	CAMT053V00104(
		"iso/std/iso/20022/tech/xsd/camt_053_001/camt.053.001.04.xsd",
		iso.std.iso._20022.tech.xsd.camt_053_001.Document.class),
	CAMT054V00104(
		"iso/std/iso/20022/tech/xsd/camt_054_001_04/camt.054.001.04.xsd",
		iso.std.iso._20022.tech.xsd.camt_054_001.Document.class);

	@Nonnull
	private final String xsdPath;

	@Nonnull
	private final Class<? extends Document> documentClass;

	CamtTypeVersion(
		@Nonnull String xsdPath,
		@Nonnull Class<? extends Document> documentClass) {
		this.xsdPath = xsdPath;
		this.documentClass = documentClass;
	}

	@Nonnull
	public String getXsdPath() {
		return xsdPath;
	}

	@Nonnull
	public <T extends Document> Class<T> getDocumentClass() {
		//noinspection unchecked
		return (Class<T>) documentClass;
	}
}
