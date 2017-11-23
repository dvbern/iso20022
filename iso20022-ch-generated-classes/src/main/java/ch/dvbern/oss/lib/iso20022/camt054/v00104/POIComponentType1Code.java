
package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for POIComponentType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="POIComponentType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SOFT"/>
 *     &lt;enumeration value="EMVK"/>
 *     &lt;enumeration value="EMVO"/>
 *     &lt;enumeration value="MRIT"/>
 *     &lt;enumeration value="CHIT"/>
 *     &lt;enumeration value="SECM"/>
 *     &lt;enumeration value="PEDV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "POIComponentType1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04")
@XmlEnum
public enum POIComponentType1Code {

    SOFT,
    EMVK,
    EMVO,
    MRIT,
    CHIT,
    SECM,
    PEDV;

    public String value() {
        return name();
    }

    public static POIComponentType1Code fromValue(String v) {
        return valueOf(v);
    }

}
