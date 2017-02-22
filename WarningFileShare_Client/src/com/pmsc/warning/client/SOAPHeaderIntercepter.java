package com.pmsc.warning.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SOAPHeaderIntercepter extends AbstractSoapInterceptor
{
  private AuthHeader authHeader;

  public SOAPHeaderIntercepter()
  {
    super("write");
  }

  public void handleMessage(SoapMessage soapMessage) throws Fault
  {
    List headers = soapMessage.getHeaders();
    headers.add(getHeader());
  }

  private Object getHeader() {
	  QName qName = new QName(AuthHeader.getQname(), this.authHeader.getUsername(), "");
    Document document = DOMUtils.createDocument();
    Element element = document.createElement("authheader");
    Element username = document.createElement("username");
    username.setTextContent(this.authHeader.getUsername());
    element.appendChild(username);
    
    Element password = document.createElement("password");

    password.setTextContent(this.authHeader.getPassword());
    element.appendChild(password);
    
    
    
    SoapHeader header = new SoapHeader(qName, element);
    return header;
  }

  public AuthHeader getAuthHeader() {
    return this.authHeader;
  }

  public void setAuthHeader(AuthHeader authHeader) {
    this.authHeader = authHeader;
  }
  
  
}