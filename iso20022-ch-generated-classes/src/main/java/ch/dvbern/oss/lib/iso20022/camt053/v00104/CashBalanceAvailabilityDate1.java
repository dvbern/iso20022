
package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CashBalanceAvailabilityDate1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashBalanceAvailabilityDate1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="NbOfDays" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max15PlusSignedNumericText"/>
 *           &lt;element name="ActlDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ISODate"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashBalanceAvailabilityDate1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04", propOrder = {
    "nbOfDays",
    "actlDt"
})
public class CashBalanceAvailabilityDate1 {

    @XmlElement(name = "NbOfDays", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected String nbOfDays;
    @XmlElement(name = "ActlDt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar actlDt;

    /**
     * Gets the value of the nbOfDays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbOfDays() {
        return nbOfDays;
    }

    /**
     * Sets the value of the nbOfDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbOfDays(String value) {
        this.nbOfDays = value;
    }

    /**
     * Gets the value of the actlDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActlDt() {
        return actlDt;
    }

    /**
     * Sets the value of the actlDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActlDt(XMLGregorianCalendar value) {
        this.actlDt = value;
    }

}
