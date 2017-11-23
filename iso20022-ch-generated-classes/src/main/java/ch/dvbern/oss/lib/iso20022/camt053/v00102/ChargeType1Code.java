
package ch.dvbern.oss.lib.iso20022.camt053.v00102;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargeType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChargeType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BRKF"/>
 *     &lt;enumeration value="COMM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChargeType1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02")
@XmlEnum
public enum ChargeType1Code {

    BRKF,
    COMM;

    public String value() {
        return name();
    }

    public static ChargeType1Code fromValue(String v) {
        return valueOf(v);
    }

}
