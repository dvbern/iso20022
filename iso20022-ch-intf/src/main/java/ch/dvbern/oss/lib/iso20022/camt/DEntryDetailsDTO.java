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

import java.util.ArrayList;
import java.util.List;

/**
 * D-Level: Betrags-Details, «Entry Details»
 * We use only Transaction Details (and not Batch Details)
 */
public class DEntryDetailsDTO {

	private List<DTransactionDetailsDTO> transactionDetailsDTOS = new ArrayList<>();

	public List<DTransactionDetailsDTO> getTransactionDetailsDTOS() {
		return transactionDetailsDTOS;
	}

	public void setTransactionDetailsDTOS(List<DTransactionDetailsDTO> transactionDetailsDTOS) {
		this.transactionDetailsDTOS = transactionDetailsDTOS;
	}
}
