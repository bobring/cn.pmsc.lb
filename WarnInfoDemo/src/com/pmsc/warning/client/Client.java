package com.pmsc.warning.client;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;  

import org.springframework.context.support.ClassPathXmlApplicationContext;  
  




import com.pmsc.warning.FileShare;

import cn.pmsc.lb.MyLog;

import com.pmsc.warning.*;  
  
public class Client {  
	
	private static ClassPathXmlApplicationContext context = null;
	
	static {
		try {
			context=new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("XML config file Read error!");
		}
	}
	
	/**
	 * 待显示的预警信息序号，从1开始
	 * 涉及方法print_warncap
	 */
//	private static int seq_num = 1;
	
	
	/**
	 * 将输入的字符串按regex分割，取出第num段字符串作为返回值
	 * @param str 输入字符串, regex 分隔符, num 从0开始的字符串序号
	 * @return
	 */
	public static String cutstr(String str, String regex, int num){
		String[] s1 = str.split(regex);
		
		if(s1.length > num) {
			return s1[num];
		} else {
			return null;
		}
	}
	
	
	
	/**
	 * 将输入的字符串按小数点分割，取出第一段字符串作为返回值
	 * @param str 输入字符串
	 * @return
	 */
	public static String cutstr(String str){
		Integer i = str.indexOf(".");
		
		if(i >= 0 && i < str.length()) {
			return str.substring(0, i);
		}
		return str;
	}
	
	
	
	/**
	 * 比较两个字符串形式的日期的先后顺序
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static Boolean comparedatestr(String str1, String str2, String format){
		SimpleDateFormat sdf =new SimpleDateFormat(format);
		
		try {
			Date t1 = sdf.parse(str1);
			Date t2 = sdf.parse(str2);
			
			if(t1.before(t2)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * jdk1.7中排序，compare()方法的返回值必须是1和-1
	 *
	 */
	class SortByTime implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			WarnCap s1 = (WarnCap) o1;
			WarnCap s2 = (WarnCap) o2;
			
			if (comparedatestr(cutstr(s1.getEffective()), cutstr(s2.getEffective()), "yyyy-MM-dd HH:mm:ss")){
				return 1;
			} else{
				return -1;
			}
		}
	}
	
	
	/**
	 * jdk1.7中排序，compare()方法的返回值必须是1和-1
	 *
	 */
	class SortByFileName implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			String[] s1 = o1.toString().split("_");
			String[] s2 = o2.toString().split("_");
			
			if (comparedatestr(s1[1], s2[1], "yyyyMMddHHmmss")){
				return 1;
			} else{
				return -1;
			}
		}
	}
	
	
	public static boolean download(String Dir, String fileName) {
//		FileShareService fss = null;
		FileShare fsp = null;
		
		DataHandler returnhandler = null;
		FileOutputStream fileOutPutStream = null;
		
		try {
			//从wsdl文件中获得服务名
//			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = (FileShare)context.getBean("client");
			//通过服务的协议调用提供的方法
			fileOutPutStream = new FileOutputStream(Dir + File.separator + fileName);
			returnhandler = fsp.download(fileName);
			returnhandler.writeTo(fileOutPutStream);
			
			fileOutPutStream.flush();
			fileOutPutStream.close();
			
			return true;
		} catch (Exception e) {
//			throw e;
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static int wsdemo_downloadzipfiles(String Dir, int minutes) throws Exception {
//		FileShareService fss = null;
		FileShare fsp = null;
		
		int num = 0; //zip文件个数
		int count = 0; //实际下载文件总数
		
		try {
			//从wsdl文件中获得服务名
//			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = (FileShare)context.getBean("client");
			//通过服务的协议调用提供的方法
			List<String> files = fsp.listFilesByElement("A", "Red,Orange,Yellow,Blue", "A", minutes);
			
			List<String> maxfiles = fsp.listFilesByWarnId(fsp.getMaxWarnId());
			
			for(String a:maxfiles) {
				String fileName = a.substring(0, a.lastIndexOf("_"));
				files.add(fileName);
				System.out.println("maxfiles: " + fileName);
			}
			
			num = files.size();
			if(num != 0) {
				DataHandler returnhandler = null;
				FileOutputStream fileOutPutStream = null;
				
				for(String fileName:files) {
					fileOutPutStream = new FileOutputStream(Dir + File.separator + fileName);
					returnhandler = fsp.download(fileName);
					returnhandler.writeTo(fileOutPutStream);
					
					fileOutPutStream.flush();
					fileOutPutStream.close();
					System.out.println("downloading file: " + fileName);
					count++;
				}
			} else {
				System.out.println("no zip files founded.");
			}
			
			return count;
			
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	public static int wsdemo_downloadzipfiles(String Dir) throws Exception {
//		FileShareService fss = null;
		FileShare fsp = null;
		
		int num = 0; //zip文件个数
		int count = 0; //实际下载文件总数
		
		try {
			//从wsdl文件中获得服务名
//			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = (FileShare)context.getBean("client");
			//通过服务的协议调用提供的方法
			List<String> files = fsp.listFilesByTop(100);
			
			List<String> maxfiles = fsp.listFilesByWarnId(fsp.getMaxWarnId());
			
			for(String a:maxfiles) {
				String fileName = a.substring(0, a.lastIndexOf("_"));
				files.add(fileName);
				System.out.println("maxfiles: " + fileName);
			}
			
			num = files.size();
			if(num != 0) {
				DataHandler returnhandler = null;
				FileOutputStream fileOutPutStream = null;
				String filepath = null;
				
				for(String fileName:files) {
					filepath = Dir + File.separator + fileName;
					
					File file = new File(filepath);
					if(file.isFile() && file.length() > 0) {
						MyLog.info("file already exists: " + fileName);
//						System.out.println("file already exists: " + fileName);
					} else {
						fileOutPutStream = new FileOutputStream(filepath);
						returnhandler = fsp.download(fileName);
						returnhandler.writeTo(fileOutPutStream);
						
						fileOutPutStream.flush();
						fileOutPutStream.close();
						MyLog.info("downloading file: " + fileName);
						count++;
//						System.out.println("downloading file: " + fileName);
					}
				}
			} else {
				MyLog.info("no zip files founded.");
//				System.out.println("no zip files founded.");
			}
			
			return count;
			
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
  
}  