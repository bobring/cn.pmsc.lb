
package com.pmsc.warning.XmlBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author BobRing
 * 预警XML文件
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "alert")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "identifier",
     "sender",
     "senderCode",
     "sendTime",
     "status",
     "msgType",
     "scope",
     "code",
     "secClassification",
     "note",
     "references",
     "info",
})


public class WarnXML implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String identifier;
	protected String sender;
	protected String senderCode;
	protected String sendTime;
	protected String status;
	protected String msgType;
	protected String scope;
	
	protected Code code;
	
	protected String secClassification;
	protected String note;
	protected String references;
	
	protected Info info;
	
	public WarnXML() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WarnXML(String identifier, String sender, String senderCode, String sendTime, String status, String msgType,
			String scope, Code code, String secClassification, String note, String references, Info info) {
		super();
		this.identifier = identifier;
		this.sender = sender;
		this.senderCode = senderCode;
		this.sendTime = sendTime;
		this.status = status;
		this.msgType = msgType;
		this.scope = scope;
		this.code = code;
		this.secClassification = secClassification;
		this.note = note;
		this.references = references;
		this.info = info;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderCode() {
		return senderCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public String getSecClassification() {
		return secClassification;
	}

	public void setSecClassification(String secClassification) {
		this.secClassification = secClassification;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
	
	
}
