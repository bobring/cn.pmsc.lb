package com.pmsc.warning.test;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  
  



import com.pmsc.warning.client.*;  
  
public class Client {  
  
    public static void main(String[] args) throws Exception {  
//        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");  
//        FileShare client = (FileShare)context.getBean("client");
       
    	FileShareService fss = new FileShareService();
    	FileShare client = fss.getFileSharePort();
    	
        /*
    	 * Ĭ�Ϸ����ṩ10���������İ���Red,Orange,Yellow,Blue���͵�ȫ��Ԥ����Ϣ
    	 * 
        List<String> fileList= client.listFiles();
        for(String a :fileList){
        	 String target_dir="F:\\rcvtlqzip\\";  //����zip���ļ���·��
        	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+a);
        	 DataHandler returnHandler = 	client.download(a);
        	 returnHandler.writeTo(fileOutPutStream);
        	 fileOutPutStream.flush();  
             fileOutPutStream.close();  
        }*/
        
        /*
         * areaCode ����Ԥ������  G:���Ҽ�  S:ȫ��ʡ��  A:ȫ��Ԥ������   �������Զ�������:
         * ʾ��: ֻ��ȡ���Ҽ�,��д  : "G"
         *      ��ȡȫ��ʡ��,��д : "S"
         *      ��ȡȫ��Ԥ������,��д:"A" 
         *      ��ȡĳ������,��д "36000041600000" ,��������м��ö��Ÿ���,��"36000041600000,36000041600001"
         * warnLevel ����Ԥ������   ����:Red,Orange,Yellow,Blue �����������Ԥ����Ҫ�ڸ�������ö��Ÿ���   ��  "Red,Orange"
         * warnEvent ����Ԥ���¼����� ��  ̨���¼�:11B01  ����¼�֮���ö��Ÿ��� ,�����ȡȫ��Ԥ���¼�,������  "A"
         * itime     �������ʱ��(��λ:����),������Ҫ��ȡ�뵱ǰϵͳʱ��30�����ڵ�Ԥ������  ������  30
         * */
       /* List<String> fileList2= client.listFilesByElement("A", "YELLOW", "A", 200);
        for(String a :fileList2){
        	 String target_dir="F:\\rcvtlqzip\\";//����zip���ļ���·��
        	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+a);  
        	 DataHandler returnHandler = 	client.download(a);
        	 returnHandler.writeTo(fileOutPutStream);
        	 fileOutPutStream.flush();  
             fileOutPutStream.close();  
        }*/
        
        
        
        /*
         * ��ȡĬ��Ԥ��������Ϣ,��������Ϊ���ݱ��еĻ���Ԥ����Ϣ
         * Ĭ�Ϸ����ṩ10���������İ���Red,Orange,Yellow,Blue���͵�ȫ��Ԥ����Ϣ
         * 
        List<WarnCap> fileList3= client.listWarnCap();
        for(WarnCap a :fileList3){
        	 System.out.println(a.getIdentifier()+" "+a.getEventTypeCN());
        }*/
        
        
        /*
         * ��ȡ�Զ���Ԥ��������Ϣ,��������Ϊ���ݱ��еĻ���Ԥ����Ϣ
         * areaCode ����Ԥ������  G:���Ҽ�  S:ȫ��ʡ��  A:ȫ��Ԥ������   �������Զ�������:
         * ʾ��: ֻ��ȡ���Ҽ�,��д  : "G"
         *      ��ȡȫ��ʡ��,��д : "S"
         *      ��ȡȫ��Ԥ������,��д:"A" 
         *      ��ȡĳ������,��д "36000041600000" ,��������м��ö��Ÿ���,��"36000041600000,36000041600001"
         * warnLevel ����Ԥ������   ����:Red,Orange,Yellow,Blue �����������Ԥ����Ҫ�ڸ�������ö��Ÿ���   ��  "Red,Orange"
         * warnEvent ����Ԥ���¼����� ��  ̨���¼�:11B01  ����¼�֮���ö��Ÿ��� ,�����ȡȫ��Ԥ���¼�,������  "A"
         * itime     �������ʱ��(��λ:����),������Ҫ��ȡ�뵱ǰϵͳʱ��30�����ڵ�Ԥ������  ������  30
         * 
        List<WarnCap> fileList4= client.listWarnCapByElement("A", "RED,BLUE,YELLOW", "A", 200);
        for(WarnCap a :fileList4){
        	 System.out.println(a.getIdentifier()+" "+a.getEventTypeCN());
        }
         */
       
        /*
        List<String> fileList5= client.listFilesByTop(30);
        for(String a :fileList5){
        	 String target_dir="F:\\rcvtlqzip\\";//����zip���ļ���·��
        	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+a);  
        	 DataHandler returnHandler = 	client.download(a);
        	 returnHandler.writeTo(fileOutPutStream);
        	 fileOutPutStream.flush();  
             fileOutPutStream.close();  
        }*/
        
        
        //��ȡ��ǰ���ݿ������¸��µ�Ԥ������id��
        int maxWarnId=client.getMaxWarnId();
        
        //��ȡָ��Ԥ������id�ŵ�����id�ż��Ԥ������
//        List<String> fileList6=client.listFilesByWarnId(maxWarnId);
        List<String> fileList6=client.listFilesByElement("A", "Red,Orange,Yellow,Blue", "A", 1440);
        for(String a :fileList6){
       	 String target_dir="F:\\rcvtlqzip\\";//����zip���ļ���·��
       	System.out.println(a);
       	 
       	 //��ȡԤ���ļ�����
       	 String fileName=a.substring(0, a.lastIndexOf("_"));
       	 
       	 //��ȡ��ǰԤ��˳���
       	 String currentWarnId=a.substring(a.lastIndexOf("_")+1);
       	 System.out.println("预警ID="+currentWarnId+"文件名="+fileName);
       	 
//       	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+fileName);  
//       	 DataHandler returnHandler = 	client.download(fileName);
//       	 returnHandler.writeTo(fileOutPutStream);
//       	 fileOutPutStream.flush();  
//            fileOutPutStream.close();  
           
       }
        
    }  
  
}  