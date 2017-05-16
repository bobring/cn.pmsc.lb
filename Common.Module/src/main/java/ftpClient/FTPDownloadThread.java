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

	private FtpInfo ftpinfo;	//FTP连接信息
    private List<FTPFile> filelist;		//待下载的FTP文件清单
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
	
	public FTPDownloadThread(FtpInfo ftpinfo, List<FTPFile> filelist) {
		this.ftpinfo = ftpinfo;
		
		this.filelist = filelist;
		this.fileMap = null;
		
		this.trans_period = calculate();
	}
	
	
	
	public FTPDownloadThread(FtpInfo ftpinfo, List<FTPFile> filelist, 
			Map<String,String> fileMap) {
		this.ftpinfo = ftpinfo;
		
		this.filelist = filelist;
		this.fileMap = fileMap;
		
		this.trans_period = calculate();
	}
	
	
	
	
	public void run() {
		// TODO Auto-generated method stub
//		Runtime runtime = Runtime.getRuntime();
//		String cmd = "cd " + localpath;
		FTPUtils ftpclient = new FTPUtils();
		
		try {
			//连接FTP
			ftpclient.connect(ftpinfo.getHost(), ftpinfo.getPort(), ftpinfo.getUser(), 
					ftpinfo.getPassword(), ftpinfo.isTextMode(), ftpinfo.isPassiveMode());
			
			if(!ftpclient.setWorkingDirectory(ftpinfo.getFtppath())) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				
				logger.error("Invalid ftp path: " + ftpinfo.getFtppath()
				+ " of host:" + ftpinfo.getHost() + " user:" + ftpinfo.getUser()
				+ " password:" + ftpinfo.getPassword(), (Object)null); //$NON-NLS-1$
				
				ftpclient.disconnect();
				return;
			}
			
			File localfile;
			//切换本地目录
//			runtime.exec(cmd);
			//逐个上传文件
			for(FTPFile f: filelist) {
				try {
					if (fileMap != null && fileMap.containsKey(f.getName())) {
						String newname = MyString.filepath(ftpinfo.getLocalpath(), fileMap.get(f.getName()));
						localfile = new File(newname);
						ftpclient.download(f.getName(), localfile);
						
						Stat.add_filesSuccess(); //统计传输成功的文件数
						
						if (logger.isInfoEnabled()) {
							logger.info("downloading file: " + ftpinfo.getHost() 
							+ ":" + MyString.filepath(ftpinfo.getFtppath(), f.getName()) 
							+ " -> " + localfile.getAbsolutePath()); //$NON-NLS-1$
						}
						//设置目标文件的修改时间与源文件一致
						if(!localfile.setLastModified(f.getTimestamp().getTimeInMillis())) {
							logger.warn("failed to set modify time "
									+ "of file: " + localfile.getAbsolutePath(), (Object)null); //$NON-NLS-1$
						}
					} else if(fileMap != null) {
						//文件名被脚本过滤，认为是非业务所需文件
						logger.warn("file: " + f.getName() + 
							" has no target name, will not be downloaded."); //$NON-NLS-1$
					} else {
						localfile = new File(MyString.filepath(ftpinfo.getLocalpath(), f.getName()));
						ftpclient.download(f.getName(), localfile);
						
						Stat.add_filesSuccess(); //统计传输成功的文件数
						
						if (logger.isInfoEnabled()) {
							logger.info("downloading file: " + ftpinfo.getHost() 
							+ ":" + MyString.filepath(ftpinfo.getFtppath(), f.getName()) 
							+ " -> " + localfile.getAbsolutePath()); //$NON-NLS-1$
						}
						//设置目标文件的修改时间与源文件一致
						if(!localfile.setLastModified(f.getTimestamp().getTimeInMillis())) {
							logger.warn("failed to set modify time of file: " + localfile.getAbsolutePath(), (Object)null); //$NON-NLS-1$
						}
					}
				} catch(IOException e) {
					Stat.add_filesFailed(); //记录传输失败文件数
					logger.error("file download error - e=" + e, e); //$NON-NLS-1$
				}
			}
			//断开连接
			ftpclient.disconnect();
		} catch (IOException e) {
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
			// TODO Auto-generated catch block
			logger.error("FTP connection error - e=" + e, e); //$NON-NLS-1$
		}
	}

}
