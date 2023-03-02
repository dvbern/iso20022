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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.header.GroupHeader;
import ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces.notification.statement.StatementOrNotification;

@SuppressWarnings({ "override", "NullableProblems" })
public interface Notification {

	@Nonnull
	GroupHeader getGrpHdr();

	@Nonnull
	default List<StatementOrNotification> getAccountNotification() {
		return Stream.concat(getNtfctn().stream(), getStmt().stream())
			.collect(Collectors.toList());
	}

	@Nonnull
	default <T extends StatementOrNotification> List<T> getNtfctn() {
		return Collections.emptyList();
	}

	@Nonnull
	default <T extends StatementOrNotification> List<T> getStmt() {
		return Collections.emptyList();
	}
}
