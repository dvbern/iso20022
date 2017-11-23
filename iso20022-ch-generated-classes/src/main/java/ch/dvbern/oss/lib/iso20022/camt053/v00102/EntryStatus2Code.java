
package ch.dvbern.oss.lib.iso20022.camt053.v00102;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntryStatus2Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntryStatus2Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BOOK"/>
 *     &lt;enumeration value="PDNG"/>
 *     &lt;enumeration value="INFO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntryStatus2Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02")
@XmlEnum
public enum EntryStatus2Code {

    BOOK,
    PDNG,
    INFO;

    public String value() {
        return name();
    }

    public static EntryStatus2Code fromValue(String v) {
        return valueOf(v);
    }

}
