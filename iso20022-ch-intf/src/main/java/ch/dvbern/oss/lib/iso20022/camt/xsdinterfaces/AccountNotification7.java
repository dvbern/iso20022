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

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;

@SuppressWarnings({ "override", "NullableProblems" })
public interface AccountNotification7 {

	@Nonnull
	String getId();

	@Nullable
	BigDecimal getElctrncSeqNb();

	@Nonnull
	CashAccount25 getAcct();

	@Nonnull
	XMLGregorianCalendar getCreDtTm();

	@Nonnull
	<T extends ReportEntry4> List<T> getNtry();
}
