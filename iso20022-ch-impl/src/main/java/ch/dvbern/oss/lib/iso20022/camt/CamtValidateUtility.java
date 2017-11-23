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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import org.xml.sax.SAXException;

public final class CamtValidateUtility {

	private CamtValidateUtility() {
		// tuil
	}

	public static boolean isMatchingXsdSchema(byte[] xmlAsBytes, @Nonnull String pathToXsd) {

		try (ByteArrayInputStream xmlAsStream = new ByteArrayInputStream(xmlAsBytes);
			InputStream xsdAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToXsd)) {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(xsdAsStream));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlAsStream));
			return true;
		} catch (IOException e) {
			throw new Iso20022RuntimeException("IO Exception while validating xml file with xsd schema", e);
		} catch (SAXException ignore) {
			return false;
		}
	}
}
