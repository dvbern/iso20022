
package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardAggregated1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAggregated1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddtlSvc" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CardPaymentServiceType2Code" minOccurs="0"/>
 *         &lt;element name="TxCtgy" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ExternalCardTransactionCategory1Code" minOccurs="0"/>
 *         &lt;element name="SaleRcncltnId" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max35Text" minOccurs="0"/>
 *         &lt;element name="SeqNbRg" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CardSequenceNumberRange1" minOccurs="0"/>
 *         &lt;element name="TxDtRg" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}DateOrDateTimePeriodChoice" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAggregated1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04", propOrder = {
    "addtlSvc",
    "txCtgy",
    "saleRcncltnId",
    "seqNbRg",
    "txDtRg"
})
public class CardAggregated1 {

    @XmlElement(name = "AddtlSvc", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    @XmlSchemaType(name = "string")
    protected CardPaymentServiceType2Code addtlSvc;
    @XmlElement(name = "TxCtgy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected String txCtgy;
    @XmlElement(name = "SaleRcncltnId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected String saleRcncltnId;
    @XmlElement(name = "SeqNbRg", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected CardSequenceNumberRange1 seqNbRg;
    @XmlElement(name = "TxDtRg", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected DateOrDateTimePeriodChoice txDtRg;

    /**
     * Gets the value of the addtlSvc property.
     * 
     * @return
     *     possible object is
     *     {@link CardPaymentServiceType2Code }
     *     
     */
    public CardPaymentServiceType2Code getAddtlSvc() {
        return addtlSvc;
    }

    /**
     * Sets the value of the addtlSvc property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardPaymentServiceType2Code }
     *     
     */
    public void setAddtlSvc(CardPaymentServiceType2Code value) {
        this.addtlSvc = value;
    }

    /**
     * Gets the value of the txCtgy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxCtgy() {
        return txCtgy;
    }

    /**
     * Sets the value of the txCtgy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxCtgy(String value) {
        this.txCtgy = value;
    }

    /**
     * Gets the value of the saleRcncltnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaleRcncltnId() {
        return saleRcncltnId;
    }

    /**
     * Sets the value of the saleRcncltnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaleRcncltnId(String value) {
        this.saleRcncltnId = value;
    }

    /**
     * Gets the value of the seqNbRg property.
     * 
     * @return
     *     possible object is
     *     {@link CardSequenceNumberRange1 }
     *     
     */
    public CardSequenceNumberRange1 getSeqNbRg() {
        return seqNbRg;
    }

    /**
     * Sets the value of the seqNbRg property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardSequenceNumberRange1 }
     *     
     */
    public void setSeqNbRg(CardSequenceNumberRange1 value) {
        this.seqNbRg = value;
    }

    /**
     * Gets the value of the txDtRg property.
     * 
     * @return
     *     possible object is
     *     {@link DateOrDateTimePeriodChoice }
     *     
     */
    public DateOrDateTimePeriodChoice getTxDtRg() {
        return txDtRg;
    }

    /**
     * Sets the value of the txDtRg property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateOrDateTimePeriodChoice }
     *     
     */
    public void setTxDtRg(DateOrDateTimePeriodChoice value) {
        this.txDtRg = value;
    }

}
