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

import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateOrderCondition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateOrderCondition">
 *   &lt;complexContent>
 *     &lt;extension base="{}OrderBase">
 *       &lt;sequence>
 *         &lt;element name="AffiliateConfirmationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerIPAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsGuaranteeOrCharged" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SupplierCardNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsNeedInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Contact" type="{}Contact" minOccurs="0"/>
 *         &lt;element name="ExtendInfo" type="{}ExtendInfo" minOccurs="0"/>
 *         &lt;element name="NightlyRates" type="{}ArrayOfBaseNightlyRate" minOccurs="0"/>
 *         &lt;element name="OrderRooms" type="{}ArrayOfCreateOrderRoom" minOccurs="0"/>
 *         &lt;element name="Invoice" type="{}Invoice" minOccurs="0"/>
 *         &lt;element name="CreditCard" type="{}CreditCard" minOccurs="0"/>
 *         &lt;element name="IsForceGuarantee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateOrderCondition", propOrder = {
    "affiliateConfirmationId",
    "customerIPAddress",
    "isGuaranteeOrCharged",
    "supplierCardNo",
    "isNeedInvoice",
    "contact",
    "extendInfo",
    "nightlyRates",
    "orderRooms",
    "invoice",
    "creditCard",
    "isForceGuarantee",
    "isCreateOrderOnly"
})
public class CreateOrderCondition
    extends OrderBase
{

    @JSONField(name = "AffiliateConfirmationId")
    protected String affiliateConfirmationId;
    @JSONField(name = "CustomerIPAddress")
    protected String customerIPAddress;
    @JSONField(name = "IsGuaranteeOrCharged")
    protected boolean isGuaranteeOrCharged;
    @JSONField(name = "SupplierCardNo")
    protected String supplierCardNo;
    @JSONField(name = "IsNeedInvoice")
    protected boolean isNeedInvoice;
    @JSONField(name = "Contact")
    protected Contact contact;
    @JSONField(name = "ExtendInfo")
    protected ExtendInfo extendInfo;
    @JSONField(name = "NightlyRates")
    protected List<BaseNightlyRate> nightlyRates;
    @JSONField(name = "OrderRooms")
    protected List<CreateOrderRoom> orderRooms;
    @JSONField(name = "Invoice")
    protected Invoice invoice;
    @JSONField(name = "CreditCard")
    protected CreditCard creditCard;
    @JSONField(name = "IsForceGuarantee")
    protected Boolean isForceGuarantee;
    @JSONField(name = "IsCreateOrderOnly")
    protected Boolean isCreateOrderOnly;
    
    /**   
	 * 得到isCreateOrderOnly的值   
	 *   
	 * @return isCreateOrderOnly的值
	 */
	public Boolean getIsCreateOrderOnly() {
		return isCreateOrderOnly;
	}

	/**
	 * 设置isCreateOrderOnly的值
	 *   
	 * @param isCreateOrderOnly 被设置的值
	 */
	public void setIsCreateOrderOnly(Boolean isCreateOrderOnly) {
		this.isCreateOrderOnly = isCreateOrderOnly;
	}

    /**
     * Gets the value of the affiliateConfirmationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliateConfirmationId() {
        return affiliateConfirmationId;
    }

    /**
     * Sets the value of the affiliateConfirmationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliateConfirmationId(String value) {
        this.affiliateConfirmationId = value;
    }

    /**
     * Gets the value of the customerIPAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerIPAddress() {
        return customerIPAddress;
    }

    /**
     * Sets the value of the customerIPAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerIPAddress(String value) {
        this.customerIPAddress = value;
    }

    /**
     * Gets the value of the isGuaranteeOrCharged property.
     * 
     */
    public boolean isIsGuaranteeOrCharged() {
        return isGuaranteeOrCharged;
    }

    /**
     * Sets the value of the isGuaranteeOrCharged property.
     * 
     */
    public void setIsGuaranteeOrCharged(boolean value) {
        this.isGuaranteeOrCharged = value;
    }

    /**
     * Gets the value of the supplierCardNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierCardNo() {
        return supplierCardNo;
    }

    /**
     * Sets the value of the supplierCardNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierCardNo(String value) {
        this.supplierCardNo = value;
    }

    /**
     * Gets the value of the isNeedInvoice property.
     * 
     */
    public boolean isIsNeedInvoice() {
        return isNeedInvoice;
    }

    /**
     * Sets the value of the isNeedInvoice property.
     * 
     */
    public void setIsNeedInvoice(boolean value) {
        this.isNeedInvoice = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link Contact }
     *     
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contact }
     *     
     */
    public void setContact(Contact value) {
        this.contact = value;
    }

    /**
     * Gets the value of the extendInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendInfo }
     *     
     */
    public ExtendInfo getExtendInfo() {
        return extendInfo;
    }

    /**
     * Sets the value of the extendInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendInfo }
     *     
     */
    public void setExtendInfo(ExtendInfo value) {
        this.extendInfo = value;
    }

    /**
     * Gets the value of the nightlyRates property.
     * 
     * @return
     *     possible object is
     *     {@link List<BaseNightlyRate> }
     *     
     */
    public List<BaseNightlyRate> getNightlyRates() {
        return nightlyRates;
    }

    /**
     * Sets the value of the nightlyRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link List<BaseNightlyRate> }
     *     
     */
    public void setNightlyRates(List<BaseNightlyRate> value) {
        this.nightlyRates = value;
    }

    /**
     * Gets the value of the orderRooms property.
     * 
     * @return
     *     possible object is
     *     {@link List<CreateOrderRoom> }
     *     
     */
    public List<CreateOrderRoom> getOrderRooms() {
        return orderRooms;
    }

    /**
     * Sets the value of the orderRooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link List<CreateOrderRoom> }
     *     
     */
    public void setOrderRooms(List<CreateOrderRoom> value) {
        this.orderRooms = value;
    }

    /**
     * Gets the value of the invoice property.
     * 
     * @return
     *     possible object is
     *     {@link Invoice }
     *     
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Sets the value of the invoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Invoice }
     *     
     */
    public void setInvoice(Invoice value) {
        this.invoice = value;
    }

    /**
     * Gets the value of the creditCard property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCard }
     *     
     */
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the value of the creditCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCard }
     *     
     */
    public void setCreditCard(CreditCard value) {
        this.creditCard = value;
    }

    /**
     * Gets the value of the isForceGuarantee property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsForceGuarantee() {
        return isForceGuarantee;
    }

    /**
     * Sets the value of the isForceGuarantee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsForceGuarantee(Boolean value) {
        this.isForceGuarantee = value;
    }

}
