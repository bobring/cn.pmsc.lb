package com.pmsc.warning.test;


import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import com.pmsc.warning.client.*; 
import cn.pmsc.lb.*;
import net.sf.json.JSONObject;


public class Check {
	
	/**
	 * 待显示的预警信息序号，从1开始
	 * 涉及方法print_warncap
	 */
	private static int seq_num = 1;
	
	
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
	public static Boolean comparedatestr(String str1, String str2){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
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
	class SortByTime implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			WarnCap s1 = (WarnCap) o1;
			WarnCap s2 = (WarnCap) o2;
			
			if (comparedatestr(cutstr(s1.getEffective()), cutstr(s2.getEffective()))){
				return 1;
			} else{
				return -1;
			}
		}
	}
	
	
	
	public static int wsdemo_downloadzipfiles(String Dir, URL url, int minutes) throws Exception {
		FileShareService fss = null;
		FileShare fsp = null;
		
		int num = 0; //zip文件个数
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
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
				}
			} else {
				System.out.println("no zip files founded.");
			}
			
			return num;
			
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	public static int wsdemo_downloadzipfiles(String Dir, URL url) throws Exception {
		FileShareService fss = null;
		FileShare fsp = null;
		
		int num = 0; //zip文件个数
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
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
						System.out.println("file already exists: " + fileName);
					} else {
						fileOutPutStream = new FileOutputStream(filepath);
						returnhandler = fsp.download(fileName);
						returnhandler.writeTo(fileOutPutStream);
						
						fileOutPutStream.flush();
						fileOutPutStream.close();
						System.out.println("downloading file: " + fileName);
					}
				}
			} else {
				System.out.println("no zip files founded.");
			}
			
			return num;
			
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 读取预警服务接口
	 * 返回最近到达的前num个zip文件名清单，num最大值为一百
	 * 解决标签id不规范导致访问接口调取不到的问题
	 * @return List<String>，返回值为String类型的文件名列表
	 */
	public static List<String> wsdemo_gettopziplist(URL url, int num) throws Exception {
		if(num <= 0 || num > 100){
			System.out.println("method wsdemo_gettopziplist param num must be a positive integer and less than 100.");
			return null;
		}
		
		FileShareService fss = null;
		FileShare fsp = null;
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
			//通过服务的协议调用提供的方法
			List<String> files = fsp.listFilesByTop(num);
			
			
			return files;
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	/**
	 * 读取预警服务接口
	 * 返回minutes分钟内到达的zip文件名清单
	 * @return List<String>，返回值为String类型的文件名列表
	 */
	public static List<String> wsdemo_getziplist(URL url, int minutes) throws Exception {
		if(minutes <= 0){
			System.out.println("method wsdemo_getziplist param minutes must be a positive integer.");
			return null;
		}
		
		FileShareService fss = null;
		FileShare fsp = null;
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
			//通过服务的协议调用提供的方法
			List<String> files = fsp.listFilesByElement("A", "Red,Orange,Yellow,Blue", "A", minutes);
			
			
			return files;
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	/**
	 * 读取预警服务接口
	 * 返回10分钟内到达的zip文件名清单
	 * @return List<String>，返回值为String类型的文件名列表
	 */
	public static List<String> wsdemo_getziplist(URL url) throws Exception {
		FileShareService fss = null;
		FileShare fsp = null;
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
			//通过服务的协议调用提供的方法
			List<String> files = fsp.listFiles();
			return files;
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	public static Object warncaptoJSON(WarnCap wc) {
		MySimpleJson.insertJSONElement("alertid", wc.getIdentifier());
		MySimpleJson.insertJSONElement("sender", wc.getSender());
		MySimpleJson.insertJSONElement("status", wc.getMsgType());
		MySimpleJson.insertJSONElement("referid", wc.getReferencesInfo());
		MySimpleJson.insertJSONElement("eventcode", wc.getEventType());
		MySimpleJson.insertJSONElement("eventname", wc.getEventTypeCN());
		MySimpleJson.insertJSONElement("level", wc.getSeverity());
		MySimpleJson.insertJSONElement("title", wc.getHeadline());
		MySimpleJson.insertJSONElement("content", wc.getDescription());
		MySimpleJson.insertJSONElement("cancelinfo", wc.getNote());
		MySimpleJson.insertJSONElement("begintime", wc.getEffective());
		MySimpleJson.insertJSONElement("endtime", wc.getExpires());
		
		return MySimpleJson.getJSONObject();
	}
	
	
	/**
	 * 读取预警服务接口
	 * 返回最近10分钟内的预警数据并写入公服接口
	 * @return List<WarnCap>，返回值为WarnCap类型的序列
	 */
	public static void wsdemo_warncapToDB(URL source_url, URL dest_url) throws Exception {
		
		try {
			//取得10分钟内的warncap数据
			List<WarnCap> files = wsdemo_getwarncaplist(source_url);
			
			if(files.size() > 1) {
				for(WarnCap a:files) {
					MySimpleJson.insertJSONObject((JSONObject) warncaptoJSON(a));
				}
				MySimpleJson.PostJSON(dest_url, MySimpleJson.getJSONArray());
			} else if(files.size() == 1) {
				for(WarnCap a:files) {
					MySimpleJson.PostJSON(dest_url, warncaptoJSON(a));
				}
			} else {
				System.out.println("no new warning info insert to database.");
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 读取预警服务接口
	 * 返回最近10分钟内的预警数据
	 * @return List<WarnCap>，返回值为WarnCap类型的序列
	 */
	public static List<WarnCap> wsdemo_getwarncaplist(URL url) throws Exception {
		FileShareService fss = null;
		FileShare fsp = null;
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
			//通过服务的协议调用提供的方法
			List<WarnCap> files = fsp.listWarnCap();
			return files;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 读取预警服务接口
	 * 返回以分钟为单位的一段时间内的预警数据
	 * @return List<WarnCap>，返回值为WarnCap类型的序列
	 */
	public static List<WarnCap> wsdemo_getwarncaplist(URL url, int minutes) throws Exception {
		if(minutes <= 0){
			System.out.println("method wsdemo_getwarncaplist param minutes must be a positive integer.");
			return null;
		}
		
		FileShareService fss = null;
		FileShare fsp = null;
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
			//通过服务的协议调用提供的方法
			List<WarnCap> files = fsp.listWarnCapByElement("A", "Red,Orange,Yellow,Blue", "A", minutes);
			
			return files;
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	/**
	 * 打印一条预警信息里的所有字段
	 * @param data
	 */
	public static void print_warncap(WarnCap data){
//		Integer i = 1;
		
		System.out.println("序号：" + seq_num);
		
		System.out.println("预警消息唯一标示：" + data.getIdentifier());
		System.out.println("预警发送单位：" + data.getSender());
		System.out.println("预警信息状态：" + data.getMsgType());
		System.out.println("引用的预警信息：" + data.getReferencesInfo());
		System.out.println("预警事件类型编码：" + data.getEventType());
		System.out.println("预警事件类型名称：" + data.getEventTypeCN());
		System.out.println("预警级别：" + data.getSeverity());
		
		System.out.println("预警标题：" + data.getHeadline());
		System.out.println("预警信息正文：" + data.getDescription());
		System.out.println("对预警信息解除原因的说明：" + data.getNote());
		System.out.println("预警信息生效时间：" + data.getEffective());
		System.out.println("预警事件的失效时间：" + data.getExpires());
		
		System.out.println("---------------------------------------------");
		seq_num++;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			URL url = new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl");
//			List<WarnCap> wc_list = Client.wsdemo_getwarncaplist(url, 720);
////	    	//对预警信息进行排序
//	    	Collections.sort(wc_list, new Client().new SortByTime());
			
			List<String> zip_list = Client.wsdemo_getziplist(url, 720);
			List<String> topzip_list = Client.wsdemo_gettopziplist(url, 100);

	    	
	    	//打印输出结果
	    	for(String b: topzip_list) {
	    		boolean isexists = false;
//	    		String[] id = b.split("_");
	    		
	    		for(String a: zip_list) {
	    			if(b.equals(a)) {
	    				isexists = true;
	    				break;
	    			}
//	    			if(id.length >= 2 && a.getIdentifier().equals(id[0] + "_" + id[1])) {
//	    				isexists = true;
//	    				break;
//	    			}
		    	}
	    		
	    		if(!isexists) {
	    			System.out.println(b);
//	    			Client.print_warncap(a);
	    		}
	    	}
//	    	
//	    	for(String b: zip_list) {
//	    		System.out.println(b);
//	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	


	}

}
