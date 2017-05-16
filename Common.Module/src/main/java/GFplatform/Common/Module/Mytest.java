package GFplatform.Common.Module;

import ftpClient.*;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

public class Mytest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		FTPUtils ftpclient = new FTPUtils();
//		ftpclient.connect("10.16.13.196", 21, "getdata", "getdata123", false, true);
////		FTPUtils.connect(host, port, user, password, isTextMode, isPassiveMode);
//		
//		if(!ftpclient.setWorkingDirectory("/hfdata/data/Micaps/citylist/citydata/ddata")) {
////			Stat.update_ThreadStatus(ftpinfo.getPid(), false); //记录本线程状态为异常
////			
////			logger.error("Invalid ftp path: " + ftpinfo.getFtppath()
////			+ " of host:" + ftpinfo.getHost() + " user:" + ftpinfo.getUser()
////			+ " password:" + ftpinfo.getPassword(), (Object)null); //$NON-NLS-1$
//			System.out.println("path not found");
//			ftpclient.disconnect();
//			return;
//		}
//		
//		//取得FTP指定路径下的文件清单
//		List<FTPFile> ftpfilelist = ftpclient.listFiles("-t *170510* *170509*");
//		//暂时断开连接
//		ftpclient.disconnect();
//		
//		for(FTPFile ff : ftpfilelist) {
//			System.out.println(ff.getRawListing());
//		}
		
		
//		String test = "*1234***345**qw*ert*";
//		String t1 = test.replaceAll("\\*+", "\\*");
//		System.out.println(t1);
//		
//		String t2 = t1.replaceAll("\\*", "\\.+");
//		
//		for(char c : t2.toCharArray()) {
//			System.out.print(c);
//		}
//		System.out.println("");
//		System.out.println(t2.length());
//		
//		String t3 = ".+1234.+";
//		System.out.println(t3);
//		System.out.println(t3.length());
//		System.out.println(t3);
//		
//		if(t1.matches(t3)) {
//			System.out.println(t3);
//		} else {
//			System.out.println(t1);
//		}
//		
//		System.out.println("\\s+");
//		
//		
//		String t4 = test.replaceAll("\\*+", "");
//		System.out.println(t4);
//		
//		if(t4.matches(t3)) {
//			System.out.println(t3);
//		} else {
//			System.out.println(t4);
//		}
//		
//		
//		String t5 = ".{0,}1234.{0,}";
//		
//		if(t4.matches(t5)) {
//			System.out.println(t5);
//		} else {
//			System.out.println(t4);
//		}
//		
//		String[] sections = test.split("\\.");
//		
//		for(int i = 0; i < sections.length; i++ ) {
//			System.out.println(sections[i]);
//		}
		
		String test = "\"1234***345**qw*\"ert*";
		String[] t1 = test.split("\"");
		
		for(int i = 0; i < t1.length; i++ ) {
			System.out.println(t1[i]);
			System.out.println(t1.length);
		}
	}

}
