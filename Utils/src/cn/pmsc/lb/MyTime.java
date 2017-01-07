package cn.pmsc.lb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {
	
	public static Date get_currenttime() {
		return new Date();
	}
	
	public static String get_timeformat(SimpleDateFormat sdf) {
		return sdf.format(new Date());
	}
	
	public static String get_stdtimeformat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String get_14bitstimeformat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

}
