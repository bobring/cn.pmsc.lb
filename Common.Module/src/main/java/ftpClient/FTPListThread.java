package ftpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

import ftpClient.FTPUtils;

public class FTPListThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FTPListThread.class);

	private String host;			//FTP站点名
    private int port = 21;			//FTP端口号，默认21
    private String user;			//FTP用户名
    private String password;		//FTP密码
    private boolean isTextMode;		//文件传输模式，true为文本模式，false为二进制模式
    private boolean isPassiveMode;	//FTP连接模式，true为被动模式，false为主动模式
    private String ftppath;			//FTP路径
//    private String localpath;		//本地路径
    
    private List<FTPFile> ftpfilelist; //FTP文件清单
    
	/**
	 * @param period_seconds
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param isTextMode
	 * @param isPassiveMode
	 * @param ftppath
	 * @param localpath
	 */
	public FTPListThread(String host, int port, String user, String password, boolean isTextMode,
			boolean isPassiveMode, String ftppath) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.isTextMode = isTextMode;
		this.isPassiveMode = isPassiveMode;
		this.ftppath = ftppath;
//		this.localpath = localpath;
		this.ftpfilelist = new ArrayList<FTPFile>();
	}
	
	
	public List<FTPFile> getFtpfilelist() {
		return ftpfilelist;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			//连接FTP
			FTPUtils.connect(host, port, user, password, isTextMode, isPassiveMode);
			
			if(!FTPUtils.setWorkingDirectory(ftppath)) {
				logger.error("Invalid ftp path: " + ftppath, (Object)null); //$NON-NLS-1$
				FTPUtils.disconnect();
				return;
			}
			
			//取得FTP指定路径下的文件清单
			ftpfilelist = FTPUtils.listFiles(ftppath);
			//暂时断开连接
			FTPUtils.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
