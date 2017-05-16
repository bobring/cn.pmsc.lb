package ftpClient;

import java.util.List;

import cn.pmsc.lb.MyString;

public class FtpInfo {
	private final String type;				//FTP业务类型，-upload为上传，-download为下载
	private final int seconds;				//文件的修改时间范围，从当前时间逆推
	private final String localpath;			//本地文件路径
	private final String local_wildcard;	//本地文件的文件名关键字
	private final String host;				//FTP站点名
    private final int port;					//FTP端口号，默认21
    private final String user;				//FTP用户名
    private final String password;			//FTP密码
    private final String ftppath;			//FTP路径
    private final String ftp_wildcard;		//FTP文件的文件名通配符
    private final boolean isTextMode;		//文件传输模式，true为文本模式，false为二进制模式
    private final boolean isPassiveMode;	//FTP连接模式，true为被动模式，false为主动模式
    
    private final String shellfile;			//可能存在的文件名过滤脚本
    
    private final int paras_num;			//当前输入的参数个数
    private final int paras_num_min = 11;	//参数个数下限，当存在重命名需求的时候，参数应为13个
    
    private final int pid;					//本条ftpinfo对应的线程标识号
    
	
	/**
	 * 认定参数行中只有空格作为分隔符，不包含双引号，每个独立参数中均不带空格
	 */
	public FtpInfo(String[] args, int pid, String Parent_Path) {
		super();
		
		if ((this.paras_num = args.length) >= paras_num_min) {
			this.type = args[0];
			
			try {
				this.seconds = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid Integer: \""
						+ args[1] + "\" in paras: " + args);
			}
			
			try {
				this.port = Integer.parseInt(args[5]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid Integer: \""
						+ args[5] + "\" in paras: " + args);
			}
			
			try {
				this.local_wildcard = MyWildCard.decode(args[3], false);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid Integer: \""
						+ args[3] + "\" in paras: " + args);
			}
			
			this.localpath = args[2];
			this.host = args[4];
			this.user = args[6];
			this.password = args[7];
			this.ftppath = args[8];
			
			
			if (this.paras_num >= paras_num_min + 2) {
				try {
					this.ftp_wildcard = MyWildCard.decode(args[9], true);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid Integer: \""
							+ args[9] + "\" in paras: " + args);
				}
				
				this.isTextMode = toTextMode(args[10]);
				this.isPassiveMode = toPassiveMode(args[11]);
				this.shellfile = MyString.filepath(Parent_Path, args[paras_num_min]);
			} else {
				try {
					this.ftp_wildcard = MyWildCard.decode(args[3], true);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid Integer: \""
							+ args[3] + "\" in paras: " + args);
				}
				
				this.isTextMode = toTextMode(args[9]);
				this.isPassiveMode = toPassiveMode(args[10]);
				this.shellfile = null;
			}
			
			this.pid = pid;
			
		} else if (this.paras_num >= paras_num_min - 2 && paras_num_min > 2) {
//			//统计错误参数，数量只少了2个以内，认为是用户输入错误
			throw new IllegalArgumentException("Invalid number "
					+ "of paras: " + args);
		} else {
			//其他情况，认为是用户输入的无效参数如注释或空行，不做统计
			throw new NullPointerException("Invalid number "
					+ "of paras: " + args);
		}
	}
	
	
	/**
	 * 按先双引号后空格的方式分割参数行
	 * @param line
	 * @param pid
	 * @param Parent_Path
	 */
	public FtpInfo(String line, int pid, String Parent_Path) {
		super();
		
		List<String> args = MyString.decode_args(line);
		
		if ((this.paras_num = args.size()) >= paras_num_min) {
			this.type = args.get(0);
			
			try {
				this.seconds = Integer.parseInt(args.get(1));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid Integer: \""
						+ args.get(1) + "\" in paras: " + args);
			}
			
			try {
				this.port = Integer.parseInt(args.get(5));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid Integer: \""
						+ args.get(5) + "\" in paras: " + args);
			}
			
			try {
				this.local_wildcard = MyWildCard.decode(args.get(3), false);
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid Integer: \""
						+ args.get(3) + "\" in paras: " + args);
			}
			
			this.localpath = args.get(2);
			this.host = args.get(4);
			this.user = args.get(6);
			this.password = args.get(7);
			this.ftppath = args.get(8);
			
			//输入参数为13位，即存在上传下载存在重命名需求，本地文件和FTP文件各自的通配符不一样
			if (this.paras_num >= paras_num_min + 2) {
				try {
					this.ftp_wildcard = MyWildCard.decode(args.get(9), true);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid Integer: \""
							+ args.get(9) + "\" in paras: " + args);
				}
				
				this.isTextMode = toTextMode(args.get(10));
				this.isPassiveMode = toPassiveMode(args.get(11));
				this.shellfile = MyString.filepath(Parent_Path, args.get(paras_num_min));
			} else {
				//输入参数为11位，即上传下载文件名一致，本地文件和FTP文件使用相同的通配符
				try {
					this.ftp_wildcard = MyWildCard.decode(args.get(3), true);
				} catch (Exception e) {
					throw new IllegalArgumentException("Invalid Integer: \""
							+ args.get(3) + "\" in paras: " + args);
				}
				
				this.isTextMode = toTextMode(args.get(9));
				this.isPassiveMode = toPassiveMode(args.get(10));
				this.shellfile = null;
			}
			
			this.pid = pid;
			
		} else if (this.paras_num >= paras_num_min - 2 && paras_num_min > 2) {
//			//统计错误参数，数量只少了2个以内，认为是用户输入错误
			throw new IllegalArgumentException("Invalid number "
					+ "of paras: " + args);
		} else {
			//其他情况，认为是用户输入的无效参数如注释或空行，不做统计
			throw new NullPointerException("Invalid number "
					+ "of paras: " + args);
		}
	}
	
	
	private boolean toTextMode(String mode) {
		if (mode.equalsIgnoreCase("textmode")) {
			return true;
		} 
		return false;
	}
	
	private boolean toPassiveMode(String mode) {
		if (mode.equalsIgnoreCase("port")) {
			return false;
		} 
		return true;
	}
	
	public String getType() {
		return type;
	}
	public int getSeconds() {
		return seconds;
	}
	public String getLocalpath() {
		return localpath;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	public boolean isTextMode() {
		return isTextMode;
	}
	public boolean isPassiveMode() {
		return isPassiveMode;
	}
	public String getFtppath() {
		return ftppath;
	}
	public String getShellfile() {
		return shellfile;
	}
	public int getParas_num() {
		return paras_num;
	}
	public int getParas_num_min() {
		return paras_num_min;
	}
	public int getPid() {
		return pid;
	}
	public String getFtp_wildcard() {
		return ftp_wildcard;
	}
	public String getLocal_wildcard() {
		return local_wildcard;
	}

	public String getTextMode() {
		if(isTextMode) {
			return "TextMode";
		} else {
			return "BinaryMode";
		}
	}
	public String getPassiveMode() {
		if(isPassiveMode) {
			return "Passive";
		} else {
			return "port";
		}
	}
	
	@Override
	public String toString() {
		return type + " " + seconds + " " + localpath + " " + local_wildcard
				+ " " + host + " " + port + " " + user 
				+ " " + password + " " + ftppath + " "
				+ ftp_wildcard + " " + getTextMode() 
				+ " " + getPassiveMode() + " " + shellfile;
	}
}
