package cn.pmsc.lb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class MyProperties {
	
	public Properties properties = null;
	
	public MyProperties(String path) throws Exception {
		properties = new Properties();
//        InputStream inputStream = MyProperties.class.getResourceAsStream(path);
//        FileInputStream fis = null;
        //解决中文乱码
        BufferedReader buff = null;
        
        try {
        	buff = new BufferedReader(new FileReader(path));
//        	fis = new FileInputStream(new File(path));
//        	properties.load(fis);
        	properties.load(buff);
        	buff.close();
//        	fis.close();
//        	inputStream.close();
        } catch(Exception e) {
        	throw e;
        }
	}
	
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
