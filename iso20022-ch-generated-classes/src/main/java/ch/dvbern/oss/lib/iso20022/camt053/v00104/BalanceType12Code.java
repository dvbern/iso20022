
package ch.dvbern.oss.lib.iso20022.camt053.v00104;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BalanceType12Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BalanceType12Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="XPCD"/>
 *     &lt;enumeration value="OPAV"/>
 *     &lt;enumeration value="ITAV"/>
 *     &lt;enumeration value="CLAV"/>
 *     &lt;enumeration value="FWAV"/>
 *     &lt;enumeration value="CLBD"/>
 *     &lt;enumeration value="ITBD"/>
 *     &lt;enumeration value="OPBD"/>
 *     &lt;enumeration value="PRCD"/>
 *     &lt;enumeration value="INFO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BalanceType12Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04")
@XmlEnum
public enum BalanceType12Code {

    XPCD,
    OPAV,
    ITAV,
    CLAV,
    FWAV,
    CLBD,
    ITBD,
    OPBD,
    PRCD,
    INFO;

    public String value() {
        return name();
    }

    public static BalanceType12Code fromValue(String v) {
        return valueOf(v);
    }

}
