package cn.pmsc.lb;

import java.io.UnsupportedEncodingException;

public class MyCharEncoding {
	
	public static String setEncoding(String str, String encode) {
		try {
			return new String(str.getBytes(encode), encode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static String getEncoding(String str) {
		String encode = null;
		
		try {
			encode = "GB2312";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return encode;
			}
		} catch(Exception e) {
		}
		
		try {
			encode = "ISO-8859-1";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return encode;
			}
		} catch(Exception e) {
		}
		
	    try {
			encode = "UTF-8";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return encode;
			}
		} catch(Exception e) {
		}
	    
	    try {
			encode = "GBK";
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return encode;
			}
		} catch(Exception e) {
		}
	    
	    return null;        //如果都不是，说明输入的内容不属于常见的编码格式。
	}
	
}
