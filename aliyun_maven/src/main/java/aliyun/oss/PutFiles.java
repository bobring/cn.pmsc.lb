 package aliyun.oss;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.pmsc.lb.*;


public class PutFiles {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PutFiles.class);
	
	/**
	 * 按文件的完全路径,提取出文件名,并存放在Map中作为返回值
	 * @return Map<String, String> filelist
	 * @return Map<Local file, Remote file> filelist
	 */
	private static Map<String, String> make_list(String[] args) {
		Map<String, String> filemap = new HashMap<String, String>();
		
		File file = null;
		for(int i = 1; i < args.length; i++) {
			file = new File(args[i]);
			if(file.isFile()) {
				filemap.put(args[i], file.getName());
			}
		}
		return filemap;
	}
    /**
     * 主方法main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	
    	String ACCESS_ID = null;
    	String ACCESS_KEY = null;
    	String OSS_ENDPOINT = null;
    	String BUCKET_NAME = null;
    	
    	if (args.length >= 2) {
    		try {
    			MyProperties program_config = new MyProperties(args[0]);
    			
    	    	ACCESS_ID = program_config.getMyProperties("ACCESS_ID");
    	    	ACCESS_KEY = program_config.getMyProperties("ACCESS_KEY");
    	    	OSS_ENDPOINT = program_config.getMyProperties("OSS_ENDPOINT");
    	    	BUCKET_NAME = program_config.getMyProperties("BUCKET_NAME");
    	    } catch(Exception e) {
				logger.error("main(String[]) - error happened while reading program cfgfile: " + args[0] + e, e); //$NON-NLS-1$
    	    	throw new RuntimeException(e); 
    	    }
		} else {
			throw new NullPointerException(
					"program paras formats like:" + System.lineSeparator()
					+ "java -jar jarname configfile Filenames" + System.lineSeparator());
		}
    	
    	TestOSS.setACCESS_ID(ACCESS_ID);
    	TestOSS.setACCESS_KEY(ACCESS_KEY);
    	TestOSS.setOSS_ENDPOINT(OSS_ENDPOINT);
    	TestOSS.setBUCKET_NAME(BUCKET_NAME);
    	
    	int num = TestOSS.upload(make_list(args));
    	
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - file sum=" + num); //$NON-NLS-1$
		}
    }  
  
}  