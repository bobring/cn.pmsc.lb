package ftpClient;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

public class DiffList {
	
	/**
	 * 检查文件的修改时间是否在需求范围内
	 * @param ftime，文件时间
	 * @param nowtime，当前时间
	 * @param offset，偏移量，单位为秒
	 * @return
	 */
	private static boolean TimeInRange(Date ftime, Date nowtime, int offset) {
		Date ctime = new Date(nowtime.getTime() - offset);
		
		if(ftime.after(ctime)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查本地文件和FTP文件的文件名，大小，修改时间是否相同
	 * @param ftpfile，FTP文件
	 * @param localfile，本地文件
	 * @return
	 */
	private static boolean equals(FTPFile ftpfile, File localfile) {
		if(ftpfile.getName().equals(localfile.getName()) && 
				ftpfile.getSize() == localfile.length() &&
				ftpfile.getTimestamp().getTime().getTime() == (localfile.lastModified())) {
			return true;
		}
		
		return false;
	}
	
	
	public static List<File> get_UploadList(List<FTPFile> ftpfilelist, List<File> localfilelist) {
		
	}
	
	public static List<FTPFile> get_DownloadList(List<FTPFile> ftpfilelist, List<File> localfilelist) {

	}
}
