package ftpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileUtils {
	
	
	private static BufferedReader br;

	public static List<String> readfile(String filepath) throws IOException {
		
		File file = new File(filepath);
		
		if(file.exists() && file.isFile()) {
			List<String> buf = new ArrayList<String>();
			
			br = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = br.readLine()) != null) {
				buf.add(line);
			}
			
			return buf;
		} else {
			throw new IOException("not a regular file or does not exist: " + filepath);
		}
	}
	
	public static String get_ParentDir(String filepath) {
		File file = new File(filepath);
		
		if(file.exists()) {
			return file.getParent();
		} else {
			return null;
		}
	}
	
	
	
	/**
	 * 列出指定路径下的所有文件
	 * @param path
	 * @return
	 */
	public static List<File> listfiles(String path, String keywords) {
		List<File> filelist = new ArrayList<File>();
		
		File file = new File(path);
		
		if(file.isDirectory()) {
			File[] files = file.listFiles(new MyNameFilter(keywords));
			
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
	
	/**
	 * 列出指定路径下的所有文件夹
	 * @param path
	 * @return
	 */
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
