package com.pmsc.warning.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.pmsc.warning.client.WarnCap;

import cn.pmsc.lb.MyLog;
import cn.pmsc.lb.MyProperties;

public class WriteToDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 5) {
			throw new NullPointerException(
					"enter the program cfgfile, the log4j cfgfile, the logfile, business id and process id");
		}
    	
    	String program_cfgfile = args[0];
    	String log4j_cfgfile = args[1];
    	
    	MyProperties program_config = null;
    	String outdir = null;
    	String warn_url = null;
    	String dest_url = null;
    	
    	try {
    		program_config = new MyProperties(program_cfgfile);
    		outdir = program_config.getMyProperties("outdir"); //文件下载路径
    		warn_url = program_config.getMyProperties("warn_url"); //预警数据源
    		dest_url = program_config.getMyProperties("dest_url"); //写入数据库接口
    	} catch(Exception e) {
    		System.out.println("error happened while reading program cfgfile: " + program_cfgfile);
    		throw new RuntimeException(e); 
    	}
    	
    	MyLog.set_business_id(args[3]);
		MyLog.set_process_id(args[4]);
		
    	try {
    		MyLog.load_log4jcfg(log4j_cfgfile);
    		MyLog.userdefine_prop("log4j.appender.logFile.File", args[2]);
    		MyLog.flush();
    	} catch(Exception e) {
    		System.out.println("error happened while reading log4j cfgfile: " + log4j_cfgfile);
    	}
    	
    	try {
			List<WarnCap> wc_list = Client.wsdemo_getwarncaplist(new URL(warn_url), 360);
			
			Client.wsdemo_warncapToDB(wc_list, new URL(dest_url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
