package ftpClient;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

public class FtpLog {

	private static String logpath = ((FileAppender)Logger.getRootLogger().getAppender("logFile")).getFile();
	private static List<String> log = null;
	private static boolean is_ready = false;
	
	/**
	 * 选择性的读取log4j日志文件
	 */
	public static void init_log() {
		if(!is_ready) {
			try {
				log = LocalFileUtils.readfile(logpath, "uploading");
			} catch (IOException e) {
				e.printStackTrace();
			}
			is_ready = true;
		}
	}
	
	/**
	 * 在读取的log4j日志中查找特定字符串
	 * 为防止发来的数据反悔(新数据错误，还原成旧数据，但旧数据在日志中已记录为传输成功)
	 * 采取从后向前查询日志，找到文件名，FTP服务器地址，FTP上传路径三项，
	 * 再对比文件大小和修改时间，不论结果如何，立即返回比较结果
	 * @param filepath，包含文件名的绝对路径
	 * @param filesize，文件大小，字节数
	 * @param mtime，文件的修改时间，秒数
	 * @param host，FTP服务器地址
	 * @param ftppath，FTP上传路径
	 * @return
	 */
	public static boolean search(String filepath, String filesize, String mtime, 
			String host, String ftppath) {
		
		if(log != null) {
			ListIterator<String> iter = log.listIterator(log.size());
			
			while (iter.hasPrevious()) {
				String s = (String) iter.previous();
				
				if(s.contains(filepath) && s.contains(host) && s.contains(ftppath)) {
					if(s.contains(filesize) && s.contains(mtime)) return true;
					else return false;
				}
			}
		}
		
		return false;
	}
	
}
