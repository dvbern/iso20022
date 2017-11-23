
package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BankTransactionCodeStructure5 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankTransactionCodeStructure5">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}ExternalBankTransactionDomain1Code"/>
 *         &lt;element name="Fmly" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}BankTransactionCodeStructure6"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankTransactionCodeStructure5", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", propOrder = {
    "cd",
    "fmly"
})
public class BankTransactionCodeStructure5 {

    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", required = true)
    protected String cd;
    @XmlElement(name = "Fmly", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", required = true)
    protected BankTransactionCodeStructure6 fmly;

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
     * Gets the value of the fmly property.
     * 
     * @return
     *     possible object is
     *     {@link BankTransactionCodeStructure6 }
     *     
     */
    public BankTransactionCodeStructure6 getFmly() {
        return fmly;
    }

    /**
     * Sets the value of the fmly property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankTransactionCodeStructure6 }
     *     
     */
    public void setFmly(BankTransactionCodeStructure6 value) {
        this.fmly = value;
    }

}
