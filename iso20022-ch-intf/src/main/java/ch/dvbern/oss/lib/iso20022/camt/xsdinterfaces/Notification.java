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

package ch.dvbern.oss.lib.iso20022.camt.xsdinterfaces;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

@SuppressWarnings({ "override", "NullableProblems" })
public interface Notification {

	@Nonnull
	GroupHeader58 getGrpHdr();

	@Nonnull
	default List<AccountNotification7> getAccountNotification() {
		return Stream.concat(getNtfctn().stream(), getStmt().stream())
			.collect(Collectors.toList());
	}

	@Nonnull
	default <T extends AccountNotification7> List<T> getNtfctn() {
		return Collections.emptyList();
	}

	@Nonnull
	default <T extends AccountNotification7> List<T> getStmt() {
		return Collections.emptyList();
	}
}
