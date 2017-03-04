package cn.pmsc.lb;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MyTime.class);
	
	public static Date get_currenttime() {
		return new Date();
	}
	
	/**
	 * @param regex, 时间格式正则表达式
	 * @return 返回所需格式的当前时间
	 */
	public static String get_timeformat(String regex) {
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		return sdf.format(new Date());
	}
	
	public static String get_stdtimeformat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String get_14bitstimeformat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/** 
	 * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法 
	 * @param s 
	 * @return 
	 */  
	public static String Convert_DateStr(String s)  
	{
		if(s.isEmpty()) {
//			logger.warn("Convert_DateStr(String) - input Date = NULL", null); //$NON-NLS-1$

			return "";
		}
		
	    String str = "";
	    Date date = null;
	    
	    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
		try {
			date = sd.parse(s);
			str=sdf.format(date);
			return str;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			date = sdf.parse(s);
			str = s;
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	    return str;  
	}  
}
