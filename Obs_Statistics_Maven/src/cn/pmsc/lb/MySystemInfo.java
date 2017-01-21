package cn.pmsc.lb;

import java.net.InetAddress;
import java.util.Properties;

public class MySystemInfo {
	private static InetAddress addr;
	private static Properties props=System.getProperties();
	
	static {
		try {
			addr = InetAddress.getLocalHost();
		} catch(Exception e) {
			e.printStackTrace();
			addr = null;
		}
	}
	
	public static String get_ipaddr() {
		if(addr != null) {
			return addr.getHostAddress().toString();
		} else {
			return "0.0.0.0";
		}
	}
	
	public static String get_hostname() {
		if(addr != null) {
			return addr.getHostName().toString();
		} else {
			return "unknownhost";
		}
	}
	
	public static String get_username() {
		return props.getProperty("user.name");
	}
	
	public static String get_homepath() {
		return props.getProperty("user.home");
	}
	
	public static String get_currrentpath() {
		System.out.println(props.getProperty("user.dir"));
		return props.getProperty("user.dir");
	}
	
	public static String get_javaversion() {
		return props.getProperty("java.version");
	}
	
	public static String get_javahomepath() {
		return props.getProperty("java.home");
	}
	
	public static String get_osname() {
		return props.getProperty("os.name");
	}
	
	public static String get_osversion() {
		return props.getProperty("os.version");
	}
}
