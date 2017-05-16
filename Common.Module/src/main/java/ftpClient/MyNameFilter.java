package ftpClient;

import java.io.File;
import java.io.FilenameFilter;

public class MyNameFilter implements FilenameFilter {
	
	private String keywords;
	
	public MyNameFilter(String keywords) {
		this.keywords = keywords;
	}
	
	private boolean isMatch(String name) {
		if(name.matches(keywords)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return isMatch(name);
	}

}
