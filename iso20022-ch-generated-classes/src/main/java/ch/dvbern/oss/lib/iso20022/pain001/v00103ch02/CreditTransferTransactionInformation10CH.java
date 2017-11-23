
/*
 * Copyright 2017 DV Bern AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * limitations under the License.
 */

package ch.dvbern.oss.lib.iso20022.pain001.v00103ch02;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CreditTransferTransactionInformation10-CH complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditTransferTransactionInformation10-CH">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PmtId" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}PaymentIdentification1"/>
 *         &lt;element name="PmtTpInf" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}PaymentTypeInformation19-CH" minOccurs="0"/>
 *         &lt;element name="Amt" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}AmountType3Choice"/>
 *         &lt;element name="XchgRateInf" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}ExchangeRateInformation1" minOccurs="0"/>
 *         &lt;element name="ChrgBr" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}ChargeBearerType1Code" minOccurs="0"/>
 *         &lt;element name="ChqInstr" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}Cheque6-CH" minOccurs="0"/>
 *         &lt;element name="UltmtDbtr" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}PartyIdentification32-CH" minOccurs="0"/>
 *         &lt;element name="IntrmyAgt1" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}BranchAndFinancialInstitutionIdentification4-CH" minOccurs="0"/>
 *         &lt;element name="CdtrAgt" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}BranchAndFinancialInstitutionIdentification4-CH" minOccurs="0"/>
 *         &lt;element name="Cdtr" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}PartyIdentification32-CH_Name" minOccurs="0"/>
 *         &lt;element name="CdtrAcct" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}CashAccount16-CH_Id" minOccurs="0"/>
 *         &lt;element name="UltmtCdtr" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}PartyIdentification32-CH_Name" minOccurs="0"/>
 *         &lt;element name="InstrForCdtrAgt" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}InstructionForCreditorAgent1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InstrForDbtrAgt" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}Max140Text" minOccurs="0"/>
 *         &lt;element name="Purp" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}Purpose2-CH_Code" minOccurs="0"/>
 *         &lt;element name="RgltryRptg" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}RegulatoryReporting3" maxOccurs="10" minOccurs="0"/>
 *         &lt;element name="RmtInf" type="{http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd}RemittanceInformation5-CH" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditTransferTransactionInformation10-CH", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd", propOrder = {
    "pmtId",
    "pmtTpInf",
    "amt",
    "xchgRateInf",
    "chrgBr",
    "chqInstr",
    "ultmtDbtr",
    "intrmyAgt1",
    "cdtrAgt",
    "cdtr",
    "cdtrAcct",
    "ultmtCdtr",
    "instrForCdtrAgt",
    "instrForDbtrAgt",
    "purp",
    "rgltryRptg",
    "rmtInf"
})
public class CreditTransferTransactionInformation10CH {

