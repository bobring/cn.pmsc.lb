 package com.pmsc.warning.client;

import org.apache.log4j.Logger;


/**
 * @author BobRing
 * 用于在核心业务平台上,下载市县级森林火险预警,因而只能使用自定义的MyLog,不能使用log4j
 */
public class GetFireWarning_City {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GetFireWarning_City.class);
	
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
    	
    	if (args.length < 1) {
    		logger.error("main(String[]) - invalid program paras, please enter the download path "); //$NON-NLS-1$
			throw new NullPointerException(
					"please enter the download path");
		}
    	
    	String outdir = args[0];
    	
//    	MyProperties program_config = null;
//    	String outdir = null;
//    	
//    	try {
//    		program_config = new MyProperties(program_cfgfile);
//    		outdir = program_config.getMyProperties("outdir");
//    	} catch(Exception e) {
//			logger.error("main(String[]) - error happened while reading program cfgfile: " + program_cfgfile, e); //$NON-NLS-1$
//
////    		System.out.println("error happened while reading program cfgfile: " + program_cfgfile);
//    		throw new RuntimeException(e); 
//    	}
    	
    	int file_count = 0;
//    	Client.wsdemo_downloadzipfiles("/tmp", new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl"));
    	try {
    		file_count = Client.wsdemo_getfirewarning_city(outdir);
    		
    		
    		if(file_count == 0) {
    			//输出1，表示无更新数据

				if (logger.isInfoEnabled()) {
					logger.info("main(String[]) - No files downloaded."); //$NON-NLS-1$
				}
    		} else {
    			//输出0，表示有正常数据下载
    			if (logger.isInfoEnabled()) {
					logger.info("main(String[]) - " + file_count + " files downloaded."); //$NON-NLS-1$
				}
    		}
    	} catch (Exception e) {
			logger.error("main(String[]) - ", e); //$NON-NLS-1$
    	}
    	
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end"); //$NON-NLS-1$
		}
    }  
  
}  