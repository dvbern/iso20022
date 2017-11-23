
package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserInterface2Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserInterface2Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MDSP"/>
 *     &lt;enumeration value="CDSP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserInterface2Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
@XmlEnum
public enum UserInterface2Code {

    MDSP,
    CDSP;

    public String value() {
        return name();
    }

    public static UserInterface2Code fromValue(String v) {
        return valueOf(v);
    }

}
