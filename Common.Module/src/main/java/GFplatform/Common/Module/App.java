package GFplatform.Common.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ftpClient.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class App {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	
	
	public static void main(String[] args) {
		
		if(args.length >= 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("program begins at " + new Date().toString()); //$NON-NLS-1$
			}
			
			//读取配置文件
			List<String> content;
			String Parent_Path = null;
			
			try {
				content = LocalFileUtils.readfile(args[0]);
				Parent_Path = LocalFileUtils.get_ParentDir(args[0]);
			} catch (IOException e) {
				logger.error("main(String[]) - config file " + args[0] 
						+ " read error, " 
						+ System.lineSeparator() 
						+ Stat.get_status_code(2) 
						+ "e=" + e, e); //$NON-NLS-1$
				return;
			}
			
			//按行分解配置文件，逐行加入线程池
			ExecutorService exec = Executors.newCachedThreadPool();
			
			FtpInfo ftpinfo;
			
			int i = 0;
			for(String line : content) {
				
				if(line.trim().startsWith("#") || line.trim().isEmpty()) {
					continue; //认为是空行或注释信息，直接跳过下面的语句不予执行
				}
				
				try {
					ftpinfo = new FtpInfo(line.trim(), i, Parent_Path);

//					if (logger.isInfoEnabled()) {
//						logger.info("Formatted paras of ftp Thread: " + ftpinfo.toString()); //$NON-NLS-1$
//					}
					
					if (logger.isDebugEnabled()) {
						logger.debug("Formatted paras of ftp Thread: " + ftpinfo.toString()); //$NON-NLS-1$
					}
					
//					FTPCompleteThread ct = new FTPCompleteThread(ftpinfo);
					
					if(ftpinfo.getType().toLowerCase().contains("download")) {
						exec.submit(new FileGetThread(ftpinfo));
					} else if(ftpinfo.getType().toLowerCase().contains("upload")) {
						FtpLog.init_log(); //读取FTP上传日志
						exec.submit(new FilePutThread(ftpinfo));
					} else {
						Stat.add_invalidArg(); //统计不正确参数行
						logger.error("unknown transfer type: "
								+ ftpinfo.getType() + ", Paras: " 
								+ ftpinfo.toString(), (Object)null);
					}
				} catch (IllegalArgumentException e) {
					Stat.add_invalidArg(); //统计不正确参数行
					logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
				} catch (NullPointerException e) {
					//认为是无效行
					if (logger.isDebugEnabled()) {
						logger.debug("main(String[]) - e={}", e); //$NON-NLS-1$
					}
				}
			}
			
			exec.shutdown();
			try {
				//设置等待超时时间为1小时
				if(exec.awaitTermination(1, TimeUnit.HOURS)) {
					if (logger.isInfoEnabled()) {
						logger.info("main(String[]) - all threads terminated."); //$NON-NLS-1$
					}
				} else {
					if (logger.isInfoEnabled()) {
						logger.info("main(String[]) - some threads timeout, forced terminated."); //$NON-NLS-1$
					}
				}
			} catch (InterruptedException e) {
				logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
			}
			
			//打印程序返回状态，返回给总线
			if (logger.isDebugEnabled()) {
				logger.debug(Stat.get_status()); //$NON-NLS-1$
			}
			
		} else {
			logger.error("Invalid paras, please input the path of config file" 
					+ System.lineSeparator() + Stat.get_status_code(2), (Object) null); //$NON-NLS-1$
		}
	}
}
