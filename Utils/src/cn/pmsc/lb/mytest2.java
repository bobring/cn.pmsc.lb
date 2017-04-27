package cn.pmsc.lb;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;

public class mytest2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("F:\\temp\\data\\1.txt");
		FileInputStream fis = null;
		byte[] buf = null;
		StringBuffer sb = new StringBuffer();
		
		if (file.exists() && !file.isDirectory()) {
			buf = new byte[(int) file.length()];
			fis = new FileInputStream(file);
			while (fis.read(buf) != -1) {
				sb.append(new String(buf));
//				buf = new byte[1024];
			}
			String[] lines = sb.toString().split(System.lineSeparator());
			
			for(int i = 0; i < lines.length; i++) {
				System.out.println(i + ": " + lines[i]);
			}
		}
		
	}

}
