package com.pmsc.warning.client;


public class AuthHeader
{
	private static final String QNAME = "http://service.warning.pmsc.com/";
	public static String getQname() {
		return QNAME;
	}
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}