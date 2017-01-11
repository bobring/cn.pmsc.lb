package cn.pmsc.lb;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.Properties;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyLog {
	private static Logger logger = Logger.getLogger(MyLog.class);
	private static Properties props = new Properties();
	private static String business_id = "0000000";
	private static String process_id = "000";
	
	private static String logstr() {
		String str = business_id + "--" + process_id + "--"
				+ MySystemInfo.get_ipaddr() + "--" 
				+ MySystemInfo.get_username() + "--";
		return str;
	}
	
	
	public static void set_business_id(String id) {
		business_id = id;
	}
	
	public static void set_process_id(String id) {
		process_id = id;
	}
	
	public static String get_business_id() {
		return business_id;
	}
	
	public static String get_process_id() {
		return process_id;
	}
	
	public static void load_log4jcfg(String filepath) {
		
		BufferedReader buff = null;
		try {
//			PropertyConfigurator.configure(filepath);
//			Properties props = new Properties();
//			FileInputStream log4jStream = new FileInputStream(filepath);
//            props.load(log4jStream);
//            log4jStream.close();
//            PropertyConfigurator.configure(props); //装入log4j配置信息
			buff = new BufferedReader(new FileReader(filepath));
			props.load(buff);
        	buff.close();
    	} catch(FileNotFoundException fnfe) {
        	System.out.println("failed to open file: " + filepath);
        	fnfe.printStackTrace();
        } catch(IOException ioe) {
        	System.out.println("failed to decode file: " + filepath);
        	ioe.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public static void userdefine_prop(String key, Object value) {
		System.out.println("before changed, key:" + key + " value:" + props.getProperty(key));
		props.setProperty(key, value.toString());
		System.out.println("after changed, key:" + key + " value:" + props.getProperty(key));
	}
	
//	public static void show() {
//		System.out.println("");
//	}
	
	public static void flush() {
		try {
			PropertyConfigurator.configure(props);
		} catch (Exception e) {
    		System.out.println("log4j配置文件解析失败.");
    		e.printStackTrace();
    	}
	}
	
	public static void info(String msg) {
		logger.info(logstr() + msg);
	}
	
	public static void warn(String msg) {
		logger.warn(logstr() + msg);
	}
	
	public static void error(String msg) {
		logger.error(logstr() + msg);
	}
	
	public static void debug(Exception e) {
		logger.debug(logstr(), e);
	}
}
