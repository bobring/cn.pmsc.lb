package com.pmsc.warning.client;

import org.apache.log4j.Logger;

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
import com.pmsc.warning.XmlBean.Method;
import com.pmsc.warning.XmlBean.WarnXML;
import com.pmsc.warning.WarnCap;

import cn.pmsc.lb.MyLog;
import cn.pmsc.lb.MySimpleJson;
import cn.pmsc.lb.MyTime;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

  
public class Client {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Client.class);  
	
	/**
	 * 待显示的预警信息序号，从1开始
	 * 涉及方法print_warncap
	 */
	private static int seq_num = 1;
	
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
//						MyLog.info("file already exists: " + fileName);

						if (logger.isInfoEnabled()) {
							logger.info("file already exists: " + fileName); //$NON-NLS-1$
						}

//						System.out.println("file already exists: " + fileName);
					} else {
						fileOutPutStream = new FileOutputStream(filepath);
						returnhandler = fsp.download(fileName);
						returnhandler.writeTo(fileOutPutStream);
						
						fileOutPutStream.flush();
						fileOutPutStream.close();
						
						if (logger.isInfoEnabled()) {
							logger.info("downloading file: " + fileName); //$NON-NLS-1$
						}
//						MyLog.info("downloading file: " + fileName);
						count++;
//						System.out.println("downloading file: " + fileName);
					}
				}
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("no zip files founded."); //$NON-NLS-1$
				}
//				MyLog.info("no zip files founded.");
//				System.out.println("no zip files founded.");
			}
			
			return count;
			
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 读取预警服务接口
	 * 返回以分钟为单位的一段时间内的预警数据
	 * @return List<WarnCap>，返回值为WarnCap类型的序列
	 */
	public static List<WarnCap> wsdemo_getwarncaplist(String area_code, String level, 
			String event_code, int minutes) {
		if(minutes <= 0){
			System.out.println("method wsdemo_getwarncaplist param minutes must be a positive integer.");
			return null;
		}
		
//		FileShareService fss = null;
		FileShare fsp = null;
		
		try {
			//从wsdl文件中获得服务名
//			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = (FileShare)context.getBean("client");
			//通过服务的协议调用提供的方法
			List<WarnCap> files = fsp.listWarnCapByElement(area_code, level, event_code, minutes);
			
			return files;
		} catch (Exception e) {
//			throw e;
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static WarnCap search_WarnID(String WarnID, List<WarnCap> WList) {
		for(WarnCap a:WList) {
			if(WarnID.equals(a.getIdentifier())) {
				return a;
			}
		}
		
		return null;
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
	
	
	public static Object warncaptoJSON(WarnCap wc) {
		JSONObject job = new JSONObject();
		
		job.put("alertid", wc.getIdentifier());
		job.put("sender", wc.getSender());
		job.put("status", wc.getMsgType());
		job.put("referid", wc.getReferencesInfo());
		job.put("eventcode", wc.getEventType());
		job.put("eventname", wc.getEventTypeCN());
		job.put("level", wc.getSeverity());
		job.put("title", wc.getHeadline());
		job.put("content", wc.getDescription());
		job.put("cancelinfo", wc.getNote());
		job.put("begintime", wc.getEffective());
		job.put("endtime", wc.getExpires());
		
		return job;
	}
	
	
	
	/**
	 * 在warnxmltoJSON方法中，用于转换"yyyy-MM-dd HH:mm:ss+08:00"格式的日期
	 * @param datestr
	 * @param wx
	 * @return
	 */
	private static String convert_time(String datestr, WarnXML wx) {
		String res = "";
		
		res = MyTime.Convert_DateStr(datestr);
		if(datestr.isEmpty()) {
			return datestr;
		} else if(res.isEmpty()) {
			logger.error("WarnID: " + wx.getIdentifier() + 
					" with unknown time format: " + datestr, null); //不加null会导致方法跳出
		}
		return res;
	}
	
	
	public static Object warnxmltoJSON(WarnXML wx) {
		JSONObject job = new JSONObject();
		String str = null;
		StringBuffer buf = null;
		
		
		job.put("alertId", wx.getIdentifier());
		job.put("senderName", wx.getSender());
		job.put("senderCode", wx.getSenderCode());
		
		
		job.put("sendTime", convert_time(wx.getSendTime(), wx));
		job.put("status", wx.getMsgType());
		
		
		
		List<Method> methods = wx.getCode().getMethod();
		if(methods.size() >= 1) {
			buf = new StringBuffer();
			for(Method a:methods) {
				buf.append(a.getMethodName() + ",");
			}
			buf.setLength(buf.length() - 1);
		}
		job.put("method", buf.toString());
		
		job.put("referId", wx.getReferences());
		job.put("eventCode", wx.getInfo().getEventType());
		
		List<WarnCap> wlist = wsdemo_getwarncaplist(wx.getSenderCode(), wx.getInfo().getSeverity(), 
				wx.getInfo().getEventType(), 720);
		str = null;
		
		if(wlist != null) {
			WarnCap wc = search_WarnID(wx.getIdentifier(), wlist);
			if(wc != null) {
				str = wc.getEventTypeCN();
			}
		}
		
		if(str != null) {
			job.put("eventName", str);
		} else {
			job.put("eventName", "");
		}
		
		
		job.put("level", wx.getInfo().getSeverity());
		job.put("title", wx.getInfo().getHeadline());
		job.put("content", wx.getInfo().getDescription());
		job.put("cancelInfo", wx.getNote());
		job.put("occurTime", convert_time(wx.getInfo().getOnset(), wx));
		job.put("beginTime", convert_time(wx.getInfo().getEffective(), wx));
		job.put("endTime", convert_time(wx.getInfo().getExpires(), wx));
		job.put("areaDesc", wx.getInfo().getArea().getAreaDesc());
		job.put("areaCode", wx.getInfo().getArea().getGeocode());
		
		return job;
	}
	
	
	/**
	 * 将预警信息以json格式写入数据接口
	 */
	public static void wsdemo_warncapToDB(List<WarnCap> files, URL dest_url) throws Exception {
		JSONArray array = null;
		
		try {
			if(files.size() > 1) {
				array = new JSONArray();
				
				for(WarnCap a:files) {
					array.add(warncaptoJSON(a));
//					MySimpleJson.insertJSONObject((JSONObject) warncaptoJSON(a));
				}
				System.out.println(array.toString());
				MySimpleJson.PostJSON(dest_url, array);
//				System.out.println(MySimpleJson.getJSONArray().toString());
//				MySimpleJson.PostJSON(dest_url, MySimpleJson.getJSONArray());
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
	
	
}  