package cn.pmsc.lb;

public class MyString {
	
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
