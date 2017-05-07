package ftpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalFileUtils {
	
	public static List<File> listfiles(String path) {
		List<File> filelist = new ArrayList<File>();
		
		File file = new File(path);
		
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			
			for(int i = 0; i < files.length; i++) {
				if(files[i].isFile()) {
					filelist.add(files[i]);
				}
			}
		} else {
			System.out.println(path + " is not a Directory or does not exist.");
		}
		
		return filelist;
	}
	
	public static List<File> listdirs(String path) {
		List<File> dirlist = new ArrayList<File>();
		
		File file = new File(path);
		
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					dirlist.add(files[i]);
				}
			}
		} else {
			System.out.println(path + " is not a Directory or does not exist.");
		}
		
		return dirlist;
	}
}
