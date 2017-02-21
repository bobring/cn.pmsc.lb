package com.pmsc.warning.test;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class ReadXML {
	
	private Document doc = null; //��ʼ���ڲ�XML DOCUMENT����
    
	private boolean XMLok = false; //��ʼ��XML�ļ����ڱ�־
	
	//��ֹ��ʼ������ʱ��XML�ļ�·��Ϊ��
	public ReadXML() {
		XMLok = false;
	}
	
	/**
	 * 解析XML文件
	 * 
	 * @param filepath
	 *            XML文件路径
	 * 
	 * @return boolean
	 *            XML解析成功标志，表示是否是正确的格式
	 */
	public boolean Read(String filepath) {
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
        try {
            DocumentBuilder builder=factory.newDocumentBuilder();
            System.out.println("Construct document builder success.");
            doc=builder.parse(new File(filepath));            
            System.out.println("Build xml document success.");
            XMLok = true;
        }catch(ParserConfigurationException e) {
        	System.out.println("Construct document builder error. ");
        	e.printStackTrace();
            XMLok = false;
        }catch(org.xml.sax.SAXException e) {
        	System.out.println("Parse xml file error. ");
        	e.printStackTrace();
            XMLok = false;
        }catch(IOException e) {
        	System.out.println("Read xml file error. ");
        	e.printStackTrace();
            XMLok = false;
        }
        
        return XMLok;
	}
	
	
	/**
	 * 读取XML文件内容
	 * 
	 * @param nodename
	 *            XML文件头标签
	 * 
	 * @return List<String[]>
	 *            
	 */
	public List<String[]> SelectNodes(String nodename) {
		if(! XMLok) {
			System.out.println("XML file is not OK.");
			return null;
		}
		
		List<String[]> sta_list = new ArrayList<String[]>();
		String StainfoSta, Longitude, Latitude;
		
		Element root_element = doc.getDocumentElement();
		
		NodeList list = root_element.getElementsByTagName(nodename);
		
		for(int i = 0; i < list.getLength(); i++) {
			StainfoSta = null;
			Longitude = null;
			Latitude = null;
			//if(nd.getNodeType() == Node.ELEMENT_NODE)
			Element ele = (Element) list.item(i);
			StainfoSta = ele.getAttribute("StainfoSta");
			Longitude = ele.getAttribute("Longitude");
			Latitude = ele.getAttribute("Latitude");
			
			if(StainfoSta.isEmpty() || Longitude.isEmpty() || Latitude.isEmpty()) {
				;
			} else {
				String[] site = {StainfoSta, Longitude, Latitude};
				sta_list.add(site);
			}
		}
		
		return sta_list;
	}
	
	/**
	 * 读取XML文件内容
	 * 
	 * @param nodename
	 *            XML文件头标签
	 * 
	 * @return List<String[]>
	 *            
	 */
	public List<String[]> SelectAttributes(String nodename) {
		if(! XMLok) {
			System.out.println("XML file is not OK.");
			return null;
		}
		
		List<String[]> sta_list = new ArrayList<String[]>();
		String StainfoSta, Longitude, Latitude;
		
		Element root_element = doc.getDocumentElement();
		
		NodeList list = root_element.getElementsByTagName(nodename);
		
		for(int i = 0; i < list.getLength(); i++) {
			StainfoSta = null;
			Longitude = null;
			Latitude = null;
			//if(nd.getNodeType() == Node.ELEMENT_NODE)
			Element ele = (Element) list.item(i);
			StainfoSta = ele.getAttribute("StainfoSta");
			Longitude = ele.getAttribute("Longitude");
			Latitude = ele.getAttribute("Latitude");
			
			if(StainfoSta.isEmpty() || Longitude.isEmpty() || Latitude.isEmpty()) {
				;
			} else {
				String[] site = {StainfoSta, Longitude, Latitude};
				sta_list.add(site);
			}
		}
		
		return sta_list;
	}
	
}