    @XmlElement(name = "PmtId", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd", required = true)
    protected PaymentIdentification1 pmtId;
    @XmlElement(name = "PmtTpInf", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected PaymentTypeInformation19CH pmtTpInf;
    @XmlElement(name = "Amt", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd", required = true)
    protected AmountType3Choice amt;
    @XmlElement(name = "XchgRateInf", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected ExchangeRateInformation1 xchgRateInf;
    @XmlElement(name = "ChrgBr", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    @XmlSchemaType(name = "string")
    protected ChargeBearerType1Code chrgBr;
    @XmlElement(name = "ChqInstr", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected Cheque6CH chqInstr;
    @XmlElement(name = "UltmtDbtr", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected PartyIdentification32CH ultmtDbtr;
    @XmlElement(name = "IntrmyAgt1", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected BranchAndFinancialInstitutionIdentification4CH intrmyAgt1;
    @XmlElement(name = "CdtrAgt", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected BranchAndFinancialInstitutionIdentification4CH cdtrAgt;
    @XmlElement(name = "Cdtr", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected PartyIdentification32CHName cdtr;
    @XmlElement(name = "CdtrAcct", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected CashAccount16CHId cdtrAcct;
    @XmlElement(name = "UltmtCdtr", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected PartyIdentification32CHName ultmtCdtr;
    @XmlElement(name = "InstrForCdtrAgt", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected List<InstructionForCreditorAgent1> instrForCdtrAgt;
    @XmlElement(name = "InstrForDbtrAgt", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected String instrForDbtrAgt;
    @XmlElement(name = "Purp", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected Purpose2CHCode purp;
    @XmlElement(name = "RgltryRptg", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected List<RegulatoryReporting3> rgltryRptg;
    @XmlElement(name = "RmtInf", namespace = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd")
    protected RemittanceInformation5CH rmtInf;

    /**
     * Gets the value of the pmtId property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentIdentification1 }
     *     
     */
    public PaymentIdentification1 getPmtId() {
        return pmtId;
    }

    /**
     * Sets the value of the pmtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentIdentification1 }
     *     
     */
    public void setPmtId(PaymentIdentification1 value) {
        this.pmtId = value;
    }

    /**
     * Gets the value of the pmtTpInf property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTypeInformation19CH }
     *     
     */
    public PaymentTypeInformation19CH getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * Sets the value of the pmtTpInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTypeInformation19CH }
     *     
     */
    public void setPmtTpInf(PaymentTypeInformation19CH value) {
        this.pmtTpInf = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType3Choice }
     *     
     */
    public AmountType3Choice getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType3Choice }
     *     
     */
    public void setAmt(AmountType3Choice value) {
        this.amt = value;
    }

    /**
     * Gets the value of the xchgRateInf property.
     * 
     * @return
     *     possible object is
     *     {@link ExchangeRateInformation1 }
     *     
     */
    public ExchangeRateInformation1 getXchgRateInf() {
        return xchgRateInf;
    }

    /**
     * Sets the value of the xchgRateInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeRateInformation1 }
     *     
     */
    public void setXchgRateInf(ExchangeRateInformation1 value) {
        this.xchgRateInf = value;
    }

    /**
     * Gets the value of the chrgBr property.
     * 
     * @return
     *     possible object is
     *     {@link ChargeBearerType1Code }
     *     
     */
    public ChargeBearerType1Code getChrgBr() {
        return chrgBr;
    }

    /**
     * Sets the value of the chrgBr property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeBearerType1Code }
     *     
     */
    public void setChrgBr(ChargeBearerType1Code value) {
        this.chrgBr = value;
    }

    /**
     * Gets the value of the chqInstr property.
     * 
     * @return
     *     possible object is
     *     {@link Cheque6CH }
     *     
     */
    public Cheque6CH getChqInstr() {
        return chqInstr;
    }

    /**
     * Sets the value of the chqInstr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cheque6CH }
     *     
     */
    public void setChqInstr(Cheque6CH value) {
        this.chqInstr = value;
    }

    /**
     * Gets the value of the ultmtDbtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification32CH }
     *     
     */
    public PartyIdentification32CH getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification32CH }
     *     
     */
    public void setUltmtDbtr(PartyIdentification32CH value) {
        this.ultmtDbtr = value;
    }

    /**
     * Gets the value of the intrmyAgt1 property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4CH }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4CH getIntrmyAgt1() {
        return intrmyAgt1;
    }

    /**
     * Sets the value of the intrmyAgt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4CH }
     *     
     */
    public void setIntrmyAgt1(BranchAndFinancialInstitutionIdentification4CH value) {
        this.intrmyAgt1 = value;
    }

    /**
     * Gets the value of the cdtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4CH }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4CH getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Sets the value of the cdtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4CH }
     *     
     */
    public void setCdtrAgt(BranchAndFinancialInstitutionIdentification4CH value) {
        this.cdtrAgt = value;
    }

    /**
     * Gets the value of the cdtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification32CHName }
     *     
     */
    public PartyIdentification32CHName getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification32CHName }
     *     
     */
    public void setCdtr(PartyIdentification32CHName value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the cdtrAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16CHId }
     *     
     */
    public CashAccount16CHId getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16CHId }
     *     
     */
    public void setCdtrAcct(CashAccount16CHId value) {
        this.cdtrAcct = value;
    }

    /**
     * Gets the value of the ultmtCdtr property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification32CHName }
     *     
     */
    public PartyIdentification32CHName getUltmtCdtr() {
        return ultmtCdtr;
    }

    /**
     * Sets the value of the ultmtCdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification32CHName }
     *     
     */
    public void setUltmtCdtr(PartyIdentification32CHName value) {
        this.ultmtCdtr = value;
    }

    /**
     * Gets the value of the instrForCdtrAgt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrForCdtrAgt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstrForCdtrAgt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionForCreditorAgent1 }
     * 
     * 
     */
    public List<InstructionForCreditorAgent1> getInstrForCdtrAgt() {
        if (instrForCdtrAgt == null) {
            instrForCdtrAgt = new ArrayList<InstructionForCreditorAgent1>();
        }
        return this.instrForCdtrAgt;
    }

    /**
     * Gets the value of the instrForDbtrAgt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstrForDbtrAgt() {
        return instrForDbtrAgt;
    }

    /**
     * Sets the value of the instrForDbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstrForDbtrAgt(String value) {
        this.instrForDbtrAgt = value;
    }

    /**
     * Gets the value of the purp property.
     * 
     * @return
     *     possible object is
     *     {@link Purpose2CHCode }
     *     
     */
    public Purpose2CHCode getPurp() {
        return purp;
    }

    /**
     * Sets the value of the purp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Purpose2CHCode }
     *     
     */
    public void setPurp(Purpose2CHCode value) {
        this.purp = value;
    }

    /**
     * Gets the value of the rgltryRptg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rgltryRptg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRgltryRptg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegulatoryReporting3 }
     * 
     * 
     */
    public List<RegulatoryReporting3> getRgltryRptg() {
        if (rgltryRptg == null) {
            rgltryRptg = new ArrayList<RegulatoryReporting3>();
        }
        return this.rgltryRptg;
    }

    /**
     * Gets the value of the rmtInf property.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceInformation5CH }
     *     
     */
    public RemittanceInformation5CH getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceInformation5CH }
     *     
     */
    public void setRmtInf(RemittanceInformation5CH value) {
        this.rmtInf = value;
    }

}
