
package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardTransaction1Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardTransaction1Choice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Aggtd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}CardAggregated1"/>
 *         &lt;element name="Indv" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}CardIndividualTransaction1"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardTransaction1Choice", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", propOrder = {
    "aggtd",
    "indv"
})
public class CardTransaction1Choice {

    @XmlElement(name = "Aggtd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04")
    protected CardAggregated1 aggtd;
    @XmlElement(name = "Indv", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04")
    protected CardIndividualTransaction1 indv;

    /**
     * Gets the value of the aggtd property.
     * 
     * @return
     *     possible object is
     *     {@link CardAggregated1 }
     *     
     */
    public CardAggregated1 getAggtd() {
        return aggtd;
    }

    /**
     * Sets the value of the aggtd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAggregated1 }
     *     
     */
    public void setAggtd(CardAggregated1 value) {
        this.aggtd = value;
    }

    /**
     * Gets the value of the indv property.
     * 
     * @return
     *     possible object is
     *     {@link CardIndividualTransaction1 }
     *     
     */
    public CardIndividualTransaction1 getIndv() {
        return indv;
    }

    /**
     * Sets the value of the indv property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardIndividualTransaction1 }
     *     
     */
    public void setIndv(CardIndividualTransaction1 value) {
        this.indv = value;
    }

}
