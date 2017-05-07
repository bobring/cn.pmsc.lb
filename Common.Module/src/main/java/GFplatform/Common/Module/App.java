package GFplatform.Common.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ftpClient.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.TimeoutController;
import org.apache.commons.httpclient.util.TimeoutController.TimeoutException;
import org.apache.commons.net.ftp.FTPFile;

public class App {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	private String type;			//FTP业务类型，-upload为上传，-download为下载
	private int seconds;			//文件的修改时间范围，从当前时间逆推
	private String localpath;		//本地文件路径
	private String host;			//FTP站点名
    private int port = 21;			//FTP端口号，默认21
    private String user;			//FTP用户名
    private String password;		//FTP密码
    private boolean isTextMode;		//文件传输模式，true为文本模式，false为二进制模式
    private boolean isPassiveMode;	//FTP连接模式，true为被动模式，false为主动模式
    private String ftppath;			//FTP路径
//    private String program;			//文件名过滤脚本程序
    
    
	
	/**
	 * @param type
	 * @param seconds
	 * @param localpath
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param isTextMode
	 * @param isPassiveMode
	 * @param ftppath
	 */
	public App(String type, String seconds, String localpath, String host, 
			String port, String user, String password,
			String isTextMode, String isPassiveMode, String ftppath) {
		this.type = type;
//		this.seconds = seconds;
		try {
			this.seconds = Integer.parseInt(seconds);
			this.port = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			logger.error("main(String[]) - " + "failed to "
					+ "parse String to integer : " + seconds + " or " + port); //$NON-NLS-3$
			throw e;
		}
		this.localpath = localpath;
		this.host = host;
//		this.port = port;
		this.user = user;
		this.password = password;
//		this.isTextMode = isTextMode;
//		this.isPassiveMode = isPassiveMode;
		if(isTextMode.equalsIgnoreCase("textmode")) {
			this.isTextMode = true;
		} else {
			this.isTextMode = false;
		}
		
		if(isPassiveMode.equalsIgnoreCase("port")) {
			this.isPassiveMode = false;
		} else {
			this.isPassiveMode = true;
		}
		
		this.ftppath = ftppath;
	}
	
	
	
	
	
	public App(String[] args) {
		if (args.length >= 10) {
			this.type = args[0];
			try {
				this.seconds = Integer.parseInt(args[1]);
				this.port = Integer.parseInt(args[4]);
			} catch (NumberFormatException e) {
				logger.error(
						"main(String[]) - " + "failed to " + "parse String to integer : " + seconds + " or " + port); // $NON-NLS-3$
				throw e;
			}
			this.localpath = args[2];
			this.host = args[3];
			this.user = args[5];
			this.password = args[6];
			this.ftppath = args[7];
			
			if (args[8].equalsIgnoreCase("textmode")) {
				this.isTextMode = true;
			} else {
				this.isTextMode = false;
			}

			if (args[9].equalsIgnoreCase("port")) {
				this.isPassiveMode = false;
			} else {
				this.isPassiveMode = true;
			}
			
		} else {
			throw new RuntimeException("Invalid number of paras: " + args.toString());
		}
	}
		
		
	

	public static void main(String[] args) {
		List<FTPFile> ftpfiles;
		List<File> localfiles;
		App ftpapp;
		
		ReduceList diff;
		ReduceList.NameFilter namefilter;
		Map<String, String> fileMap;
		
		int minimum = 10;
		
		if(args.length >= minimum) {
			//初始化传递参数
			try {
				ftpapp = new App(args);
			} catch (NumberFormatException e) {
				logger.error("main(String[]) - e=" + e, e); //$NON-NLS-3$
				return;
			}
			
			//获取已存在的FTP文件清单和本地文件清单
			try {
				FTPListThread ftplistT = new FTPListThread(ftpapp.host, 
						ftpapp.port, ftpapp.user, ftpapp.password, ftpapp.isTextMode, 
						ftpapp.isPassiveMode, ftpapp.ftppath);
				//超时设置为2分钟，即120秒
				TimeoutController.execute(ftplistT, 120000);
				ftpfiles = ftplistT.getFtpfilelist();
				
				LocalFileListThread locallistT = new LocalFileListThread(ftpapp.localpath);
				//超时设置为2分钟，即120秒
				TimeoutController.execute(locallistT, 120000);
				localfiles = locallistT.getLocalfilelist();
			} catch(TimeoutException e) {
				logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
				return;
			}
			
			//计算待上传或下载的文件清单
			
			////利用时间区间精简文件清单
			ReduceList.reduce_ListByTime(localfiles, ftpapp.seconds);
			ReduceList.reduce_ListByTime(ftpfiles, ftpapp.seconds);
			
			////将本地文件和FTP文件清单进行对比，清理已存在项目
			diff = new ReduceList();
			
			if(ftpapp.type.equalsIgnoreCase("-download") && args.length >= minimum + 1) {
				namefilter = diff.new NameFilter(args[minimum], ReduceList.get_NameList(ftpfiles));
				try {
					//超时设置为2分钟，即120秒
					TimeoutController.execute(namefilter, 120000);
				} catch(TimeoutException e) {
					logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
					return;
				}
				fileMap = namefilter.getFileNameMap();
				ReduceList.get_TransList(localfiles, ftpfiles, fileMap);
			} else if(ftpapp.type.equalsIgnoreCase("-download")) {
				fileMap = null;
				ReduceList.get_TransList(localfiles, ftpfiles, fileMap);
			} else if(args.length >= minimum + 1) {
				namefilter = diff.new NameFilter(args[minimum], ReduceList.get_NameList(localfiles));
				try {
					//超时设置为2分钟，即120秒
					TimeoutController.execute(namefilter, 120000);
				} catch(TimeoutException e) {
					logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
					return;
				}
				fileMap = namefilter.getFileNameMap();
				ReduceList.get_TransList(ftpfiles, localfiles, fileMap);
			} else {
				fileMap = null;
				ReduceList.get_TransList(ftpfiles, localfiles, fileMap);
			}
			
			
			//执行FTP上传或下载任务
			if(ftpapp.type.equalsIgnoreCase("-download")) {
				FTPDownloadThread downloadT = new FTPDownloadThread(ftpapp.host, 
						ftpapp.port, ftpapp.user, ftpapp.password, 
						ftpapp.isTextMode, ftpapp.isPassiveMode, 
						ftpapp.ftppath, ftpfiles, ftpapp.localpath, fileMap);
				try {
					//超时设置为毫秒
					TimeoutController.execute(downloadT, downloadT.getTrans_period() * 1000);
				} catch (TimeoutException e) {
					logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
					return;
				}
				
			} else {
				FTPUploadThread uploadT = new FTPUploadThread(ftpapp.host, 
						ftpapp.port, ftpapp.user, ftpapp.password, 
						ftpapp.isTextMode, ftpapp.isPassiveMode, 
						ftpapp.ftppath, localfiles, fileMap);
				try {
					//超时设置为毫秒
					TimeoutController.execute(uploadT, uploadT.getTrans_period() * 1000);
				} catch (TimeoutException e) {
					logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
					return;
				}
			}
			
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("invalid number of args: " + args.toString() 
				+ System.lineSeparator() + "examples: "+ System.lineSeparator() 
				+ "1: -upload 3600 /mnt/data_nfs/ 10.16.36.21 21 ftp ftp /itemdata binary PASSIVE"
				+ System.lineSeparator()
				+ "2: -download 3600 /mnt/data_nfs/ 10.16.36.21 21 ftp ftp /itemdata textmode PASSIVE namefilter.sh"); //$NON-NLS-1$
			}
		}
	}
}
