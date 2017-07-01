package ftpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPFile;

public class ReduceList {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ReduceList.class);

	/**
	 * 利用外部命令，以文件名集合作为参数，得到过滤后的文件重命名对照表
	 *
	 */
	public class NameFilter implements Runnable {
		private List<String> command; // 完整命令行
		private List<String> names; // 命令参数，这里是文件名集合
		private Map<String, String> FileNameMap; // 生成的文件名对照表

		public NameFilter(String program, List<String> paras) {
			names = paras;
			command = new ArrayList<String>();
			command.add(program);
			command.addAll(paras);
			FileNameMap = new HashMap<String, String>();
		}
		
		public NameFilter(List<String> paras) {
			FileNameMap = new HashMap<String, String>();
			
			for(String s : paras) {
				FileNameMap.put(s, s);
			}
		}

		public Map<String, String> getFileNameMap() {
			return FileNameMap;
		}

		public void run() {
			// int i = 0;
			String[] str;

			try {
				ProcessBuilder pb = new ProcessBuilder(command);
				Process process = pb.start();

				// 获取标准输出
				InputStream fis = process.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));

				// 获取错误输出
				InputStream fes = process.getErrorStream();
				BufferedReader er = new BufferedReader(new InputStreamReader(fes));

				String line;

				while ((line = br.readLine()) != null && !line.isEmpty()) {
					if (logger.isDebugEnabled()) {
						logger.debug("run() - line={}", line); //$NON-NLS-1$
					}

//					if (logger.isInfoEnabled()) {
//						logger.info("run() - line={}", line); //$NON-NLS-1$
//					}
					str = line.trim().split("\\s+");
					if (str.length >= 2 && names.contains(str[0])) {
						FileNameMap.put(str[0], str[1]);
					}
				}

				while ((line = er.readLine()) != null && !line.isEmpty()) {
					logger.error("run() - line=" + line);
				}

			} catch (Exception ex) {
				logger.error(this.getClass().getName() + "@ " + ex, ex); //$NON-NLS-1$
			}
		}
	}
	
	
	/**
	 * 获取文件名，通配本地文件和FTP文件
	 * 遇上未识别的对象时，为防止出现null值错误，设定为1970年1月1日零时
	 * @param t
	 * @return
	 */
	private static <T> String get_fileName(T t) {
		if(t instanceof File) {
			File f = (File)t;
			return f.getName();
		} else if(t instanceof FTPFile) {
			FTPFile f = (FTPFile)t;
			return f.getName();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("get_fileDate(T) - {}", "failed to get time info from file " + t.toString()); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return null;
		}
	}
	
	
	/**
	 * 获取文件的最后修改时间，通配本地文件和FTP文件
	 * 遇上未识别的对象时，为防止出现null值错误，设定为1970年1月1日零时
	 * @param t
	 * @return
	 */
	private static <T> Date get_fileDate(T t) {
		if(t instanceof File) {
			File f = (File)t;
			return new Date(f.lastModified());
		} else if(t instanceof FTPFile) {
			FTPFile f = (FTPFile)t;
//			if (logger.isDebugEnabled()) {
//				logger.debug("get_fileDate(T) - {}", f.getName() + " " + f.getTimestamp().getTime().toString()); //$NON-NLS-1$ //$NON-NLS-2$
//			}

			if (logger.isTraceEnabled()) {
				logger.trace("get_fileDate(T) - {}", f.getName() + " " + f.getTimestamp().getTime().toString()); //$NON-NLS-1$
			}

			return f.getTimestamp().getTime();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("get_fileDate(T) - {}", "failed to get time info from file " + t.toString()); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return new Date(0);
		}
	}
	
	/**
	 * 检查文件的修改时间是否在需求范围内
	 * 
	 * @param ftime，文件时间
	 * @param nowtime，当前时间
	 * @param offset，偏移量，单位为秒
	 * @return
	 */
	private static boolean TimeInRange(Date ftime, Date nowtime, int offset) {
		Date ctime = new Date(nowtime.getTime() - offset * 1000);

		if (ftime.after(ctime)) {
			return true;
		}

		return false;
	}

	/**
	 * 检查本地文件和FTP文件的文件名，大小，修改时间是否相同
	 * 
	 * @param ftpfile，FTP文件
	 * @param localfile，本地文件
	 * @return
	 */
	private static boolean equals(FTPFile ftpfile, File localfile) {
		if (ftpfile.getSize() == localfile.length()
				&& ftpfile.getTimestamp().getTime().getTime() == (localfile.lastModified())) {
			return true;
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("equals() - " + ftpfile.getName() + ":" + ftpfile.getTimestamp().getTime()
						+ ":" + ftpfile.getSize() + " " + localfile.getName() + ":" 
						+ localfile.lastModified() + ":" + localfile.length()); //$NON-NLS-1$
			}
			return false;
		}
	}

	/**
	 * 按照时间区间过滤文件
	 * 
	 * @param <T>
	 * @param list
	 * @param offset
	 * @return
	 */
	public static <T> void reduce_ListByTime(List<T> list, int offset) {
		Date nowtime = new Date();
		
		if (logger.isDebugEnabled()) {
			logger.debug("reduce_ListByTime(List<T>, int) - {}", "before time reduce, " + list.size() + " results counted."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		for (Iterator<T> it = list.iterator(); it.hasNext() && !list.isEmpty();) {
			T a = (T)it.next();
			if (!TimeInRange(get_fileDate(a), nowtime, offset)) {
				it.remove();
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("reduce_ListByTime(List<T>, int) - {}", "after time reduce, " + list.size() + " results left."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}
	
	
	/**
	 * 剔除已存在的文件，生成待传输的文件清单
	 * @param source 参照清单
	 * @param target 生成清单
	 * @return
	 */
	public static <T, E> void get_TransList(List<T> source, List<E> target, Map<String, String> fileMap) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("get_TransList() - before trans reduce, " + target.size() + " results left."); //$NON-NLS-1$
		}
		
		for (Iterator<E> it = target.iterator(); it.hasNext() && !target.isEmpty();) {
			E a = (E) it.next();
			for (T b : source) {
				if(a instanceof File && b instanceof FTPFile) {
					File file = (File)a;
					FTPFile ff = (FTPFile)b;
					
					if(fileMap != null && fileMap.containsKey(file.getName())) {
						if(ff.getName().equals(fileMap.get(file.getName()))
								&& equals(ff, file)) {
							it.remove();
							break;
						}
					} else if(fileMap != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("get_TransList(List<T>, List<E>, Map<String,String>) - "
									+ file.getAbsolutePath() + " has no target name."); //$NON-NLS-1$
						}
					} else {
						if(ff.getName().equals(file.getName())
								&& equals(ff, file)) {
							it.remove();
							break;
						}
					}
				} else if(b instanceof File && a instanceof FTPFile) {
					File file = (File)b;
					FTPFile ff = (FTPFile)a;
					
					if(fileMap != null && fileMap.containsKey(ff.getName())) {
						if(file.getName().equals(fileMap.get(ff.getName()))
								&& equals(ff, file)) {
							it.remove();
							break;
						}
					} else if(fileMap != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("get_TransList(List<T>, List<E>, Map<String,String>) - "
									+ ff.getName() + " has no target name."); //$NON-NLS-1$
						}
					} else {
						if(ff.getName().equals(file.getName())
								&& equals(ff, file)) {
							it.remove();
							break;
						}
					}
				} else {
					if (logger.isTraceEnabled()) {
						logger.trace("get_TransList() - unknown type of "
								+ target.toString() + " or " + source.toString()); //$NON-NLS-1$
					}
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("get_TransList() - after trans reduce, " + target.size() + " results left."); //$NON-NLS-1$
		}
	}


	/**
	 * 从文件集合中提取文件名字符串，并生成集合
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> get_NameList(List<?> list) {
		List<String> namelist = new ArrayList<String>();
		for (Object obj : list) {
			if (obj instanceof FTPFile) {
				FTPFile ff = (FTPFile) obj;
				namelist.add(ff.getName());
			} else if (obj instanceof File) {
				File file = (File) obj;
				namelist.add(file.getName());
			}
		}

		return namelist;
	}
	
	
	
	/**
	 * 从target集合中剔除与source重名的项目
	 * @param source
	 * @param target
	 */
	public static <T> void reduce_ListByName(List<T> source, List<T> target) {
		List<String> dirnames = ReduceList.get_NameList(source);

		for (Iterator<T> it = target.iterator(); it.hasNext() 
				&& !target.isEmpty();) {
			T t = (T) it.next();
			if (dirnames.contains(get_fileName(t))) {
				it.remove();
			}
		}
	}
	
	
	/**
	 * 清理待上传文件中的已上传部分，
	 * 根据文件名，文件大小，修改时间，FTP地址，FTP上传路径等5项参数判断
	 * 5项参数都存在，才认为文件已上传
	 * @param localfiles
	 * @param ftpinfo
	 */
	public static void ReduceList_ByLog(List<File> list, FtpInfo ftpinfo) {
		if (logger.isDebugEnabled()) {
			logger.debug("ReduceList_ByLog() - before reduce, "
					+ list.size() + " files counted."); //$NON-NLS-1$
		}

		for (Iterator<File> it = list.iterator(); it.hasNext() 
				&& !list.isEmpty();) {
			File f = (File) it.next();
			if (FtpLog.search(f.getAbsolutePath(), Long.toString(f.length()), 
					Long.toString(f.lastModified()), ftpinfo.getHost(), ftpinfo.getFtppath() )) {
				it.remove();
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("ReduceList_ByLog() - after reduce, "
					+ list.size() + " files left."); //$NON-NLS-1$
		}
	}
}
