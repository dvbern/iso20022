
package ch.dvbern.oss.lib.iso20022.camt054.v00104;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyType3Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PartyType3Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPOI"/>
 *     &lt;enumeration value="MERC"/>
 *     &lt;enumeration value="ACCP"/>
 *     &lt;enumeration value="ITAG"/>
 *     &lt;enumeration value="ACQR"/>
 *     &lt;enumeration value="CISS"/>
 *     &lt;enumeration value="DLIS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PartyType3Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.04")
@XmlEnum
public enum PartyType3Code {

    OPOI,
    MERC,
    ACCP,
    ITAG,
    ACQR,
    CISS,
    DLIS;

    public String value() {
        return name();
    }

    public static PartyType3Code fromValue(String v) {
        return valueOf(v);
    }

}
