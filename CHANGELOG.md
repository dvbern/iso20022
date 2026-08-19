# 9.0.1

## Bugfixes
- Addressed the issue where empty adress elements are generated. 
  When providing an empty string for an adress element such as the hausnummer, the library generated an empty
  xml element. This is not valid according to the standard. Adress Elements are now only generated if the 
  provided dto attribute is not blank (neither empty string nor null)
# 9.0.0

## BREAKING CHANGES

Remove `zahlungsempfaengerBankClearingNumber` from `AuszahlungDTO`.
The `zahlungsempfaengerIBAN` has always been required, even though that was not yet reflected in the DTO.

According to the Swiss Payment Standards SPS 2026 Version 2.3, section 3.12, specifying the Creditor Agent is not
necessary when using an IBAN – although it is recommended for payment type `X`. Therefore, the Creditor Agent is
now optionally specified when the user sets a value for `zahlungsempfaengerBIC`. Otherwise, it is omitted entirely.


# 8.0.3

Fix publishing to github.


# 8.0.2

## BREAKING CHANGES

- require Java version 17 or higher
- `pain.001.001.03` replaced with `pain.001.001.09`

## Bugfixes
- invalid pain001 format when using `AuszahlungsDTO` with `zahlungsempfaengerBIC`.<br>
  When using BICFI for the debtor account, then the Clearing System Member element is not permitted (ig-credit-transfer-sps-2026 section 3.12).

## Other changes
- XML Unit matchers, XML Unit update from `2.10.0` to `2.12.0`
- Replacement of deprecated `RandomStringsUtils` methods
- The CI/CD pipeline has been updated.

## Migration Guide

Only users of the Pain001Service or Pain008Service are affected.
 
Replace the import `import ch.dvbern.oss.lib.iso20022.pain001.v00103ch02.Pain001Service;` with `import ch.dvbern.oss.lib.iso20022.pain001.v00109ch03.Pain001Service;`

If you've used `Pain001DTO` `softwareName` or `softwareVersion`, specify instead `Pain001SoftwareDTO`.
If you haven't, note that it's recommended according to the implementation guidelines of SIX.

The software version in both, `pain001` and `pain008` services was previously set to 'V01' if not specified otherwise.
This is no longer the case. It's expected that you specify your product version.

### Changes to the generated pain001 XML

The generated XML structure has been updated to comply with `pain.001.001.09`:
- **Namespace**: The schema location and namespace have been updated to `urn:iso:std:iso:20022:tech:xsd:pain.001.001.09`.
- **Contact Details** (`CtctDtls`): Now explicitly structured using channel types (`ChanlTp`) such as `NAME`, `PRVD`, `VRSN`, and `SPSV` combined with `<Id>` elements, replacing the previous plain text approach.
- **Payment Method** (`PmtMtd`): The value has been updated from `TRA` to `TRF`.
- **Execution Date** (`ReqdExctnDt`): The date value is now wrapped inside a nested `<Dt>` element.
- **Debtor Agent** (`DbtrAgt`): The `BIC` element has been replaced by `BICFI`.
