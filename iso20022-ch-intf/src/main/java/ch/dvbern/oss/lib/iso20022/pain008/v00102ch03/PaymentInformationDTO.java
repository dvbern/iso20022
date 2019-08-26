package ch.dvbern.oss.lib.iso20022.pain008.v00102ch03;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * B-Level, payment info (creditor).
 */
public class PaymentInformationDTO {

	/**
	 * {@link UUID#randomUUID()} by default.
	 */
	@NotNull
	private String paymentInfoId = UUID.randomUUID().toString();

	@NotNull
	private LocalDate requestedCollectionDate = null;

	@NotNull
	private String creditorName = null;

	@NotNull
	private String creditorIBAN = null;

	/**
	 * Identification of creditors financial institution
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{3,5}")
	private String creditorIID = null;

	/**
	 * ESR participation number of creditors financial institution
	 */
	@NotNull
	@Pattern(regexp = "[0-9]{9}")
	private String institutionEsr = null;

	/**
	 * Creditor LSV+ identification.
	 */
	@NotNull
	private String creditorId = null;

	@Valid
	private List<TransactionInformationDTO> transactionInfo = new ArrayList<>();

	public String getPaymentInfoId() {
		return paymentInfoId;
	}

	public void setPaymentInfoId(String paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}

	public LocalDate getRequestedCollectionDate() {
		return requestedCollectionDate;
	}

	public void setRequestedCollectionDate(LocalDate requestedCollectionDate) {
		this.requestedCollectionDate = requestedCollectionDate;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorIBAN() {
		return creditorIBAN;
	}

	public void setCreditorIBAN(String creditorIBAN) {
		this.creditorIBAN = creditorIBAN;
	}

	public String getCreditorIID() {
		return creditorIID;
	}

	public void setCreditorIID(String creditorIID) {
		this.creditorIID = creditorIID;
	}

	public String getInstitutionEsr() {
		return institutionEsr;
	}

	public void setInstitutionEsr(String institutionEsr) {
		this.institutionEsr = institutionEsr;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public List<TransactionInformationDTO> getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(List<TransactionInformationDTO> transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
