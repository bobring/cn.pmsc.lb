package cn.pmsc.lb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyString {
	
	/**
	 * 先双引号后空格来解析参数行
	 * @param line
	 * @return
	 */
	public static List<String> decode_args(String line) {
		
		String[] args = line.trim().split("\""); //首先按双引号分割参数行
		//如果双引号数量非偶数(包括0)，即分割后的字符串为偶数个，认为参数错误
		if(args.length % 2 != 1) {
			throw new IllegalArgumentException("Invalid number of "
					+ "\" in paras: " + args.length);
		}
		
		return split_paras(args);
	}
	
	
	/**
	 * 按空格分隔字符串，并根据之前decode_args方法按双引号分割的结果拼接成完整参数
	 * @param args
	 * @return
	 */
	private static List<String> split_paras(String[] args) {
		List<String> array = new ArrayList<String>();
		String[] sections;
		String pre_str = "", post_str = ""; //定义双引号之前和双引号之后的相连字符串
		
		for(int i = 0; i < args.length; i++) {
			sections = args[i].split("\\s+");
			//原参数行不包含双引号
			if(args.length == 1) {
				add_paras(sections, 0, sections.length-1, array); //所有内容直接加入参数集合
				break;
			//按双引号分割的第一段字符串，再按空格分隔之后的最后一部分为双引号的前缀
			} else if(i == 0) {
				pre_str = sections[sections.length-1];
				
				add_paras(sections, 0, sections.length-2, array); //扣除最后一段字符串
			//按双引号分割的最后一段字符串，再按空格分隔之后的第一部分为双引号的后缀
			} else if(i == args.length-1) {
				post_str = sections[0];
				
				add_paras(sections, 1, sections.length-1, array); //扣除最后一段字符串
			//按双引号分割的奇数段，即序号为偶数的部分，再按空格分隔之后，第一部分为双引号的后缀，最后一部分为双引号的前缀
			} else if(i % 2 == 0) {
				post_str = sections[0];
				pre_str = sections[sections.length-1];
				
				add_paras(sections, 1, sections.length-2, array); //扣除第一段和最后一段字符串
			//按双引号分割的偶数段，即序号为奇数的部分，视为双引号之内的变量内容，再与前缀后缀拼接成一个完整参数
			} else {
				array.add(pre_str + args[i] + post_str);
			}
		}
		
		return array;
	}
	
	
	private static void add_paras(String[] args, int begin, int end, List<String> array) {
		for(int i = begin; i <= end; i++) {
			if(!args[i].isEmpty() && args[i] != null) {
				array.add(args[i]);
			}
		}
	}
	
	
	/**
	 * 将路径和文件名连接在一起，合成绝对路径
	 * @param path
	 * @param filename
	 * @return
	 */
	public static String filepath(String path, String filename) {
		if(path.endsWith(File.separator)) {
			return path + filename;
		} else if(!path.isEmpty()) {
			return path + File.separator + filename;
		} else {
			return filename;
		}
	}
	
	/**
	 * 将输入的字符串按regex分割，取出第num段字符串作为返回值
	 * @param str 输入字符串, regex 分隔符, num 从0开始的字符串序号
	 * @return
	 */
	public static String cutstr(String str, String regex, int num){
		String[] s1 = str.split(regex);
		
		if(s1.length > num) {
			return s1[num];
		} else {
			return null;
		}
	}
	
	/**
	 * 将输入的字符串按regex分割，取出第num段以及之后的所有字符作为返回值
	 * @param str 输入字符串, regex 分隔符, num 从0开始的字符串序号
	 * @return
	 */
	public static String cutsubstr(String str, String regex, int num){
		String[] s1 = str.split(regex, num + 1);
		
		if(s1.length > num) {
			return s1[num];
		} else {
			return null;
		}
	}
}
