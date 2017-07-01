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

public class FilePutThread extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FilePutThread.class);
	
	private FtpInfo ftpinfo;
	
	/**
	 * @param args
	 */
	public FilePutThread(FtpInfo ftpinfo) {
		super();
		this.ftpinfo = ftpinfo;
	}



	public void run() {
		List<FTPFile> ftpfiles = null;
		List<File> localfiles = null;
		
		ReduceList diff;
		ReduceList.NameFilter namefilter;
		Map<String, String> fileMap;
		
//		if(!ftpinfo.getType().toLowerCase().contains("upload")) {
//			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
//			logger.error("unknown transfer type: " + ftpinfo.getType(), (Object)null);
//			return;
//		}
		
		Stat.update_ThreadStatus(ftpinfo.getPid(), true); //初始化线程状态为正常
		
		//获取已存在的FTP文件清单
		try {
			FTPListThread ftplistT = new FTPListThread(ftpinfo);
			//超时设置为2分钟，即120秒
			TimeoutController.execute(ftplistT, 120000);
			ftpfiles = ftplistT.getFtpfilelist();
		} catch(TimeoutException e) {
			logger.error("run() - get file info from "
					+ ftpinfo.getHost() + " time out, e= " + e, e); //$NON-NLS-1$
			
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
			
			if(ftpfiles == null) {
				ftpfiles = new ArrayList<FTPFile>();
			}
		}
		//获取已存在的本地文件清单
		try {
			LocalFileListThread locallistT = new LocalFileListThread(ftpinfo);
			//超时设置为2分钟，即120秒
			TimeoutController.execute(locallistT, 120000);
			localfiles = locallistT.getLocalfilelist();
		} catch(TimeoutException e) {
			logger.error("run() - get file info from "
					+ ftpinfo.getLocalpath() + " time out, e= " + e, e); //$NON-NLS-1$
			
			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
		}
		
		//无待传文件，直接返回
		if(localfiles == null || localfiles.isEmpty()) {
			return;
		}
		
		
		//计算待上传的文件清单
		
		////利用时间区间精简文件清单，从当前时间倒退的秒数小于等于0，认为此项无效
		if(ftpinfo.getSeconds() > 0) {
			ReduceList.reduce_ListByTime(localfiles, ftpinfo.getSeconds());
			ReduceList.reduce_ListByTime(ftpfiles, ftpinfo.getSeconds());
		}
		
		////将本地文件和传输日志进行对比，清理已上传项目
		ReduceList.ReduceList_ByLog(localfiles, ftpinfo);
		
		
		////将本地文件和FTP文件清单进行对比，清理已存在项目
		diff = new ReduceList();
		////上传业务且带重命名脚本
		if(ftpinfo.getShellfile() != null) {
			namefilter = diff.new NameFilter(ftpinfo.getShellfile(), ReduceList.get_NameList(localfiles));
			try {
				//超时设置为2分钟，即120秒
				TimeoutController.execute(namefilter, 120000);
			} catch(TimeoutException e) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				logger.error("run() - get rename file info from shell file "
						+ ftpinfo.getShellfile() + " time out, e= " + e, e); //$NON-NLS-1$
				return;
			}
			fileMap = namefilter.getFileNameMap();
		////上传业务
		} else {
			fileMap = null;
		}
		////清理本地文件中的重复项
		ReduceList.get_TransList(ftpfiles, localfiles, fileMap);
		
		
		//执行FTP上传任务
		if(!localfiles.isEmpty()) {
			FTPUploadThread uploadT = new FTPUploadThread(ftpinfo, 
					localfiles, fileMap);
			try {
				//超时设置为毫秒
				TimeoutController.execute(uploadT, uploadT.getTrans_period() * 1000);
			} catch (TimeoutException e) {
				Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
				logger.error("run() - upload file to  "
						+ ftpinfo.getHost() + " time out, e= " + e, e); //$NON-NLS-1$
				return;
			}
		}
	}
}
