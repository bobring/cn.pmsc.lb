package cn.pmsc.kafka;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {
	
	private static Element root;
	
	public static Element initRoot(File file){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			Document xmldoc = db.parse(file);
			return xmldoc.getDocumentElement();
		}
		catch (Exception e) {
//			InitServices.logger.error("0000000--000--" + InitServices.ip + "--读取XML文件[" + file.getPath() +"]根节点错误");
//			InitServices.logger.debug("0000000--000--" + InitServices.ip + "--",e);
		}
		
		return null;
	}

	// 查找节点，返回符合条件的节点集。
	public static NodeList selectNodes(String express, Object source) {
		NodeList result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (NodeList) xpath.evaluate(express, source,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
//			InitServices.logger.error("0000000--000--" + InitServices.ip + "--查找XML节点list[" + express + "]错误");
//			InitServices.logger.debug("0000000--000--" + InitServices.ip + "--",e);
		}

		return result;
	}

	public static Node selectSingleNode(String express, Object source) {// 查找节点，并返回第一个符合条件节点
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath
					.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
//			InitServices.logger.error("0000000--000--" + InitServices.ip + "--查找XML节点[" + express + "]错误");
//			InitServices.logger.debug("0000000--000--" + InitServices.ip + "--",e);
		}

		return result;
	}
	
	public static boolean ReadXML(String path) {
		if((root = initRoot(new File(path))) != null) {
			return true;
		}
		return false;
	}
	
	// 查找节点，返回符合条件的节点集。
	public static NodeList selectNodes(String express) {
		NodeList result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (NodeList) xpath.evaluate(express, root,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
//			InitServices.logger.error("0000000--000--" + InitServices.ip + "--查找XML节点list[" + express + "]错误");
//			InitServices.logger.debug("0000000--000--" + InitServices.ip + "--",e);
		}

		return result;
	}

	public static Node selectSingleNode(String express) {// 查找节点，并返回第一个符合条件节点
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath
					.evaluate(express, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
//			InitServices.logger.error("0000000--000--" + InitServices.ip + "--查找XML节点[" + express + "]错误");
//			InitServices.logger.debug("0000000--000--" + InitServices.ip + "--",e);
		}

		return result;
	}
}
