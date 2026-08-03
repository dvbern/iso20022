package ch.dvbern.oss.lib.iso20022.dtos.pain;

import jakarta.annotation.Nullable;

/**
 * @param productName Name of the software product
 * @param manufacturerName Name of the software manufacturer
 * @param softwareVersion Version of the software
 * @param spsIgVersion Version of the SPS IG in nnnn format (e.g. 0200 for IG version 2.0) implemented by the
 * software (format is not validated). Defaults to 0230.
 */
public record Pain001SoftwareDTO(
	@Nullable String productName,
	@Nullable String manufacturerName,
	@Nullable String softwareVersion,
	@Nullable String spsIgVersion
) {
	public Pain001SoftwareDTO(
		@Nullable String productName,
		@Nullable String manufacturerName,
		@Nullable String softwareVersion
	) {
		this(productName, manufacturerName, softwareVersion, "0230");
	}
}
