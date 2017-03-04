package com.pmsc.warning.XmlBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType;  

/**
 * @author BobRing
 * 预警范围
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "area")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "areaDesc",
     "polygon",
     "circle",
     "geocode",
})

public class Area implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String areaDesc;
	protected String polygon;
	protected String circle;
	protected String geocode;
	
	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Area(String areaDesc, String polygon, String circle, String geocode) {
		super();
		this.areaDesc = areaDesc;
		this.polygon = polygon;
		this.circle = circle;
		this.geocode = geocode;
	}
	
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getPolygon() {
		return polygon;
	}
	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getGeocode() {
		return geocode;
	}
	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}
}
