package ftpClient;

//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

import ftpClient.FTPUtils;

public class FTPListThread extends Thread {
	private int period_seconds;		//监控文件的时间范围，比如只处理最近24小时内的文件，以秒为单位
    private String host;			//FTP站点名
    private int port = 21;			//FTP端口号，默认21
    private String user;			//FTP用户名
    private String password;		//FTP密码
    private boolean isTextMode;		//文件传输模式，true为文本模式，false为二进制模式
    private boolean isPassiveMode;	//FTP连接模式，true为被动模式，false为主动模式
    private String ftppath;			//FTP路径
//    private String localpath;		//本地路径
    
    private List<FTPFile> ftpfilelist = new ArrayList<FTPFile>(); //FTP文件清单


	public List<FTPFile> getFtpfilelist() {
		return ftpfilelist;
	}

//	public void setFtpfilelist(List<FTPFile> ftpfilelist) {
//		this.ftpfilelist = ftpfilelist;
//	}

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
	public FTPListThread(int period_seconds, String host, int port, String user, String password, boolean isTextMode,
			boolean isPassiveMode, String ftppath) {
		this.period_seconds = period_seconds;
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.isTextMode = isTextMode;
		this.isPassiveMode = isPassiveMode;
		this.ftppath = ftppath;
//		this.localpath = localpath;
	}
	
	/**
	 * 检查本地文件和FTP文件的文件名，大小，修改时间是否相同
	 * @param ftpfile，FTP文件
	 * @param localfile，本地文件
	 * @return
	 */
//	private boolean equals(FTPFile ftpfile, File localfile) {
//		if(ftpfile.getName().equals(localfile.getName()) && 
//				ftpfile.getSize() == localfile.length() &&
//				ftpfile.getTimestamp().getTime().getTime() == (localfile.lastModified())) {
//			return true;
//		}
//		
//		return false;
//	}
	
	/**
	 * 检查文件的修改时间是否在需求范围内
	 * @param ftime，文件时间
	 * @param nowtime，当前时间
	 * @param offset，偏移量，单位为秒
	 * @return
	 */
	private boolean TimeInRange(Date ftime, Date nowtime, int offset) {
		Date ctime = new Date(nowtime.getTime() - offset);
		
		if(ftime.after(ctime)) {
			return true;
		}
		
		return false;
	}

	public void run() {
		// TODO Auto-generated method stub
//		List<FTPFile> ftpfilelist;
//		List<File> localfilelist;
		
		FTPFile ff;
//		File file;
		
		Date nowtime = new Date();
		
		try {
			//连接FTP
			FTPUtils.connect(host, port, user, password, isTextMode, isPassiveMode);
			//取得FTP指定路径下的文件清单
			ftpfilelist = FTPUtils.listFiles(ftppath);
			//暂时断开连接
			FTPUtils.disconnect();
			
			//取得本地路径下的文件清单
//			localfilelist = LocalFileUtils.listfiles(localpath);
			
			//清理预订日期之外的文件，不纳入比较范围
//			for(Iterator<FTPFile> itor = ftpfilelist.iterator(); itor.hasNext() && !ftpfilelist.isEmpty();) {
//				ff = (FTPFile)itor.next();
//				if(!TimeInRange(ff.getTimestamp().getTime(), nowtime, period_seconds)) {
//					itor.remove();
//				}
//			}
			//清理预订日期之外的文件，不纳入比较范围
//			for(Iterator<File> it = localfilelist.iterator(); it.hasNext() && !localfilelist.isEmpty();) {
//				file = (File)it.next();
//				if(!TimeInRange(new Date(file.lastModified()), nowtime, period_seconds)) {
//					it.remove();
//				}
//			}
			
			//FTP上已存在的同名文件，直接删除，不做保留
//			for(Iterator<File> it = localfilelist.iterator(); it.hasNext() && !localfilelist.isEmpty();) {
//				file = (File)it.next();
//				for(FTPFile f: ftpfilelist) {
//					if(equals(f, file)) {
//						it.remove();
//						break;
//					}
//				}
//			}
			
//			//连接FTP
//			FTPUtils.connect(host, port, user, password, isTextMode, isPassiveMode);
//			//逐个上传文件
//			for(File f: localfilelist) {
//				try {
//					FTPUtils.upload(f.getName(), f);
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//			//断开连接
//			FTPUtils.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
