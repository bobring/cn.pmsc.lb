package com.pmsc.warning.XmlBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType;  

/**
 * @author BobRing
 * 预警信息
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "info")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "language",
     "eventType",
     "urgency",
     "severity",
     "certainty",
     "effective",
     "onset",
     "expires",
     "senderName",
     "headline",
     "description",
     "instruction",
     "web",
     "area",
})

public class Info implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String language;
	protected String eventType;
	protected String urgency;
	protected String severity;
	protected String certainty;
	protected String effective;
	protected String onset;
	protected String expires;
	protected String senderName;
	protected String headline;
	protected String description;
	protected String instruction;
	protected String web;
	
	protected Area area;

	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Info(String language, String eventType, String urgency, String severity, String certainty, String effective,
			String onset, String expires, String senderName, String headline, String description, String instruction,
			String web, Area area) {
		super();
		this.language = language;
		this.eventType = eventType;
		this.urgency = urgency;
		this.severity = severity;
		this.certainty = certainty;
		this.effective = effective;
		this.onset = onset;
		this.expires = expires;
		this.senderName = senderName;
		this.headline = headline;
		this.description = description;
		this.instruction = instruction;
		this.web = web;
		this.area = area;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getCertainty() {
		return certainty;
	}

	public void setCertainty(String certainty) {
		this.certainty = certainty;
	}

	public String getEffective() {
		return effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}

	public String getOnset() {
		return onset;
	}

	public void setOnset(String onset) {
		this.onset = onset;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}
