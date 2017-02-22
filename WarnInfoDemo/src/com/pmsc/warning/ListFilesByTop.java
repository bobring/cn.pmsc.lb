
package com.pmsc.warning;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>listFilesByTop complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="listFilesByTop">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="record" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listFilesByTop", propOrder = {
    "record"
})
public class ListFilesByTop {

    protected int record;

    /**
     * 获取record属性的值。
     * 
     */
    public int getRecord() {
        return record;
    }

    /**
     * 设置record属性的值。
     * 
     */
    public void setRecord(int value) {
        this.record = value;
    }

}
