package com.pmsc.warning.test;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;  

import org.springframework.context.support.ClassPathXmlApplicationContext;  
  


import com.pmsc.warning.service.*;  
  
public class ClientV2 {  
  
    public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");  
        FileShare client = (FileShare)context.getBean("client");
       /* List<String> fileList=		  client.listWarnEvent();
        for(String f:fileList){
        	System.out.println(f);
        }*/
        
        /*
        List<String> info=		  client.listWarnRegionInfo();
        for(String f:info){
        	System.out.println(f);
        }*/
        
        /*
    	 * 默认方法提供10分钟内入库的包含Red,Orange,Yellow,Blue类型的全部预警信息
    	 */
      /*  List<String> fileList= client.listFiles();
        for(String a :fileList){
        	 String target_dir="F:\\rcvtlqzip\\";  //保存zip包文件的路径
        	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+a);
        	 DataHandler returnHandler = 	client.download(a);
        	 returnHandler.writeTo(fileOutPutStream);
        	 fileOutPutStream.flush();  
             fileOutPutStream.close();  
        }*/
        
        /*
         * areaCode 代表预警区域  G:国家级  S:全部省级  A:全部预警区域   其余需自定义区域:
         * 示例: 只获取国家级,填写  : "G"
         *      获取全部省级,填写 : "S"
         *      获取全部预警区域,填写:"A" 
         *      获取某个区域,填写 "36000041600000" ,多个区域中间用逗号隔开,如"36000041600000,36000041600001"
         * warnLevel 代表预警级别   包含:Red,Orange,Yellow,Blue 如需多个级别的预警需要在各级别间用逗号隔开   如  "Red,Orange"
         * warnEvent 代表预警事件类型 如  台风事件:11B01  多个事件之间用逗号隔开 ,如需获取全部预警事件,则输入  "A"
         * itime     代表入库时间(单位:分钟),例如需要获取与当前系统时间30分钟内的预警数据  则输入  30
         * */
       /* List<String> fileList2= client.listFilesByElement("A", "YELLOW", "A", 200);
        for(String a :fileList2){
        	 String target_dir="F:\\rcvtlqzip\\";//保存zip包文件的路径
        	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+a);  
        	 DataHandler returnHandler = 	client.download(a);
        	 returnHandler.writeTo(fileOutPutStream);
        	 fileOutPutStream.flush();  
             fileOutPutStream.close();  
        }*/
        
        
        
        /*
         * 获取默认预警数据信息,返回类型为数据表中的基础预警信息
         * 默认方法提供10分钟内入库的包含Red,Orange,Yellow,Blue类型的全部预警信息
         * 
        List<WarnCap> fileList3= client.listWarnCap();
        for(WarnCap a :fileList3){
        	 System.out.println(a.getIdentifier()+" "+a.getEventTypeCN());
        }*/
        
        
        /*
         * 获取自定义预警数据信息,返回类型为数据表中的基础预警信息
         * areaCode 代表预警区域  G:国家级  S:全部省级  A:全部预警区域   其余需自定义区域:
         * 示例: 只获取国家级,填写  : "G"
         *      获取全部省级,填写 : "S"
         *      获取全部预警区域,填写:"A" 
         *      获取某个区域,填写 "36000041600000" ,多个区域中间用逗号隔开,如"36000041600000,36000041600001"
         * warnLevel 代表预警级别   包含:Red,Orange,Yellow,Blue 如需多个级别的预警需要在各级别间用逗号隔开   如  "Red,Orange"
         * warnEvent 代表预警事件类型 如  台风事件:11B01  多个事件之间用逗号隔开 ,如需获取全部预警事件,则输入  "A"
         * itime     代表入库时间(单位:分钟),例如需要获取与当前系统时间30分钟内的预警数据  则输入  30
         * 
        List<WarnCap> fileList4= client.listWarnCapByElement("A", "RED,BLUE,YELLOW", "A", 200);
        for(WarnCap a :fileList4){
        	 System.out.println(a.getIdentifier()+" "+a.getEventTypeCN());
        }
         */
       
        /*
        List<String> fileList5= client.listFilesByTop(30);
        for(String a :fileList5){
        	 String target_dir="F:\\rcvtlqzip\\";//保存zip包文件的路径
        	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+a);  
        	 DataHandler returnHandler = 	client.download(a);
        	 returnHandler.writeTo(fileOutPutStream);
        	 fileOutPutStream.flush();  
             fileOutPutStream.close();  
        }*/
        
        
        //获取当前数据库中最新更新的预警序列id号
        /*   int maxWarnId=client.getMaxWarnId();
        System.out.println(maxWarnId);
        //获取指定预警序列id号到最新id号间的预警数据
          List<String> fileList6=client.listFilesByWarnId(100945);
        for(String a :fileList6){
       	 String target_dir="F:\\rcvtlqzip\\";//保存zip包文件的路径
       	//System.out.println(a);
       	 
       	 //获取预警文件名称
       	 String fileName=a.substring(0, a.lastIndexOf("_"));
       	 
       	 //获取当前预警顺序号
       	 String currentWarnId=a.substring(a.lastIndexOf("_")+1);
       	 System.out.println("当前id="+currentWarnId+"当前文件名="+fileName);
       	
       	 FileOutputStream fileOutPutStream = new FileOutputStream(target_dir+fileName);  
       	 DataHandler returnHandler = 	client.download(fileName);
       	 returnHandler.writeTo(fileOutPutStream);
       	 fileOutPutStream.flush();  
            fileOutPutStream.close();  */
           
      // }
        
    }  
  
}  