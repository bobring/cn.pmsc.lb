
package com.pmsc.warning.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listFilesByWarnId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listFilesByWarnId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="warnId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listFilesByWarnId", propOrder = {
    "warnId"
})
public class ListFilesByWarnId {

    protected int warnId;

    /**
     * Gets the value of the warnId property.
     * 
     */
    public int getWarnId() {
        return warnId;
    }

    /**
     * Sets the value of the warnId property.
     * 
     */
    public void setWarnId(int value) {
        this.warnId = value;
    }

}
