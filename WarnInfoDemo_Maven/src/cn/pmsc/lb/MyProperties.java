package cn.pmsc.lb;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

public class MyProperties {
	
	public Properties properties = new Properties();
	
	public MyProperties(Reader reader) throws Exception {
//		properties = new Properties();
//        InputStream inputStream = MyProperties.class.getResourceAsStream(path);
//        FileInputStream fis = null;
        //解决中文乱码
//        BufferedReader buff = null;
        
        try {
//        	buff = new BufferedReader(ss);
//        	fis = new FileInputStream(new File(path));
//        	properties.load(fis);
        	properties.load(reader);
        	reader.close();
//        	fis.close();
//        	inputStream.close();
        } catch(Exception e) {
        	throw e;
        }
	}
	
	public MyProperties(InputStream ss) throws Exception {
//		properties = new Properties();
//        InputStream inputStream = MyProperties.class.getResourceAsStream(path);
//        FileInputStream fis = null;
        //解决中文乱码
//        BufferedReader buff = null;
        
        try {
//        	buff = new BufferedReader(ss);
//        	fis = new FileInputStream(new File(path));
//        	properties.load(fis);
        	properties.load(ss);
        	ss.close();
//        	fis.close();
//        	inputStream.close();
        } catch(Exception e) {
        	throw e;
        }
	}
//	
//	public MyProperties(FileReader fr) throws Exception {
//		properties = new Properties();
////        InputStream inputStream = MyProperties.class.getResourceAsStream(path);
////        FileInputStream fis = null;
//        //解决中文乱码
//        BufferedReader buff = null;
//        
//        try {
//        	buff = new BufferedReader(fr);
////        	fis = new FileInputStream(new File(path));
////        	properties.load(fis);
//        	properties.load(buff);
//        	buff.close();
////        	fis.close();
////        	inputStream.close();
//        } catch(Exception e) {
//        	throw e;
//        }
//	}
	
	public String getMyProperties(String key) throws Exception {
		String value = null;
		
		try {
			value = properties.getProperty(key);
			return value;
		} catch(Exception e) {
			throw e;
		}
	}

}
