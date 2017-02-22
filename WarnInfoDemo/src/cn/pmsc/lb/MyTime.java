package cn.pmsc.lb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {
	
	public static Date get_currenttime() {
		return new Date();
	}
	
	/**
	 * @param regex, 时间格式正则表达式
	 * @return 返回所需格式的当前时间
	 */
	public static String get_timeformat(String regex) {
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
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
