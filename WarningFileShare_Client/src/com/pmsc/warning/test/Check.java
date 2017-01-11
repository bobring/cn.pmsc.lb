package com.pmsc.warning.test;


import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.activation.DataHandler;

import com.pmsc.warning.client.FileShare;
import com.pmsc.warning.client.FileShareService;
import com.pmsc.warning.client.WarnCap;

import cn.pmsc.lb.*;


public class Check {
	
	public static void print_warncap(WarnCap data){
		
		System.out.println(data.getHeadline() + "   " + data.getEffective());
		
//		System.out.println("预警消息唯一标示：" + data.getIdentifier());
//		System.out.println("预警发送单位：" + data.getSender());
//		System.out.println("预警信息状态：" + data.getMsgType());
//		System.out.println("引用的预警信息：" + data.getReferencesInfo());
//		System.out.println("预警事件类型编码：" + data.getEventType());
//		System.out.println("预警事件类型名称：" + data.getEventTypeCN());
//		System.out.println("预警级别：" + data.getSeverity());
//		
//		System.out.println("预警标题：" + data.getHeadline());
//		System.out.println("预警信息正文：" + data.getDescription());
//		System.out.println("对预警信息解除原因的说明：" + data.getNote());
//		System.out.println("预警信息生效时间：" + data.getEffective());
//		System.out.println("预警事件的失效时间：" + data.getExpires());
//		
//		System.out.println("---------------------------------------------");
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
			List<String> filesbytop = fsp.listFilesByTop(100);
			List<String> filesbytime = fsp.listFilesByElement("A", "Red,Orange,Yellow,Blue", "A", minutes);
			List<WarnCap> WarnCapbytime = fsp.listWarnCapByElement("A", "Red,Orange,Yellow,Blue", "A", minutes);
			
			Collections.sort(filesbytop, new Client().new SortByFileName());
			
			num = filesbytop.size();
			
			if(num != 0) {
				DataHandler returnhandler = null;
				FileOutputStream fileOutPutStream = null;
				String filepath = null;
				
				String id = null;
				WarnCap data = null;
				
				for(String fileName:filesbytop) {
					filepath = Dir + File.separator + fileName;
					
					File file = new File(filepath);
					if(file.isFile() && file.length() > 0) { //文件已存在于本地
						System.err.println("file already exists: " + fileName);
					} else { //文件不存在
						
						if(filesbytime.contains(fileName) || 
								Client.cutstr(fileName, "_", 1).substring(0, 7).equals(MyTime.get_timeformat("yyyyMMdd"))) { 
							//文件名包含于时间列表，或者日期是当天日期，说明文件时间有效
							id = Client.cutstr(fileName, "_", 0) + "_" + Client.cutstr(fileName, "_", 1);
							data = Client.search_WarnID(id, WarnCapbytime);
							if(data != null) {
								Check.print_warncap(data);
							} else {
								System.err.println("can not find warn info of file: " + fileName);
							}
						} else {
							//文件名超出时间范围，判断为过期数据
							System.err.println("expired warn file: " + fileName);
						}
						fileOutPutStream = new FileOutputStream(filepath);
						returnhandler = fsp.download(fileName);
						returnhandler.writeTo(fileOutPutStream);
						
						fileOutPutStream.flush();
						fileOutPutStream.close();
//						MyLog.info("downloading file: " + fileName);
						System.err.println("downloading file: " + fileName);
						count++;
					}
				}
			} else {
//				MyLog.info("no zip files founded.");
				System.err.println("no zip files founded.");
			}
			
			return count;
			
		} catch (Exception e) {
			throw e;
//			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		int count = 0;
		
//		try {
//			URL url = new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl");
////			List<WarnCap> wc_list = Client.wsdemo_getwarncaplist(url, 720);
//////	    	//对预警信息进行排序
////	    	Collections.sort(wc_list, new Client().new SortByTime());
//			
//			List<String> zip_list = Client.wsdemo_getziplist(url, 720);
//			List<String> topzip_list = Client.wsdemo_gettopziplist(url, 100);
//			
//			Collections.sort(zip_list, new Client().new SortByFileName());
//
//	    	
//	    	//打印输出结果
//	    	for(String b: topzip_list) {
//	    		boolean isexists = false;
////	    		String[] id = b.split("_");
//	    		
//	    		for(String a: zip_list) {
//	    			if(b.equals(a)) {
//	    				isexists = true;
//	    				break;
//	    			}
//		    	}
//	    		
//	    		if(!isexists) {
////	    			MyLog.info("lack file: ");
//	    			System.out.println(MyTime.get_stdtimeformat() + " : lack file: " + b);
//	    			count++;
//	    		}
//	    	}
//	    	System.out.println(MyTime.get_stdtimeformat() + " : " + count + " files missing.");
//	    	
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
////			MyLog.debug(e);
//		}
		
		
		if (args.length < 1) {
			throw new NullPointerException(
					"enter the program cfgfile");
		}
		
		System.out.println(MyTime.get_stdtimeformat() + " : -----------------program begin-----------------");
		System.err.println(MyTime.get_stdtimeformat() + " : -----------------program begin-----------------");
		
		
		String program_cfgfile = args[0];
    	
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
    	
    	
    	int file_count = 0;
//    	Client.wsdemo_downloadzipfiles("/tmp", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	try {
    		file_count = Check.wsdemo_downloadzipfiles(outdir, new URL(warn_url), 720);
    		
    		System.err.println(file_count + " files downloaded");
//    		if(file_count == 0) {
//    			//输出1，表示无更新数据
//    			MyLog.info("No files downloaded.");
//    		} else {
//    			//输出0，表示有正常数据下载
//    			MyLog.info(" " + file_count + " files downloaded.");
//    		}
    	} catch (Exception e) {
//    		MyLog.debug(e);
    		e.printStackTrace();
    	}
    	
	}

}
