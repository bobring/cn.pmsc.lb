package com.pmsc.warning.client;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 1;
		for(String a:Client.wsdemo_getstalist()) {
			System.out.println(i + ": " + a);
			i++;
		}
	}

}
