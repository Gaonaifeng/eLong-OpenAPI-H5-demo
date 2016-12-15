//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.09 at 10:10:23 AM CST 
//


package com.elong.nb.model.elong;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement; 
import java.util.List; 
import com.alibaba.fastjson.annotation.JSONField;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseRatePlan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseRatePlan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RatePlanId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RatePlanName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinAmount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PaymentType" type="{}EnumPaymentType"/>
 *         &lt;element name="HourlyRoomInfo" type="{}HourlyRoomInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseRatePlan", propOrder = {
    "ratePlanId",
    "ratePlanName",
    "minAmount",
    "minDays",
    "maxDays",
    "paymentType",
    "hourlyRoomInfo"
})
@XmlSeeAlso({
    ListRatePlan.class,
    RatePlan.class
})
public class BaseRatePlan {

    @JSONField(name = "RatePlanId")
    protected int ratePlanId;
    @JSONField(name = "RatePlanName")
    protected String ratePlanName;
    @JSONField(name = "MinAmount")
    protected int minAmount;
    @JSONField(name = "MinDays")
    protected int minDays;
    @JSONField(name = "MaxDays")
    protected int maxDays;
    @JSONField(name = "PaymentType")
    @XmlSchemaType(name = "string")
    protected EnumPaymentType paymentType;
    @JSONField(name = "HourlyRoomInfo")
    protected HourlyRoomInfo hourlyRoomInfo;

    /**
     * Gets the value of the ratePlanId property.
     * 
     */
    public int getRatePlanId() {
        return ratePlanId;
    }

    /**
     * Sets the value of the ratePlanId property.
     * 
     */
    public void setRatePlanId(int value) {
        this.ratePlanId = value;
    }

    /**
     * Gets the value of the ratePlanName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanName() {
        return ratePlanName;
    }

    /**
     * Sets the value of the ratePlanName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanName(String value) {
        this.ratePlanName = value;
    }

    /**
     * Gets the value of the minAmount property.
     * 
     */
    public int getMinAmount() {
        return minAmount;
    }

    /**
     * Sets the value of the minAmount property.
     * 
     */
    public void setMinAmount(int value) {
        this.minAmount = value;
    }

    /**
     * Gets the value of the minDays property.
     * 
     */
    public int getMinDays() {
        return minDays;
    }

    /**
     * Sets the value of the minDays property.
     * 
     */
    public void setMinDays(int value) {
        this.minDays = value;
    }

    /**
     * Gets the value of the maxDays property.
     * 
     */
    public int getMaxDays() {
        return maxDays;
    }

    /**
     * Sets the value of the maxDays property.
     * 
     */
    public void setMaxDays(int value) {
        this.maxDays = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link EnumPaymentType }
     *     
     */
    public EnumPaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumPaymentType }
     *     
     */
    public void setPaymentType(EnumPaymentType value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the hourlyRoomInfo property.
     * 
     * @return
     *     possible object is
     *     {@link HourlyRoomInfo }
     *     
     */
    public HourlyRoomInfo getHourlyRoomInfo() {
        return hourlyRoomInfo;
    }

    /**
     * Sets the value of the hourlyRoomInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link HourlyRoomInfo }
     *     
     */
    public void setHourlyRoomInfo(HourlyRoomInfo value) {
        this.hourlyRoomInfo = value;
    }

}
