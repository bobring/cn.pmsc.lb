package cn.pmsc.business;

public class ApplyEnvVariables {
	
//	public static String getSysEnv(String name) {
//		return System.getenv(name);
//	}
	
	
	/**
	 * 用于替换字符串中的变量，将其替换为环境变量值
	 * 如果未找到环境变量值或者替换失败，则原值保持不变
	 * @param source
	 * @param var_name
	 * @return
	 */
	public static String ApplyEnvVar(String source, String var_name) {
		String value = System.getenv(var_name);
		String target = source;
		
		/** 正则表达式regex
		 * 第一行表示${var_name}形式的变量名
		 * 第二行表示"$var_name"形式的变量名
		 * 第三行表示$var_name形式的变量名
		 */
		if(value != null) {
			String regex = "\\$\\{" + var_name + "\\}" + "|"
							+ "\"\\$" + var_name + "\"" + "|"
							+ "\\$" + var_name;
			target = source.replaceAll(regex, value);
		} else {
			System.out.println("no such environment variable: " + var_name);
//			if(var_name.equals("NAS_PATH")) {
//				target = source.replaceAll("\\$\\{NAS_PATH\\}", "/mnt/data_nfs");
//			}
		}
		
		return target;
	}
	
	public static String ForcedApplyEnvVar(String source, String var_name, String value) {
		String target = source;
		
		if(value != null) {
			String regex = "\\$\\{" + var_name + "\\}" + "|"
							+ "\"\\$" + var_name + "\"" + "|"
							+ "\\$" + var_name;
			target = source.replaceAll(regex, value);
		}
		
		return target;
	}
} 
