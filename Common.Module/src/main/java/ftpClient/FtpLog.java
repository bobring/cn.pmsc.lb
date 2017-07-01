package ftpClient;

import java.io.IOException;
import java.util.List;

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
	 * 在读取的log4j日志中查找特定字符串，
	 * 多个字符串在同一行同时存在时，才认为是找到
	 * @param keys
	 * @return
	 */
	public static boolean search(String... keys) {
		if(log != null) {
			for(String s : log) {
				int i;
				for(i=0; i<keys.length; i++) {
					if(!s.contains(keys[i])) break;
				}
				if(i>=keys.length) return true;
			}
		}
		
		return false;
	}
	
}
