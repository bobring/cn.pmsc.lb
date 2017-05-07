package ftpClient;

import java.io.File;
import java.util.List;

public class LocalFileListThread extends Thread {
	private String localpath;
	private List<File> localfilelist;
	
	
	/**
	 * @param localpath
	 */
	public LocalFileListThread(String localpath) {
		this.localpath = localpath;
	}


	public String getLocalpath() {
		return localpath;
	}
	public List<File> getLocalfilelist() {
		return localfilelist;
	}
	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}


	public void run() {
		localfilelist = LocalFileUtils.listfiles(localpath);
	}
}
