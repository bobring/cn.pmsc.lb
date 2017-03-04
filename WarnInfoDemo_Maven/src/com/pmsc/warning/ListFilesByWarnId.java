
package com.pmsc.warning;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>listFilesByWarnId complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取warnId属性的值。
     * 
     */
    public int getWarnId() {
        return warnId;
    }

    /**
     * 设置warnId属性的值。
     * 
     */
    public void setWarnId(int value) {
        this.warnId = value;
    }

}
