
package com.pmsc.warning.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listWarnCapByElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listWarnCapByElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="warnLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="warnEvent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listWarnCapByElement", propOrder = {
    "areaCode",
    "warnLevel",
    "warnEvent",
    "itime"
})
public class ListWarnCapByElement {

    protected String areaCode;
    protected String warnLevel;
    protected String warnEvent;
    protected int itime;

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    /**
     * Gets the value of the warnLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarnLevel() {
        return warnLevel;
    }

    /**
     * Sets the value of the warnLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarnLevel(String value) {
        this.warnLevel = value;
    }

    /**
     * Gets the value of the warnEvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarnEvent() {
        return warnEvent;
    }

    /**
     * Sets the value of the warnEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarnEvent(String value) {
        this.warnEvent = value;
    }

    /**
     * Gets the value of the itime property.
     * 
     */
    public int getItime() {
        return itime;
    }

    /**
     * Sets the value of the itime property.
     * 
     */
    public void setItime(int value) {
        this.itime = value;
    }

}
