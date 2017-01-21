package cn.pmsc.lb;

public class MyStringRegex {
	private static StringBuffer buf = new StringBuffer();
	
	public static boolean validURL(String str) {
		String regex = "[a-zA-Z]{1,}\\:\\/\\/[^\\s]{1,}";
		if(str.matches(regex)) {
			return true;
		} else {
			System.out.println(str + " is not valid URL.");
			return false;
		}
	}
	
	public static String[] getURLInfo(String str) {
		String protocol = null; //预置无效协议名
		String host = null; //预置无效主机名
		String port = null; //预置无效端口号
		String context = null; //预置空后缀字符串
		
		if(validURL(str)) {
			String[] addr = str.split("\\/");
			context = "/" + MyString.cutsubstr(str, "\\/", 3);
			
			if(addr.length >= 3) {
				protocol = addr[0];
				String[] ip_port = addr[2].split(":");
				if(ip_port.length >= 2) {
					host = ip_port[0];
					try {
						Integer.parseInt(ip_port[1]);
						port = ip_port[1];
					} catch (NumberFormatException e) {
						System.out.println("port " + ip_port[1] + " is not a valid number, "
								+ "url=" + str);
					}
				} else {
					host = addr[2];
					if (protocol.equalsIgnoreCase("http")) {
						port = "80";
					} else if(protocol.equalsIgnoreCase("ftp")) {
						port = "21";
					} else if(protocol.equalsIgnoreCase("https")) {
						port = "443";
					} else {
						System.out.println("unknown protocol of " + protocol + " "
								+ "url=" + str);
					}
					
				}
			}
		}
		
		return new String[]{protocol, host, port, context};
	}
}
