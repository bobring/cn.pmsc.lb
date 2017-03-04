 package com.pmsc.warning.client;

import org.apache.log4j.Logger;

import com.pmsc.warning.XmlBean.WarnXML;
import com.pmsc.warning.XmlBean.XMLUtil;

import java.net.MalformedURLException;
import java.net.URL;

import cn.pmsc.lb.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class GetZIP {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GetZIP.class);
	
	public static void DownloadZIPs(String outdir) {
		int file_count = 0;
//    	Client.wsdemo_downloadzipfiles("/tmp", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	try {
    		file_count = Client.wsdemo_downloadzipfiles(outdir);
    		
    		
    		if(file_count == 0) {
    			//输出1，表示无更新数据
//    			MyLog.info("No files downloaded.");

				if (logger.isInfoEnabled()) {
					logger.info("DownloadZIPs(String) - No files downloaded."); //$NON-NLS-1$
				}
    		} else {
    			//输出0，表示有正常数据下载
//    			MyLog.info(" " + file_count + " files downloaded.");

				if (logger.isInfoEnabled()) {
					logger.info("DownloadZIPs(String) - file_count=" + file_count); //$NON-NLS-1$
				}
    		}
    	} catch (Exception e) {
//    		MyLog.debug(e);
			logger.error("DownloadZIPs(String) - e=" + e, e); //$NON-NLS-1$
    	}
	}
	
	
	public static void XMLToDB(String[] args) {
		WarnXML wx = null;
		JSONArray arr = new JSONArray();
		
		try {
			for(int i = 2; i < args.length; i++) {
				wx = (WarnXML) XMLUtil.XmlFileToObject(WarnXML.class, args[i]);
				if(wx != null) {
					arr.add((JSONObject) Client.warnxmltoJSON(wx));
				} else {
					logger.error("XMLToDB(String[]) - XML file format error: " + args[i]); //$NON-NLS-1$
				}
			}
			MySimpleJson.PostJSON(new URL(args[1]), arr);
		} catch (MalformedURLException e) {
			logger.error("XMLToDB(String[]) - ", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("XMLToDB(String[]) - ", e);
			e.printStackTrace();
		}
		
	}
	
    /**
     * 主方法main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}
    	
    	
//    	Client.wsdemo_downloadzipfiles("F:\\temp\\data", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	
    	if (args.length == 2 && "-getzips".equalsIgnoreCase(args[0])) {
    		try {
    			MyProperties program_config = new MyProperties(args[1]);
    	    	String outdir = program_config.getMyProperties("outdir");
    	    	DownloadZIPs(outdir);
    	    } catch(Exception e) {
				logger.error("main(String[]) - ", e); //$NON-NLS-1$

    	    	System.out.println("error happened while reading program cfgfile: " + args[1]);
    	    	
    	    	throw new RuntimeException(e); 
    	    }
		} else if(args.length >= 3 && "-xmlToDB".equalsIgnoreCase(args[0])) {
			XMLToDB(args);
		} else {
			logger.error("main(String[]) - program paras formats error:" + args.toString());
			throw new NullPointerException(
					"program paras formats like:" + System.lineSeparator()
					+ "1: -getzips configfile" + System.lineSeparator()
					+ "2: -xmlToDB Dest_URL Filenames" + System.lineSeparator());
		}
    	
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
    }  
  
}  