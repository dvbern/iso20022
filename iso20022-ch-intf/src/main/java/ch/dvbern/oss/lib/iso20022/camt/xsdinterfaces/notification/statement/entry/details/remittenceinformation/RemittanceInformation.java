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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.entry.details.remittenceinformation;

import java.util.List;

import jakarta.annotation.Nonnull;

@SuppressWarnings({ "override", "NullableProblems" })
public interface RemittanceInformation {

	/**
	 * This element can contain unstructured messages, e.g. for messages from a
	 * "pain.001" instruction or booking information. The element can occur more than
	 * once.
	 * <p>
	 * Removed ISR specific information, adapted CH-Definition.
	 * The element is supplied maximum 1 time
	 */
	@Nonnull
	List<String> getUstrd();

	/**
	 * The tag consists of a number of sub-elements. In Switzerland the &lt;CdtrRefInf&gt;
	 * element can be filled in, whenever the structured "Creditor Reference" is given in
	 * the instruction, e.g. ISR/QR/LSV reference, IPI reference or the new international
	 * "Creditor’s Reference" according to ISO 11649.
	 */
	@Nonnull
	<T extends StructuredRemittanceInformation> List<T> getStrd();
}
