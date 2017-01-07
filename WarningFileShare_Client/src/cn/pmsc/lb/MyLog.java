package cn.pmsc.lb;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class MyLog {
	private static Logger logger = Logger.getLogger(MyLog.class);
	private static Properties props = new Properties();
	
	private static String logstr(String level) {
		String str = MyTime.get_14bitstimeformat() + "--" 
				+ level + "--0000000--000--"
				+ MySystemInfo.get_ipaddr() + "--" 
				+ MySystemInfo.get_username() + "--";
		return str;
	}
	
	public static void load_log4jcfg(String filepath) {
		
		BufferedReader buff = null;
		try {
//			Properties props = new Properties();
//			FileInputStream log4jStream = new FileInputStream(filepath);
//            props.load(log4jStream);
//            log4jStream.close();
//            PropertyConfigurator.configure(props); //装入log4j配置信息
			buff = new BufferedReader(new FileReader(filepath));
			props.load(buff);
        	buff.close();
//    		PropertyConfigurator.configure(filepath);
//        	flush();
    	} catch(FileNotFoundException fnfe) {
        	System.out.println("failed to open file: " + filepath);
        	fnfe.printStackTrace();
        } catch(IOException ioe) {
        	System.out.println("failed to decode file: " + filepath);
        	ioe.printStackTrace();
        } catch(Exception e) {
//        	System.out.println("failed to decode file: " + filepath);
        	e.printStackTrace();
        }
	}
	
	public static void userdefine_prop(String key, String value) {
		props.setProperty(key, value);
	}
	
	public static void flush() {
		try {
			PropertyConfigurator.configure(props);
		} catch (Exception e) {
    		System.out.println("log4j配置文件解析失败.");
    		e.printStackTrace();
    	}
	}
	
	public static void info(String msg) {
		logger.info(logstr("INFO") + msg);
	}
	
	public static void error(String msg) {
		logger.error(logstr("ERROR") + msg);
	}
	
	public static void debug(Exception e) {
		logger.debug(logstr("DEBUG"), e);
	}
}
