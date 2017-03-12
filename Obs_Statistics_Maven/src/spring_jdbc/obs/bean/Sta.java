package spring_jdbc.obs.bean;

//import java.io.Writer;
//import java.lang.reflect.Method;
//import java.sql.Clob;
//import java.sql.SQLException;
import java.sql.Timestamp;
//import java.util.Arrays;

//import javax.sql.rowset.serial.SerialException;

public class Sta {
	protected Timestamp datatime;
	protected Timestamp checktime;
	protected String list;
	protected long count;
	
	/**
	 * 用于比较两个时间戳, 由于timestamp带毫秒，无法比较是否相等，即equals方法永远为false，
	 * 只能先将timestamp中的秒数提取出来，再做比较
	 * @param a, 带毫秒的时间戳
	 * @param b, 带毫秒的时间戳
	 * @return
	 */
	protected Boolean TimestampEquals(Timestamp a, Timestamp b) {
		long dateA = a.getTime();
		long dateB = b.getTime();
		
		if(dateA == dateB) {
			return true;
		}
		return false;
	}
	
	public Timestamp getDatatime() {
		return datatime;
	}

	public void setDatatime(Timestamp datatime) {
		this.datatime = datatime;
	}
	
	public void setDatatime(String datatime) {
//		this.datatime = Timestamp.valueOf("yyyy-MM-dd HH:mm:ss");
		this.datatime = Timestamp.valueOf(datatime);
	}

	public Timestamp getchecktime() {
		return checktime;
	}

	public void setchecktime(Timestamp checktime) {
		this.checktime = checktime;
	}
	
	public void setchecktime(String checktime) {
//		this.checktime = Timestamp.valueOf("yyyy-MM-dd HH:mm:ss");
		this.checktime = Timestamp.valueOf(checktime);
	}

	public String getList() {
		return list;
	}
	
//	public String getListStr() throws SQLException {
//		return (list != null ? list.getSubString(1, (int) list.length()) : null);
//	}

	public void setList(String list) {
		this.list = list;
	}
	
//	public void setList(String str) throws SerialException, SQLException {
//		if(!str.isEmpty()) {
//			this.list = new javax.sql.rowset.serial.SerialClob(str.toCharArray());
//		} else {
//			this.list = null;
//		}
//	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	/**
	 * 比较两条记录是不是同一个
	 * 用于避免重复插入同一数据
	 */
	public boolean same(Sta b) {
		if(TimestampEquals(this.datatime, b.getDatatime()) && TimestampEquals(this.checktime, b.getchecktime())
				&& this.count == b.getCount()) {
			return true;
		}
		return false;
	}
}
