package ftpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

public class FTPListThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FTPListThread.class);
    
    private FtpInfo ftpinfo;	//FTP连接信息
    
    private List<FTPFile> ftpfilelist; //FTP文件清单
    
	
	public FTPListThread(FtpInfo ftpinfo) {
		this.ftpinfo = ftpinfo;
		
		this.ftpfilelist = new ArrayList<FTPFile>();
	}
	
	
	public List<FTPFile> getFtpfilelist() {
		return ftpfilelist;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		FTPUtils ftpclient = new FTPUtils();
		
		try {
			//连接FTP
			ftpclient.connect(ftpinfo.getHost(), ftpinfo.getPort(), ftpinfo.getUser(), 
					ftpinfo.getPassword(), ftpinfo.isTextMode(), ftpinfo.isPassiveMode());
//			ftpclient.connect(host, port, user, password, isTextMode, isPassiveMode);
			
			//切换当前目录到指定路径
			if(!ftpclient.setWorkingDirectory(ftpinfo.getFtppath())) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				
				logger.error("Invalid ftp path: " + ftpinfo.getFtppath()
				+ " of host:" + ftpinfo.getHost() + " user:" + ftpinfo.getUser()
				+ " password:" + ftpinfo.getPassword(), (Object)null); //$NON-NLS-1$
				
				ftpclient.disconnect();
				return;
			}
			
			//取得FTP当前路径下的文件清单
			ftpfilelist = ftpclient.listFiles(ftpinfo.getFtp_wildcard());
			
			if (logger.isDebugEnabled()) {
				logger.debug("listFiles(String) - {}", ftpfilelist.size() 
						+ " files founded in " + ftpinfo.getFtppath()); //$NON-NLS-1$ //$NON-NLS-2$
			}
			//暂时断开连接
			ftpclient.disconnect();
		} catch (IOException e) {
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
			logger.error("run() - e=" + e, e); //$NON-NLS-1$
		}
	}
}
