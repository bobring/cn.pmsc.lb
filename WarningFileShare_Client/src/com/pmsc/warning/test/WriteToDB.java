package com.pmsc.warning.test;

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
    	
    	
	}

}
