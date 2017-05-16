package ftpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 统计程序运行中的各种数据，计算得出结果并打印
 * @author BobRing
 */
public class Stat {
//	private static int Args_Valid_num = 0; //统计正确的参数行个数，即正常初始化的线程数
	private static int Args_Invalid_num = 0; //统计不正确的参数行个数，即未正常初始化的线程数
	private static int Files_Success_num = 0; //统计传输成功的文件个数
	private static int Files_Failed_num = 0; //统计传输失败的文件个数
	
	//线程ID和对应状态的对照表
	private static Map<Integer, Boolean> ThreadStatus = new HashMap<Integer, Boolean>();
	
	private static String[] status_code = {"<running_status>0</running_status>",
			"<running_status>1</running_status>",
			"<running_status>2</running_status>",
			"<running_status>3</running_status>"};
	
	
	/**
	 * 统计错误参数行
	 */
	public static synchronized void add_invalidArg() {
		Args_Invalid_num++;
	}
	
	/**
	 * 统计正确参数行
	 */
//	public static synchronized void add_validArg() {
//		Args_Valid_num++;
//	}
	
	/**
	 * 统计传输成功文件个数
	 */
	public static synchronized void add_filesSuccess() {
		Files_Success_num++;
	}
	
	/**
	 * 更新线程状态对照表
	 */
	public static synchronized void update_ThreadStatus(int pid, boolean status) {
		ThreadStatus.put(pid, status);
	}
	
	/**
	 * 统计传输失败文件个数
	 */
	public static synchronized void add_filesFailed() {
		Files_Failed_num++;
	}

	/**
	 * @return 返回存在异常的线程个数
	 */
	public static int exception_threads() {
		int count = 0;
		for(boolean value : ThreadStatus.values()) {
			if(!value) {
				count++;
			}
		}
		return count;
	}
	
	
	/**
	 * @return 返回所有线程总数
	 */
	public static int threads_num() {
		return ThreadStatus.size();
	}

	/**
	 * @return 返回不正确的参数行个数
	 */
	public static int invalid_args() {
		return Args_Invalid_num;
	}
	
	/**
	 * @return 返回正确的参数行个数
	 */
//	public static int valid_args() {
//		return Args_Valid_num;
//	}

	/**
	 * @return 返回待传输的文件个数
	 */
	public static int success_files() {
		return Files_Success_num;
	}

	/**
	 * @return 返回传输失败的文件个数
	 */
	public static int failed_files() {
		return Files_Failed_num;
	}
	
	
	/**
	 * @param i
	 * @return 根据输入参数返回状态字符串
	 */
	public static String get_status_code(int i) {
		if(i >= 0 && i < status_code.length) {
			return status_code[i];
		}
		return "";
	}
	
	
	/**
	 * 更新线程状态对照表
	 */
	public static synchronized boolean get_ThreadStatus(int pid) {
		if(ThreadStatus.containsKey(pid)) {
			return ThreadStatus.get(pid);
		} else {
			throw new NoSuchElementException("no such pid of Thread: " + pid);
		}
	}
	
	/**
	 * 计算最终状态，并返回结果
	 * @return
	 */
	public static String get_status() {
		//有参数错误
		if(invalid_args() != 0) {
			return status_code[2];
		//所有线程全都发生异常
		} else if(exception_threads() == threads_num()
				&& threads_num() != 0) {
			return status_code[2];
		//部分线程发生异常且所有传输文件全部失败
		} else if(exception_threads() < threads_num()
				&& exception_threads() != 0
				&& success_files() == 0
				&& failed_files() != 0) {
			return status_code[2];
		//部分线程发生异常且部分传输文件失败或无传输失败
		} else if(exception_threads() != 0 
				&& success_files() > 0) {
			return status_code[3];
		//无线程发生异常且部分传输文件失败
		} else if(exception_threads() == 0 
				&& success_files() > 0
				&& failed_files() != 0) {
			return status_code[3];
		//无线程发生异常且所有传输文件成功
		} else if(exception_threads() == 0 
				&& success_files() != 0
				&& failed_files() == 0) {
			return status_code[0];
		//无线程发生异常且无传输文件待传
		} else if(exception_threads() == 0 
				&& success_files() == 0
				&& failed_files() == 0) {
			return status_code[1];
		//未知情况，默认错误值
		} else {
			return status_code[2];
		}
	}
}
