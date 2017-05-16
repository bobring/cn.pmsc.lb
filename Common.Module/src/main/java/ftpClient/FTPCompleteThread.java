package ftpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.TimeoutController;
import org.apache.commons.httpclient.util.TimeoutController.TimeoutException;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import ftpClient.ReduceList.NameFilter;

public class FTPCompleteThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FTPCompleteThread.class);
	
	private FtpInfo ftpinfo;
	
	/**
	 * @param args
	 */
	public FTPCompleteThread(FtpInfo ftpinfo) {
		super();
		this.ftpinfo = ftpinfo;
	}



	public void run() {
		List<FTPFile> ftpfiles = null;
		List<File> localfiles = null;
		
		ReduceList diff;
		ReduceList.NameFilter namefilter;
		Map<String, String> fileMap;
		
		Stat.update_ThreadStatus(ftpinfo.getPid(), true); //初始化线程状态为正常
		
		//获取已存在的FTP文件清单和本地文件清单
		try {
			FTPListThread ftplistT = new FTPListThread(ftpinfo);
			//超时设置为2分钟，即120秒
			TimeoutController.execute(ftplistT, 120000);
			ftpfiles = ftplistT.getFtpfilelist();
			
			LocalFileListThread locallistT = new LocalFileListThread(ftpinfo);
			//超时设置为2分钟，即120秒
			TimeoutController.execute(locallistT, 120000);
			localfiles = locallistT.getLocalfilelist();
		} catch(TimeoutException e) {
			logger.error("run() - e=" + e, e); //$NON-NLS-1$
			
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
			
			if(ftpfiles == null) {
				ftpfiles = new ArrayList<FTPFile>();
			}
			if(localfiles == null) {
				localfiles = new ArrayList<File>();
			}
		}
		
		//计算待上传或下载的文件清单
		
		////利用时间区间精简文件清单，从当前时间倒退的秒数小于等于0，认为此项无效
		if(ftpinfo.getSeconds() > 0) {
			ReduceList.reduce_ListByTime(localfiles, ftpinfo.getSeconds());
			ReduceList.reduce_ListByTime(ftpfiles, ftpinfo.getSeconds());
		}
		
		
		////将本地文件和FTP文件清单进行对比，清理已存在项目
		diff = new ReduceList();
		
		//下载业务且带重命名脚本
		if(ftpinfo.getType().equalsIgnoreCase("-download") && ftpinfo.getShellfile() != null) {
			namefilter = diff.new NameFilter(ftpinfo.getShellfile(), 
					ReduceList.get_NameList(ftpfiles));
			try {
				//超时设置为2分钟，即120秒
				TimeoutController.execute(namefilter, 120000);
			} catch(TimeoutException e) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				logger.error("run() - e=" + e, e); //$NON-NLS-1$
				return;
			}
			fileMap = namefilter.getFileNameMap();
			ReduceList.get_TransList(localfiles, ftpfiles, fileMap);
		//下载业务
		} else if(ftpinfo.getType().equalsIgnoreCase("-download")) {
			fileMap = null;
			ReduceList.get_TransList(localfiles, ftpfiles, fileMap);
		//上传业务且带重命名脚本
		} else if(ftpinfo.getShellfile() != null) {
			namefilter = diff.new NameFilter(ftpinfo.getShellfile(), ReduceList.get_NameList(localfiles));
			try {
				//超时设置为2分钟，即120秒
				TimeoutController.execute(namefilter, 120000);
			} catch(TimeoutException e) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				logger.error("run() - e=" + e, e); //$NON-NLS-1$
				return;
			}
			fileMap = namefilter.getFileNameMap();
			ReduceList.get_TransList(ftpfiles, localfiles, fileMap);
		//上传业务
		} else {
			fileMap = null;
			ReduceList.get_TransList(ftpfiles, localfiles, fileMap);
		}
		
		
		//执行FTP上传或下载任务
		if(ftpinfo.getType().equalsIgnoreCase("-download") && !ftpfiles.isEmpty()) {
			FTPDownloadThread downloadT = new FTPDownloadThread(
					ftpinfo, ftpfiles, fileMap);
			try {
				//超时设置为毫秒
				TimeoutController.execute(downloadT, downloadT.getTrans_period() * 1000);
			} catch (TimeoutException e) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				logger.error("run() - e=" + e, e); //$NON-NLS-1$
				return;
			}
			
		} else if(ftpinfo.getType().equalsIgnoreCase("-upload") && !localfiles.isEmpty()) {
			FTPUploadThread uploadT = new FTPUploadThread(ftpinfo, 
					localfiles, fileMap);
			try {
				//超时设置为毫秒
				TimeoutController.execute(uploadT, uploadT.getTrans_period() * 1000);
			} catch (TimeoutException e) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				logger.error("run() - e=" + e, e); //$NON-NLS-1$
				return;
			}
		}
	}
}
