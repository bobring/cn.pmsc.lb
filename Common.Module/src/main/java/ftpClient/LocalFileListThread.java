package ftpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class LocalFileListThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(LocalFileListThread.class);
	
	private List<File> localfilelist;
	
	private FtpInfo ftpinfo;
	/**
	 * @param localpath
	 */
	public LocalFileListThread(FtpInfo ftpinfo) {
		this.ftpinfo = ftpinfo;
	}


//	public String getLocalpath() {
//		return ftpinfo.getLocalpath();
//	}
	public List<File> getLocalfilelist() {
		return localfilelist;
	}

	public void run() {
		localfilelist = LocalFileUtils.listfiles(ftpinfo.getLocalpath(), ftpinfo.getLocal_wildcard());
		
		if (logger.isDebugEnabled()) {
			logger.debug("listfiles() - {}", localfilelist.size() 
					+ " files founded in " + ftpinfo.getLocalpath()); //$NON-NLS-1$
		}
	}
}
