//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.09 at 10:10:23 AM CST 
//


package com.elong.nb.model.elong;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnumInstantStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnumInstantStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NotInstant"/>
 *     &lt;enumeration value="Instant"/>
 *     &lt;enumeration value="Timeout"/>
 *     &lt;enumeration value="InvalidOrderId"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnumInstantStatus")
@XmlEnum
public enum EnumInstantStatus {

    @XmlEnumValue("NotInstant")
    NotInstant("NotInstant"),
    @XmlEnumValue("Instant")
    Instant("Instant"),
    @XmlEnumValue("Timeout")
    Timeout("Timeout"),
    @XmlEnumValue("InvalidOrderId")
    InvalidOrderId("InvalidOrderId");
    private final String value;

    EnumInstantStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumInstantStatus fromValue(String v) {
        for (EnumInstantStatus c: EnumInstantStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
