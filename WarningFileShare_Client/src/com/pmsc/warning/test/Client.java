package com.pmsc.warning.test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import com.pmsc.warning.service.*; 
import cn.pmsc.lb.*;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

public class Client {  
	
	
	/**
	 * 待显示的预警信息序号，从1开始
	 * 涉及方法print_warncap
	 */
	private static int seq_num = 1;
	
	
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
	
	
	public static boolean download(String Dir, URL url, String fileName) {
		FileShareService fss = null;
		FileShare fsp = null;
		
		DataHandler returnhandler = null;
		FileOutputStream fileOutPutStream = null;
		
		try {
			//从wsdl文件中获得服务名
			fss = new FileShareService(url);
			//获得当前服务提供的类型
			fsp = fss.getFileSharePort();
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
	
	public static int wsdemo_downloadzipfiles(String Dir, URL url, int minutes) throws Exception {
		FileShareService fss = null;
		FileShare fsp = null;
		
		int num = 0; //zip文件个数
		int count = 0; //实际下载文件总数
		
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
	
	
	public static int wsdemo_downloadzipfiles(String Dir, URL url) throws Exception {
		FileShareService fss = null;
		FileShare fsp = null;
		
		int num = 0; //zip文件个数
		int count = 0; //实际下载文件总数
		
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
  
	
	
	
    /**
     * 主方法main
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
    	
    	
//    	Client.wsdemo_downloadzipfiles("F:\\temp\\data", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	
    	if (args.length < 5) {
			throw new NullPointerException(
					"enter the program cfgfile, the log4j cfgfile, the logfile, business id and process id");
		}
    	
    	String program_cfgfile = args[0];
    	String log4j_cfgfile = args[1];
    	
    	MyProperties program_config = null;
    	String outdir = null;
    	String warn_url = null;
    	
    	try {
    		program_config = new MyProperties(program_cfgfile);
    		outdir = program_config.getMyProperties("outdir");
    		warn_url = program_config.getMyProperties("warn_url");
    	} catch(Exception e) {
    		System.out.println("error happened while reading program cfgfile: " + program_cfgfile);
    		throw new RuntimeException(e); 
    	}
    	
    	MyLog.set_business_id(args[3]);
		MyLog.set_process_id(args[4]);
		
    	try {
//    		System.setProperty("warncap_logpath", args[2]); //设置日志输出路径
//    		System.out.println(System.getProperty("warncap_logpath").toString());
    		MyLog.load_log4jcfg(log4j_cfgfile);
    		MyLog.userdefine_prop("log4j.appender.logFile.File", args[2]);
//    		MyLog.userdefine_prop("appender.E.fileName", args[2]);
    		MyLog.flush();
    	} catch(Exception e) {
    		System.out.println("error happened while reading log4j cfgfile: " + log4j_cfgfile);
    	}
    	
    	int file_count = 0;
//    	Client.wsdemo_downloadzipfiles("/tmp", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	try {
    		file_count = Client.wsdemo_downloadzipfiles(outdir, new URL(warn_url));
    		
    		
    		if(file_count == 0) {
    			//输出1，表示无更新数据
    		} else {
    			//输出0，表示有正常数据下载
    		}
    	} catch (Exception e) {
    		
    	}
    	
        
    	
    	

//    	System.out.println(MySystemInfo.get_currrentpath() + File.separator.toString());

//    	

    	
//    	String log4j_cfgfile = MySystemInfo.get_currrentpath() + File.separator + "log4j2.properties";
//    	System.out.println(log4j_cfgfile);

//    	MyLog.load_log4jcfg("log4j.properties");
//    	MyLog.load_log4jcfg(log4j_cfgfile); //读取log4j日志配置文件
//    	MyLog.load_log4jcfg(MySystemInfo.get_currrentpath() + File.separator + "log4j.properties"); //读取log4j日志配置文件
    	
//    	List<WarnCap> wc_list = Client.wsdemo_getwarncaplistbymin(1440);
//    	
//    	//对预警信息进行排序
//    	Collections.sort(wc_list, new Client().new SortByTime());
    	
//    	int i = 0;
//    	int j = 0;
    	
//    	for(;i < wc_list.size(); i++) {
//    		WarnCap a = wc_list.get(i);
//    		for(j = i + 1;j < wc_list.size(); j++) {
//    			WarnCap b = wc_list.get(j);
//    			
//    			//相同气象局的相同预警类型，不论是alert,update,cancel，只显示时间最新的一个，其他的均重置为cancel，不予显示
//    			if(!"Cancel".equals(b.getMsgType()) && b.getSender().equals(a.getSender()) && b.getEventType().equals(a.getEventType())) {
//					b.setMsgType("Cancel");
//				}
//    		}
//    		
//    		
//    	}
    	
    	//打印输出结果
//    	for(WarnCap a: wc_list) {
//    		if(!"Cancel".equals(a.getMsgType())) {
//    			Client.print_warncap(a);
//    			//System.out.println("预警信息生效时间：" + a.getEffective());
//    		}
//    	}
    }  
  
}  