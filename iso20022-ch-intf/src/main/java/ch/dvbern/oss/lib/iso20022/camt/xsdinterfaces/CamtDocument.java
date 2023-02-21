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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces;

import java.util.Optional;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.Notification;
import jakarta.annotation.Nullable;

@SuppressWarnings("override")
public interface CamtDocument {

	@Nullable
	default Notification getNotification() {
		return Optional.ofNullable(getBkToCstmrDbtCdtNtfctn())
			.orElseGet(this::getBkToCstmrStmt);
	}

	/**
	 * The XML message "Bank-to-Customer Statement" (camt.053) is used by financial
	 * institutions to send electronic account information to their customers. It is used
	 * on the basis of the ISO 20022 XML schema "camt.053.001.08".
	 */
	@Nullable
	default Notification getBkToCstmrDbtCdtNtfctn() {
		return null;
	}

	@Nullable
	default Notification getBkToCstmrStmt() {
		return null;
	}
}
