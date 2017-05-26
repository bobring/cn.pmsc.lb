package ftpClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UnknownFormatConversionException;

public class MyWildCard {
	
	private final static Date beginTime = new Date();
	private static Calendar resultTime = Calendar.getInstance(); 
	
	/**
	 * 因为存在多线程对静态私有变量同时进行读写的可能，需要对相关的public方法加上同步synchronized修饰
	 * @param code 待转码的通配符关键字
	 * @param type 整型变量，
	 * 0为FTP文件名通配符，带通配符星号*；
	 * 1为本地文件名过滤器的正则表达式，不带通配符星号*，而是用.{0,}，等效于星号*(任意长度的任意字符)；
	 * 2为路径字符串，不带通配符等特殊字符；
	 * 隐患：用户提供的文件名关键字中包含小数点.符号的话，在正则表达式中会被认为是匹配单个任意字符，
	 * 目前还没发现因此导致的问题
	 * @return 返回解码后的通配符关键字
	 */
	public static synchronized String decode(String code, int type) {
		
		if(code.isEmpty()) {
			System.out.println("empty wildcard.");
			throw new IllegalArgumentException("empty wildcard.");
		}
		
		resultTime.setTime(beginTime);
		String result;
		
		//路径字符串中不包含通配符等特殊字符
		if(type == 2) {
			result =  splitCode(code);
		//FTP文件名关键字
		} else if(type == 0) {
			//返回值前后各补上一个星号*
			result = "*" + splitCode(code) + "*";
			//多个连续的星号*替换成一个*
			result = result.replaceAll("\\*+", "\\*");
		//本地文件名关键字
		} else if(type == 1) {
			//返回值前后各补上一个星号*
			result = "*" + splitCode(code) + "*";
			//多个连续的星号*替换成一个*
			result = result.replaceAll("\\*+", "\\*");
			//JAVA代码中必须用"\\.{0,}"表示".{0,}"，正则表达式".{0,}"与通配符星号*等效，用"\\*"表示星号*
			result = result.replaceAll("\\*", "\\.{0,}");
		} else {
			System.out.println("unknown type of " + type);
			throw new IllegalArgumentException("unsupported code type: "
					+ type + " , must >= 0 and <= 2.");
		}
		
		return result;
		
	}
	
	/**
	 * 提取翻译字符串中的时间变量，以大括号为分隔符
	 * @param code
	 * @return
	 */
	private static String splitCode(String code) {
//		System.out.println("splitCode: " + code);
		
		int i = code.indexOf("{");
		int j = code.indexOf("}");
		
		//包含正常时间变量字符串{...}，进行分段和翻译操作
		if(i != -1 && j != -1 && i < j-1) {
			String prefix = code.substring(0, i); //前置段，非时间变量，保持不变
			String timeStr = translate(code.substring(i+1, j)); //中间段，大括号内的部分，时间变量
			String postfix = splitCode(code.substring(j+1)); //后置段，可能包含时间变量，用递归继续判断
			
//			System.out.println("prefix: " + prefix);
			
			return prefix + timeStr + postfix;
		//不包含任何时间变量字符串{...}，直接返回原值
		} else if(i == -1 && j == -1) {
			return code;
		//其他情况直接抛出错误
		} else {
			System.out.println("invalid time var: " + code);
			throw new IllegalArgumentException("invalid time var: " + code);
		}
	}
	
	/**
	 * 翻译大括号内的时间变量字符串
	 * @param timeStr
	 * @return
	 */
	private static String translate(String timeStr) {
//		System.out.println("translate: " + timeStr);
		
		int num;
		String[] sections;
		
		if(timeStr.indexOf(",") != -1) {
			sections = timeStr.split(","); //使用逗号,作为分隔符，因为逗号,不太可能出现在文件名中
		} else {
			sections = timeStr.split("\\s+"); //不包含逗号，则尝试使用空格作为分隔符
		}
		
		//默认时间变量字符串中的最后一段为格式字符串
		SimpleDateFormat sdf = new SimpleDateFormat(sections[sections.length-1]);
		
		//默认除了最后一段，时间变量字符串中的前几段都是位移操作
		for(int i = 0; i < sections.length-1; i++ ) {
			//删除时间位移字符串中的所有字母，剩下的即为数字部分
			String num_str = sections[i].replaceAll("\\p{Alpha}", "");
			//按数字分割位移字符串，为了提前出其中纯字母的时间单位部分，年月日时分秒
			String[] parts = sections[i].split("\\d+");
			
			num = Integer.parseInt(num_str); //转换字符串为数字变量
			addTime(num, TimeUnit(parts[parts.length-1])); //对私有静态时间变量进行位移操作
		}
		
		return sdf.format(resultTime.getTime()); 
	}
	
	/**
	 * 对私有静态时间变量进行位移操作
	 * @param num
	 * @param unit
	 */
	private static void addTime(int num, int unit) {
		
		resultTime.add(unit, num);
	}
	
	/**
	 * 翻译时间单位，年月日时分秒
	 * @param unit
	 * @return
	 */
	private static int TimeUnit(String unit) {
		if(unit.equalsIgnoreCase("year")) {
			return Calendar.YEAR;
		} else if(unit.equalsIgnoreCase("month")) {
			return Calendar.MONTH;
		} else if(unit.equalsIgnoreCase("mon")) {
			return Calendar.MONTH;
		} else if(unit.equalsIgnoreCase("day")) {
			return Calendar.DAY_OF_YEAR;
		} else if(unit.equalsIgnoreCase("hour")) {
			return Calendar.HOUR_OF_DAY;
		} else if(unit.equalsIgnoreCase("minute")) {
			return Calendar.MINUTE;
		} else if(unit.equalsIgnoreCase("min")) {
			return Calendar.MINUTE;
		} else if(unit.equalsIgnoreCase("second")) {
			return Calendar.SECOND;
		} else if(unit.equalsIgnoreCase("sec")) {
			return Calendar.SECOND;
		} else {
			System.out.println("unknown time UNIT: " + unit);
			throw new UnknownFormatConversionException("unknown time UNIT: " + unit);
		}
	}
	
}
