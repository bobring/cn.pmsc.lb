package spring_jdbc.obs.bean;

import java.sql.Timestamp;
import java.util.Arrays;

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

	public void setList(String list) {
		this.list = list;
	}

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
		if(TimestampEquals(this.datatime, b.getDatatime()) && TimestampEquals(this.checktime, b.getchecktime())) {
			return true;
		}
		return false;
	}
	/**
	 * 比较两条记录的内容是否相同
	 * 由于内容值可能会较大，为节省计算资源，应尽量使用same方法，根据业务逻辑，一般也没必要使用equals方法
	 */
	public boolean equals(Sta b) {
		String[] array_a = null;
		String[] array_b = null;
		
		if(this.same(b)) {
			array_a = this.list.split(",");
			array_b = b.getList().split(",");
			
			Arrays.sort(array_a);
			Arrays.sort(array_b);
			
			if(Arrays.equals(array_a, array_b)) {
				return true;
			}
		}
		return false;
	}
}
