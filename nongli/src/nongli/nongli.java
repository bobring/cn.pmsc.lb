package nongli;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 首先要明确导入的是哪些类
 *  1、Document用于生成文档，定义了格式
 *  2、Element用于为xml文档中的元素设置key和value
 *  3、XMLOutputter用于将已经设置好值的document输出到一个xml文档中。
 */


public class nongli {
	private GregorianCalendar gc = new GregorianCalendar();
	//private int begin_year, begin_month, begin_day;
	
	public nongli(int year, int month, int day){
		//gc = (GregorianCalendar) GregorianCalendar.getInstance();
		gc.set(year, month, day);
	}
	
	public nongli(Date now){
		//gc = (GregorianCalendar) GregorianCalendar.getInstance();
		gc.setTime(now);
	}
	
	// 写入ｘｍｌ文件
	 public static void callWriteXmlFile(Document doc, Writer w, String encoding) {
	  try {
	   Source source = new DOMSource(doc);
	   Result result = new StreamResult(w);
	   Transformer xformer = TransformerFactory.newInstance().newTransformer();
	   
	   xformer.setOutputProperty(OutputKeys.INDENT, "yes");
	   xformer.setOutputProperty(OutputKeys.ENCODING, encoding);
	   xformer.transform(source, result);
	  } catch (TransformerConfigurationException e) {
	   e.printStackTrace();
	  } catch (TransformerException e) {
	   e.printStackTrace();
	  }
	 }
	
	public void run(){
		int year = 0, month, day;
		
		SimpleDateFormat gongli = new SimpleDateFormat("yyyy-MM-dd");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
	    
	    try {
	    	   builder = dbf.newDocumentBuilder();
	    	  } catch (Exception e) {
	    	  }
	    	  Document doc = builder.newDocument();
	    	  Element root = doc.createElement("DATEFORMATS");
	    	  
	    	  //doc.appendChild( doc.createTextNode("\n    ") ); //补充换行缩进
	    	  doc.appendChild(root);// 将根元素添加到文档上
	    	  root.appendChild( doc.createTextNode("\n    ") ); //补充换行缩进
		
		do{
			//计算农历
			
			year = gc.get( Calendar.YEAR );
			month = gc.get( Calendar.MONTH ) + 1; //月份从0开始，需加1
			day = gc.get( Calendar.DAY_OF_MONTH );
			//Lunar MyLunar = new Lunar( year, month, day );
			
			LunarCalendar MyLunar = new LunarCalendar(gc.getTime());
			
			
			if( year <= 2049 ){
				Element dataline = doc.createElement("Format");
				/*
				dataline.setAttribute( "SolarDate", MyLunar.getGongLiDate() );
				dataline.setAttribute( "LunarYear", MyLunar.getLunarYear() );
				dataline.setAttribute( "LunarMonthDay", MyLunar.getLunarDay() );
				
				//节气不能为空，即二十四节气为分段区域性的，而不是点
				dataline.setAttribute( "LunarTerm", MyLunar.getMyTermString() );
				//dataline.setAttribute( "TermBeginTime", MyLunar.getMyTermBeginTime() );
				*/
				
				
				
				
				dataline.setAttribute( "SolarDate", gongli.format(gc.getTime()) );
//				dataline.setAttribute( "LunarYear", MyLunar.getYearString() + "年" );
				// 2017年3月4日新增，改为使用春节(大年初一)为分界线，上面的getYearString()方法是以二十四节气划分
				dataline.setAttribute( "LunarYear", MyLunar.getMycnEraYearString() + "年" );
				
				if(MyLunar.isLeap()) {
					dataline.setAttribute( "LunarMonthDay", "闰" + MyLunar.getMonthString() + "月" + MyLunar.getDayString() );
				} else {
					dataline.setAttribute( "LunarMonthDay", MyLunar.getMonthString() + "月" + MyLunar.getDayString() );
				}
				
				dataline.setAttribute( "LunarTerm", MyLunar.getMyTermString() );
				dataline.setAttribute( "TermBeginTime", MyLunar.getTermBeginTime() );
				
				root.appendChild(dataline); //将生成的节点添加到根元素上
				
				if( year == 2049 && month == 12 && day == 31 ){
					root.appendChild( doc.createTextNode("\n") ); //最后一个节点补充换行但不缩进
				}
				else{
					root.appendChild( doc.createTextNode("\n    ") ); //补充换行缩进
				}
				
			}
			
			else{
				break;
			}
			//日期加1
			gc.add(Calendar.DAY_OF_YEAR, 1);
			
			//System.out.println( MyLunar.getLunarYear() );
			
			
		} while( true );
		
		try {
			   FileOutputStream fos = new FileOutputStream("F:\\temp\\test.xml");
			   OutputStreamWriter outwriter = new OutputStreamWriter(fos, "gb2312");
			   // ((XmlDocument)doc).write(outwriter); //出错！
			   callWriteXmlFile(doc, outwriter, "gb2312"); //使用UTF-8编码，需要提前将写入xml的字符串转码
			   outwriter.close();
			   fos.close();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
	}
	
 public static void main(String[] args) {
	 nongli mynongli = new nongli(1900, 1, 1);
	 mynongli.run();

 }
}

