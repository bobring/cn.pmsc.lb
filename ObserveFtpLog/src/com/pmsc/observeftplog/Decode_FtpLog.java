package com.pmsc.observeftplog;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Decode_FtpLog {
	
	private Map<String, String> users_table = new HashMap<String, String>(); //记录用户名和home文件夹路径的对应关系
	private Map<String, String> files_table = new HashMap<String, String>(); //记录文件所在绝对路径和文件名的对应关系，一个周期内应清空一次
	private String[] LogElements; //记录一行FTP日志中的所有非空元素
	private boolean LogIsValid = false;
	private String Log_filename, Log_path;
	
	/**
	 * 构造方法
	 * 
	 * @param 
	 * @return 
	 */
	public Decode_FtpLog() {
		
	}
	
	/**
	 * 读入一行FTP日志，并尝试分析记录
	 * 若发现文件的绝对路径真实有效，并且缓存files_table不存在这一绝对路径
	 * 才将绝对路径和文件名记入缓存files_table中，防止同一路径下重复触发消息
	 * 
	 * @param LogLine
	 *            一行FTP日志
	 * @return boolean
	 *            返回布尔型变量，为真表示FTP日志行有效
	 */
	public boolean set_FtpLog(String LogLine) {
		LogElements = LogLine.trim().split("\\s+");  //将输入的日志行按空白字符进行分割
		
		System.out.println("processing line: " + LogLine);
		
		if(LogElements.length != 18) {
			System.out.println("invalid ftp log line: " + LogLine);
			LogIsValid =  false;
		} else if(! LogElements[17].equals("c")) {
			System.out.println("ftp transferring incompleted: " + LogLine);
			LogIsValid =  false;
		} else if(! LogElements[11].equals("i")) {
			System.out.println("no files uploaded: " + LogLine);
			LogIsValid =  false;
		} else {
			//LogIsValid =  true;
			//Log_username = LogElements[13];
			if((LogIsValid =  convert_FNFP(LogElements[8], LogElements[13])) ) {
				if( files_table.isEmpty() || ( ! files_table.containsKey(Log_path) ) ) {
					files_table.put(Log_path, Log_filename);
				}
			}
		}
		
		return LogIsValid;
	}
	
	/**
	 * 将文件记录清单中的所有文件进行迁移，触发消息
	 * 触发完成之后清空缓存，等待下一周期
	 * 
	 * @param
	 * 
	 * @return
	 */
	public void send_message() {
		String newpath = null;
		File tmppath;
		/*
		if(files_table.isEmpty()) {
			System.out.println("no files!");
		}
		*/
		System.out.println("begin to send message: ");
		
		for(Map.Entry<String, String> entry : files_table.entrySet() ) {  
            newpath = entry.getKey() + File.separator + "test";
            
            System.out.println("moving file in " + newpath);
            tmppath = new File(newpath);
            //若临时文件夹test不存在且创建失败，则不进行文件迁移操作
            if( (! tmppath.exists()) && (! tmppath.mkdir()) ) {
            	System.out.println("failed to create directory: " + newpath);
            } else {
            	if( MoveFile(entry.getValue(), entry.getKey(), newpath) ) {
                	MoveFile(entry.getValue(), newpath, entry.getKey());
                }
            }
        }
		
		files_table.clear();
	}
	
	
	
	/**
	 * 迁移文件，从一个目录到另一个目录
	 * 尝试提取文件名和绝对路径，成功的话分别记入Log_filename和Log_path中
	 * 
	 * @param filename
	 *            待迁移的文件名
	 * @param oldpath
	 *            文件所在原路径
	 * @param newpath
	 *            迁移的目标路径
	 * 
	 * @return boolean
	 *            迁移成功返回为真
	 */
	private boolean MoveFile(String filename, String oldpath, String newpath) {
        if(! oldpath.equals(newpath)) {
            File oldfile=new File(oldpath + File.separator + filename);
            File newfile=new File(newpath + File.separator + filename);
            if(oldfile.renameTo(newfile)) {
            	System.out.println("successfully moved file: " + filename + " from " + oldpath + " to " + newpath);
            	return true;
            }
            else {
            	System.out.println("failed to move file: " + filename + " from " + oldpath + " to " + newpath);
            	return false;
            }
        } else {
        	System.out.println( "the new path should be different from the old one: " );
        	System.out.println( oldpath + System.lineSeparator() + newpath );
        	
        	return false;
        }
    }
	
	/**
	 * 读入FTP日志中的文件路径，以及用户名，
	 * 尝试提取文件名和绝对路径，成功的话分别记入Log_filename和Log_path中
	 * 
	 * @param FilePath
	 *            FTP日志中的文件路径
	 * @param UserName
	 *            FTP日志中的用户名
	 * @return boolean
	 *            返回布尔型变量，为真表示文件路径真实有效
	 */
	private boolean convert_FNFP(String FilePath, String UserName) {
		File tmpfile = new File(FilePath);
		Log_filename = tmpfile.getName();
		
		String tmp_dirpath = tmpfile.getParent();
		
		File tmppath = new File(tmp_dirpath);
		if(tmppath.exists() && tmppath.isDirectory()) {
			Log_path = tmp_dirpath;
			return true;
		} else {
			tmp_dirpath = get_UserHome(UserName) + tmp_dirpath;
			tmppath = new File(tmp_dirpath);
			
			if(tmppath.exists() && tmppath.isDirectory()) {
				Log_path = tmp_dirpath;
				return true;
			} else {
				return false;
			}
		}
	}
	
	
	/**
	 * 根据输入的用户名查找并返回对应的home文件夹路径，
	 * 如果在已有记录中没找到，则通过finger命令在linux系统中查找其对应的home文件夹路径
	 * 找到之后，插入现有记录
	 * 
	 * @param username
	 *            用户名
	 * @return String
	 *            返回用户名对应的home文件夹路径，为空表示没找到
	 */
	private String get_UserHome(String username) {
		String result = null;
		//如果在已有记录中没找到，则尝试用finger命令在linux系统中找，找到之后，写入现有记录，方便下次查找
		if(users_table.isEmpty() || (! users_table.containsKey(username)) ) {
			if(! (result = finger_UserHome(username)).isEmpty() ) {
				users_table.put(username, result);
			}
		} else {
			result = users_table.get(username);
		}
		
        return result;
	}
	
	
	/**
	 * 根据输入的用户名在linux系统中查找其对应的home文件夹路径
	 * 
	 * @param username
	 *            用户名
	 * @return String
	 *            返回用户名对应的home文件夹路径，为空表示没找到
	 */
	private String finger_UserHome(String username) {
		
		String command = "finger " + username + " |grep irectory";
		
		String result = null;
		
		try {
			Process process = Runtime.getRuntime().exec(command);
			
			//获取标准输出
	        InputStream fis = process.getInputStream(); 
	        BufferedReader br = new BufferedReader(new InputStreamReader(fis)); 
	        
	        //获取错误输出
	        InputStream fes = process.getErrorStream();
	        BufferedReader er = new BufferedReader(new InputStreamReader(fes));
	        
	        
	        String line = null;
	        String[] tmpstr;
	        
	        while (! (line = br.readLine()).isEmpty()) { 
	        	System.out.println(line); 
	        	if(line.matches("Directory:.*")) {
	        		tmpstr = line.split("\\s+");
	        		if(tmpstr.length >= 2) {
	        			result = tmpstr[1];
	        		}
	        	}
	        }
	        
	        while (! (line = er.readLine()).isEmpty()) { 
	            System.out.println(line);
	        }
	        
	        System.out.println("waiting for command " + command);
	        
	        int exitcode = process.waitFor();
	        
	        if(exitcode != 0) {
	        	System.out.println("error while running command: " + command);
	        	result = null;
	        } 
		} catch(Exception ex) {
			System.out.println(ex.toString());
		} 
		
		return result;
	}

}
