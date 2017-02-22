 package com.pmsc.warning.client;

import java.net.URL;

import cn.pmsc.lb.*;


public class GetZIP {
	
    /**
     * 主方法main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	
    	
//    	Client.wsdemo_downloadzipfiles("F:\\temp\\data", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	
    	if (args.length < 2) {
			throw new NullPointerException(
					"enter the program cfgfile and the log4j cfgfile");
		}
    	
    	String program_cfgfile = args[0];
    	String log4j_cfgfile = args[1];
    	
    	MyProperties program_config = null;
    	String outdir = null;
//    	String warn_url = null;
    	
    	try {
    		program_config = new MyProperties(program_cfgfile);
    		outdir = program_config.getMyProperties("outdir");
//    		warn_url = program_config.getMyProperties("warn_url");
    	} catch(Exception e) {
    		System.out.println("error happened while reading program cfgfile: " + program_cfgfile);
    		throw new RuntimeException(e); 
    	}
    	
    	try {
//    		System.setProperty("warncap_logpath", args[2]); //设置日志输出路径
//    		System.out.println(System.getProperty("warncap_logpath").toString());
    		MyLog.load_log4jcfg(log4j_cfgfile);
//    		MyLog.userdefine_prop("log4j.appender.logFile.File", args[2]);
//    		MyLog.userdefine_prop("appender.E.fileName", args[2]);
    		MyLog.flush();
    		System.out.println("log4j config file loaded:" + log4j_cfgfile);
    	} catch(Exception e) {
    		System.out.println("error happened while reading log4j cfgfile: " + log4j_cfgfile);
    	}
    	
    	
    	int file_count = 0;
//    	Client.wsdemo_downloadzipfiles("/tmp", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	try {
    		file_count = Client.wsdemo_downloadzipfiles(outdir);
    		
    		
    		if(file_count == 0) {
    			//输出1，表示无更新数据
    			MyLog.info("No files downloaded.");
    		} else {
    			//输出0，表示有正常数据下载
    			MyLog.info(" " + file_count + " files downloaded.");
    		}
    	} catch (Exception e) {
    		MyLog.debug(e);
    	}
    	
    }  
  
}  