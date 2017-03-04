package com.pmsc.warning.XmlBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType;  

/**
 * @author BobRing
 * 预警方法
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "method")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "methodName",
     "message",
     "audienceGrp",
     "audenceprt",
})

public class Method implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String methodName;
	protected String message;
	protected String audienceGrp;
	protected String audenceprt;
	
	public Method() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Method(String methodName, String message, String audienceGrp, String audenceprt) {
		super();
		this.methodName = methodName;
		this.message = message;
		this.audienceGrp = audienceGrp;
		this.audenceprt = audenceprt;
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAudienceGrp() {
		return audienceGrp;
	}
	public void setAudienceGrp(String audienceGrp) {
		this.audienceGrp = audienceGrp;
	}
	public String getAudenceprt() {
		return audenceprt;
	}
	public void setAudenceprt(String audenceprt) {
		this.audenceprt = audenceprt;
	}
}
