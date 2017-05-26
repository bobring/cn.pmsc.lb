package ftpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.pmsc.lb.MyString;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FTPUploadThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FTPUploadThread.class);

	private FtpInfo ftpinfo;	//FTP连接信息
    private List<File> localfilelist;	//待上传的本地文件清单
    private Map<String,String> fileMap;	//文件重命名清单
    
    private int speed = 500*1000;		//预订平均传输速率，字节每秒
    private int trans_period;			//预计传输耗费总时长，秒
    
	public int getTrans_period() {
		return trans_period;
	}
	
	private int calculate() {
		long count = 0;
		int seconds;
		
		for(File file : localfilelist) {
			count += file.length();
		}
		
		seconds = (int) (count/speed);
		
		if(seconds < 10) {
			seconds = 10;
		}
		
		return seconds;
	}
	
	
	public FTPUploadThread(FtpInfo ftpinfo, List<File> localfilelist) {
		this.ftpinfo = ftpinfo;
		
		this.localfilelist = localfilelist;
		this.fileMap = null;
		
		this.trans_period = calculate();
	}
	
	
	
	public FTPUploadThread(FtpInfo ftpinfo, List<File> localfilelist, 
			Map<String,String> fileMap) {
		this.ftpinfo = ftpinfo;
		
		this.localfilelist = localfilelist;
		this.fileMap = fileMap;
		
		this.trans_period = calculate();
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		FTPUtils ftpclient = new FTPUtils();
		
		try {
			//连接FTP
			ftpclient.connect(ftpinfo.getHost(), ftpinfo.getPort(), ftpinfo.getUser(), 
					ftpinfo.getPassword(), ftpinfo.isTextMode(), ftpinfo.isPassiveMode());
			
			if (!ftpclient.setWorkingDirectory(ftpinfo.getFtppath())) {
				if(!ftpclient.makeDirs(ftpinfo.getFtppath()) 
						|| !ftpclient.setWorkingDirectory(ftpinfo.getFtppath())) {
					// 文件夹不存在，尝试自行创建，若创建失败，则报错
					Stat.update_ThreadStatus(ftpinfo.getPid(), false); // 记录本线程状态为异常

					logger.error("Invalid ftp path: " + ftpinfo.getFtppath() 
							+ " of host:" + ftpinfo.getHost() + " user:" 
							+ ftpinfo.getUser() + " password:" 
							+ ftpinfo.getPassword(), (Object) null); //$NON-NLS-2$

					ftpclient.disconnect();
					return;
				}
			}
			
			//逐个上传文件
			for(File f: localfilelist) {
				try {
					if (fileMap != null && fileMap.containsKey(f.getName())) {
						String newname = fileMap.get(f.getName());
						ftpclient.upload(newname, f);
						
						Stat.add_filesSuccess(); //统计传输成功的文件数
						
						if (logger.isInfoEnabled()) {
							logger.info("uploading file: " + f.getAbsolutePath() 
							+ " -> " + ftpinfo.getHost() + ":" 
							+ MyString.filepath(ftpinfo.getFtppath(), newname)); //$NON-NLS-1$
						}
						//设置目标文件的修改时间与源文件一致
						if(!ftpclient.set_ModifyTime(newname, f.lastModified())) {
							logger.warn("failed to set modify time "
									+ "of file: " + ftpinfo.getHost() + ":" 
									+ MyString.filepath(ftpinfo.getFtppath(), newname), 
									(Object) null); //$NON-NLS-1$
						}
					} else if(fileMap != null) {
						//文件名被脚本过滤，认为是非业务所需文件
						logger.warn("file: " + f.getAbsolutePath() + 
							" has no target name, will not be uploaded.", (Object) null); //$NON-NLS-1$
					} else {
						ftpclient.upload(f.getName(), f);
						
						Stat.add_filesSuccess(); //统计传输成功的文件数
						
						if (logger.isInfoEnabled()) {
							logger.info("uploading file: " + f.getAbsolutePath() 
							+ " -> " + ftpinfo.getHost() + ":" 
							+ MyString.filepath(ftpinfo.getFtppath(), f.getName())); //$NON-NLS-1$
						}
						
						//设置目标文件的修改时间与源文件一致
						if(!ftpclient.set_ModifyTime(f.getName(), f.lastModified())) {
							logger.warn("failed to set modify time "
									+ "of file: " + ftpinfo.getHost() + ":" 
									+ MyString.filepath(ftpinfo.getFtppath(), f.getName()), 
									(Object) null); //$NON-NLS-1$
						}
					}
				} catch(IOException e) {
					Stat.add_filesFailed(); //记录传输失败文件数
					logger.error("file upload error - e=" + e, e); //$NON-NLS-1$
				}
			}
			//断开连接
			ftpclient.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
			logger.error("FTP connection error - e=" + e, e); //$NON-NLS-1$
		} catch (Exception e) {
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
			// TODO Auto-generated catch block
			logger.error("FTP upload error - e=" + e, e); //$NON-NLS-1$
		}
	}

}
