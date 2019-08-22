package ch.dvbern.oss.lib.iso20022.pain008.v00102ch03;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * C-Level, Direct debit transaction info (debitor).
 */
public class TransactionInformationDTO {

	/**
	 * {@link UUID#randomUUID()} by default.
	 */
	@NotNull
	private String transactionId = UUID.randomUUID().toString();

	@NotNull
	private BigDecimal instructedAmount = null;

	/**
	 * Identification of debitors financial institution
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{3,5}")
	private String debitorIID = null;

	@NotNull
	@Size(max = 70)
	private String debitorName = null;

	private String debitorStreetName = null;

	private String debitorPostCode = null;

	private String debitorTown = null;

	@Size(max = 2, min = 2)
	private String debitorCountry = null;

	@NotNull
	private String debitorIBAN = null;

	/**
	 * ESR reference number.
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{27}")
	private String refNr = null;

	public BigDecimal getInstructedAmount() {
		return instructedAmount;
	}

	public void setInstructedAmount(BigDecimal instructedAmount) {
		this.instructedAmount = instructedAmount;
	}

	public String getDebitorIID() {
		return debitorIID;
	}

	public void setDebitorIID(String debitorIID) {
		this.debitorIID = debitorIID;
	}

	public String getDebitorName() {
		return debitorName;
	}

	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}

	public String getDebitorIBAN() {
		return debitorIBAN;
	}

	public void setDebitorIBAN(String debitorIBAN) {
		this.debitorIBAN = debitorIBAN;
	}

	public String getRefNr() {
		return refNr;
	}

	public void setRefNr(String refNr) {
		this.refNr = refNr;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDebitorStreetName() {
		return debitorStreetName;
	}

	public void setDebitorStreetName(String debitorStreetName) {
		this.debitorStreetName = debitorStreetName;
	}

	public String getDebitorPostCode() {
		return debitorPostCode;
	}

	public void setDebitorPostCode(String debitorPostCode) {
		this.debitorPostCode = debitorPostCode;
	}

	public String getDebitorTown() {
		return debitorTown;
	}

	public void setDebitorTown(String debitorTown) {
		this.debitorTown = debitorTown;
	}

	public String getDebitorCountry() {
		return debitorCountry;
	}

	public void setDebitorCountry(String debitorCountry) {
		this.debitorCountry = debitorCountry;
	}
}
