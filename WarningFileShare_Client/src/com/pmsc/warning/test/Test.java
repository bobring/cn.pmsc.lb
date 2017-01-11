package com.pmsc.warning.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url = null;
		int num;
		List<String> ziplist = null;
		
		try {
			url = new URL("http://10.0.65.169:8080/WarningFileShare/WarnFileService?wsdl");
			num = 100;
			
			ziplist = Client.wsdemo_gettopziplist(url, num);
			
			Collections.sort(ziplist, new Client().new SortByFileName());
			
			for(String a:ziplist) {
				System.out.println(a);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
