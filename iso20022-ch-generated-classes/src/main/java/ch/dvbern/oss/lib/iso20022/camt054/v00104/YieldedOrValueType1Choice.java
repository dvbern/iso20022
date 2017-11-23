
package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for YieldedOrValueType1Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="YieldedOrValueType1Choice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Yldd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}YesNoIndicator"/>
 *         &lt;element name="ValTp" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}PriceValueType1Code"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "YieldedOrValueType1Choice", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", propOrder = {
    "yldd",
    "valTp"
})
public class YieldedOrValueType1Choice {

    @XmlElement(name = "Yldd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04")
    protected Boolean yldd;
    @XmlElement(name = "ValTp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04")
    @XmlSchemaType(name = "string")
    protected PriceValueType1Code valTp;

    /**
     * Gets the value of the yldd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isYldd() {
        return yldd;
    }

    /**
     * Sets the value of the yldd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setYldd(Boolean value) {
        this.yldd = value;
    }

    /**
     * Gets the value of the valTp property.
     * 
     * @return
     *     possible object is
     *     {@link PriceValueType1Code }
     *     
     */
    public PriceValueType1Code getValTp() {
        return valTp;
    }

    /**
     * Sets the value of the valTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceValueType1Code }
     *     
     */
    public void setValTp(PriceValueType1Code value) {
        this.valTp = value;
    }

}
