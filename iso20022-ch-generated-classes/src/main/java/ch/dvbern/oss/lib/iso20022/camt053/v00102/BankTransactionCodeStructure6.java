
package ch.dvbern.oss.lib.iso20022.camt053.v00102;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BankTransactionCodeStructure6 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankTransactionCodeStructure6">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}ExternalBankTransactionFamily1Code"/>
 *         &lt;element name="SubFmlyCd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}ExternalBankTransactionSubFamily1Code"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankTransactionCodeStructure6", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", propOrder = {
    "cd",
    "subFmlyCd"
})
public class BankTransactionCodeStructure6 {

    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", required = true)
    protected String cd;
    @XmlElement(name = "SubFmlyCd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", required = true)
    protected String subFmlyCd;

    /**
     * Gets the value of the cd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCd(String value) {
        this.cd = value;
    }

    /**
     * Gets the value of the subFmlyCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubFmlyCd() {
        return subFmlyCd;
    }

    /**
     * Sets the value of the subFmlyCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubFmlyCd(String value) {
        this.subFmlyCd = value;
    }

}
