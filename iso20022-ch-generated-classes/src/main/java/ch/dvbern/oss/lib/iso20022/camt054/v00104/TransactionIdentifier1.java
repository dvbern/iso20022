
package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TransactionIdentifier1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionIdentifier1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TxDtTm" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}ISODateTime"/>
 *         &lt;element name="TxRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}Max35Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionIdentifier1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", propOrder = {
    "txDtTm",
    "txRef"
})
public class TransactionIdentifier1 {

    @XmlElement(name = "TxDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar txDtTm;
    @XmlElement(name = "TxRef", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", required = true)
    protected String txRef;

    /**
     * Gets the value of the txDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTxDtTm() {
        return txDtTm;
    }

    /**
     * Sets the value of the txDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTxDtTm(XMLGregorianCalendar value) {
        this.txDtTm = value;
    }

    /**
     * Gets the value of the txRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxRef() {
        return txRef;
    }

    /**
     * Sets the value of the txRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxRef(String value) {
        this.txRef = value;
    }

}
