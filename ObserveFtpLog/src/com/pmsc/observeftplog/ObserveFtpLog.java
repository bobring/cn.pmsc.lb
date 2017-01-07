package com.pmsc.observeftplog;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ObserveFtpLog {
	
	private List<String> FtpLog = new ArrayList<String>();
	
	public synchronized void WriteLog(String Line) {
		FtpLog.add(Line);
	}
	
	public synchronized List<String> ReadThenClearLog() {
		List<String> list = new ArrayList<String>(FtpLog);
		//list.addAll(FtpLog);
		System.out.println("log list number = " + list.size());
		FtpLog.clear();
		System.out.println("log list number = " + list.size());
		return list;
	}
	
	class ThreadMonitor implements Runnable{
		private List<String> command;
		
		public ThreadMonitor(List<String> cmd){
			command = cmd;
		}
		
		public void run(){
			int i = 0;
			try{
				ProcessBuilder pb = new ProcessBuilder(command);
				Process process = pb.start();
				
				//设置一个kafka消息生产者
				//KafkaProducer producer = new KafkaProducer("shujushi333");
				
				//获取标准输出
		        InputStream fis = process.getInputStream(); 
		        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		        
		      //获取错误输出
		        InputStream fes = process.getErrorStream();
		        BufferedReader er = new BufferedReader(new InputStreamReader(fes));
				
		        String line = null;
		        
		        while (! (line = br.readLine()).isEmpty()) { 
                	System.out.println(line);
                	new KafkaProducer("shujushi333").produce(i, line); //发送一条消息之后自动关闭
                	
                	i++;
                	//WriteLog(line);
                }
		        
		        while (! (line = er.readLine()).isEmpty()) { 
                    System.out.println(line);
                }
		        
			} catch(Exception ex){
				System.out.println(this.getClass().getName() + "@ " + ex.toString());
			}
			
		}
	}
	
	public void ProcessLogHistory() throws IOException { 
        //StringBuffer cmdout = new StringBuffer(); 
        
		Decode_FtpLog decoder = new Decode_FtpLog();
		
		while (true) {
			
			try{
                Thread.currentThread();
				Thread.sleep(3000);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
			
			List<String> LogHistory = ReadThenClearLog();
			
			System.out.println("log history has " + LogHistory.size() + " lines");
			
			if(! LogHistory.isEmpty()){
				int i = 1;
				for(String line : LogHistory) {
		        	decoder.set_FtpLog(line);
		        	System.out.println(i + ": " + line);
		        	i++;
				}
			}
	        
	        decoder.send_message();
		}
    } 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String command = "sudo tail -F /var/log/xferlog";
		//String command = "notepad.exe";
		
		List<String> command = new ArrayList<String>();
		
		command.add("sudo");
		command.add("tail");
		command.add("-F");
		command.add("/var/log/xferlog");
		
		//command.add("notepad.exe");
		
		try{
			ObserveFtpLog obs = new ObserveFtpLog();
			ThreadMonitor tm = obs.new ThreadMonitor(command);
			
			Thread thread = new Thread(tm);
			thread.start();
			
			//obs.ProcessLogHistory();
			
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
	}

}
