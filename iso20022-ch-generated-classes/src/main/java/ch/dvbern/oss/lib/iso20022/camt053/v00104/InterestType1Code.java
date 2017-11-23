
package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InterestType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InterestType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INDY"/>
 *     &lt;enumeration value="OVRN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InterestType1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
@XmlEnum
public enum InterestType1Code {

    INDY,
    OVRN;

    public String value() {
        return name();
    }

    public static InterestType1Code fromValue(String v) {
        return valueOf(v);
    }

}
