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

package ch.dvbern.oss.lib.iso20022;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

public final class TestUtil {

	private TestUtil() {
		// util
	}

	public static byte[] readXml(@Nonnull String path) {
		try (InputStream xmlAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
			if (xmlAsStream != null) {
				return IOUtils.toByteArray(xmlAsStream);
			}
			return ArrayUtils.EMPTY_BYTE_ARRAY;
		} catch (IOException e) {
			throw new IllegalStateException("Could not read XML", e);
		}
	}

	@Nonnull
	public static Stream<Path> getPathsFromResources(@Nonnull String resourceDir) throws IOException, URISyntaxException {
		URL url = Thread.currentThread().getContextClassLoader()
			.getResource(resourceDir);

		return Files.list(Paths.get(Objects.requireNonNull(url).toURI()));
	}

	@Nonnull

	public static Stream<Path> getXmlFilesFromResources(@Nonnull String resourceDir)
		throws IOException, URISyntaxException {
		return getPathsFromResources(resourceDir)
			.filter(path -> path.getFileName().toString().endsWith("xml"));
	}
}
