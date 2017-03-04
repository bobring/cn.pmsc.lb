package com.pmsc.warning.XmlBean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType;  

/**
 * @author BobRing
 * 预警编码
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "code")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "method",
})

public class Code implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<Method> method;
	
	public Code() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Code(List<Method> method) {
		super();
		this.method = method;
	}

	public List<Method> getMethod() {
		return method;
	}

	public void setMethod(List<Method> method) {
		this.method = method;
	}
}
