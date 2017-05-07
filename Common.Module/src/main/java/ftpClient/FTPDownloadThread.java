package ftpClient;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.pmsc.lb.MyString;

public class FTPDownloadThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FTPDownloadThread.class);

	private String host;				//FTP站点名
    private int port = 21;				//FTP端口号，默认21
    private String user;				//FTP用户名
    private String password;			//FTP密码
    private boolean isTextMode;			//文件传输模式，true为文本模式，false为二进制模式
    private boolean isPassiveMode;		//FTP连接模式，true为被动模式，false为主动模式
    private String ftppath;				//FTP路径
    private List<FTPFile> filelist;		//待下载的FTP文件清单
    private String localpath;			//下载的本地存储路径
    private Map<String,String> fileMap;	//文件重命名清单
    
    private int speed = 500*1000;		//预订平均传输速率，字节每秒
    private int trans_period;			//预计传输耗费总时长，秒
    
	public int getTrans_period() {
		return trans_period;
	}
	
	private int calculate() {
		long count = 0;
		int seconds;
		
		for(FTPFile file : filelist) {
			count += file.getSize();
		}
		
		seconds = (int) (count/speed);
		
		if(seconds < 10) {
			seconds = 10;
		}
		
		return seconds;
	}
	/**
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param isTextMode
	 * @param isPassiveMode
	 * @param ftppath
	 * @param localfilelist
	 */
	public FTPDownloadThread(String host, int port, String user, 
			String password, boolean isTextMode, boolean isPassiveMode, 
			String ftppath, List<FTPFile> filelist, String localpath) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.isTextMode = isTextMode;
		this.isPassiveMode = isPassiveMode;
		this.ftppath = ftppath;
		this.filelist = filelist;
		this.localpath = localpath;
		this.fileMap = null;
		
		this.trans_period = calculate();
//		this.fileMap = new HashMap<String, String>();
//		
//		for (File file : localfilelist) {
//			fileMap.put(file.getName(), file.getName());
//		}
	}
	
	
	
	/**
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param isTextMode
	 * @param isPassiveMode
	 * @param ftppath
	 * @param filelist
	 * @param fileMap
	 */
	public FTPDownloadThread(String host, int port, String user, 
			String password, boolean isTextMode, boolean isPassiveMode, 
			String ftppath, List<FTPFile> filelist, 
			String localpath, Map<String,String> fileMap) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.isTextMode = isTextMode;
		this.isPassiveMode = isPassiveMode;
		this.ftppath = ftppath;
		this.filelist = filelist;
		this.localpath = localpath;
		this.fileMap = fileMap;
		
		this.trans_period = calculate();
	}
	
	public void run() {
		// TODO Auto-generated method stub
//		Runtime runtime = Runtime.getRuntime();
//		String cmd = "cd " + localpath;
		
		try {
			//连接FTP
			FTPUtils.connect(host, port, user, password, isTextMode, isPassiveMode);
			
			if(!FTPUtils.setWorkingDirectory(ftppath)) {
				logger.error("Invalid ftp path: " + ftppath, (Object)null); //$NON-NLS-1$
				FTPUtils.disconnect();
				return;
			}
			
			//切换本地目录
//			runtime.exec(cmd);
			//逐个上传文件
			for(FTPFile f: filelist) {
				try {
					if (fileMap != null && fileMap.containsKey(f.getName())) {
						String newname = MyString.filepath(localpath, fileMap.get(f.getName()));
						FTPUtils.download(f.getName(), new File(newname));
						
						if (logger.isInfoEnabled()) {
							logger.info("downloading file: " + f.getName() 
							+ " -> " + newname); //$NON-NLS-1$
						}
					} else if(fileMap != null) {
						logger.warn("file: " + f.getName() + 
							" has no target name, will not be downloaded."); //$NON-NLS-1$
					} else {
						FTPUtils.download(f.getName(), 
								new File(MyString.filepath(localpath, f.getName())));

						if (logger.isInfoEnabled()) {
							logger.info("downloading file: " + f.getName()); //$NON-NLS-1$
						}
					}
				} catch(Exception e) {
					logger.error("file download error - e=" + e, e); //$NON-NLS-1$
				}
			}
			//断开连接
			FTPUtils.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("FTP connection error - e=" + e, e); //$NON-NLS-1$
		}
	}

}
