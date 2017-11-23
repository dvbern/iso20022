
package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityIdentification14 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityIdentification14">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ISIN" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ISINIdentifier" minOccurs="0"/>
 *         &lt;element name="OthrId" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}OtherIdentification1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Desc" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max140Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityIdentification14", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04", propOrder = {
    "isin",
    "othrId",
    "desc"
})
public class SecurityIdentification14 {

    @XmlElement(name = "ISIN", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected String isin;
    @XmlElement(name = "OthrId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected List<OtherIdentification1> othrId;
    @XmlElement(name = "Desc", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
    protected String desc;

    /**
     * Gets the value of the isin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISIN() {
        return isin;
    }

    /**
     * Sets the value of the isin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISIN(String value) {
        this.isin = value;
    }

    /**
     * Gets the value of the othrId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the othrId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOthrId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherIdentification1 }
     * 
     * 
     */
    public List<OtherIdentification1> getOthrId() {
        if (othrId == null) {
            othrId = new ArrayList<OtherIdentification1>();
        }
        return this.othrId;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

}
