
package ch.dvbern.oss.lib.iso20022.camt053.v00102;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Pagination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pagination">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PgNb" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}Max5NumericText"/>
 *         &lt;element name="LastPgInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.02}YesNoIndicator"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagination", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", propOrder = {
    "pgNb",
    "lastPgInd"
})
public class Pagination {

    @XmlElement(name = "PgNb", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02", required = true)
    protected String pgNb;
    @XmlElement(name = "LastPgInd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02")
    protected boolean lastPgInd;

    /**
     * Gets the value of the pgNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPgNb() {
        return pgNb;
    }

    /**
     * Sets the value of the pgNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPgNb(String value) {
        this.pgNb = value;
    }

    /**
     * Gets the value of the lastPgInd property.
     * 
     */
    public boolean isLastPgInd() {
        return lastPgInd;
    }

    /**
     * Sets the value of the lastPgInd property.
     * 
     */
    public void setLastPgInd(boolean value) {
        this.lastPgInd = value;
    }

}
