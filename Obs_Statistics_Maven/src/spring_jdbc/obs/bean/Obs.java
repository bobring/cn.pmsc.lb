package spring_jdbc.obs.bean;

import java.sql.Date;
import java.util.Arrays;

public class Obs {
	protected String id;
	protected Date dtime;
	protected byte[] statistics;
	
//	public Obs(){}
	
//	public Obs(byte[] statistics) {
//		this.statistics = statistics;
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return dtime;
	}
	public void setDate(Date date) {
		this.dtime = date;
	}
	public void setDate(String str) {
//		this.dtime = Date.valueOf("2005-12-12");
		this.dtime = Date.valueOf(str);
	}
	public byte[] getStatistics() {
		return statistics;
	}
	public void setStatistics(byte[] statistics) {
		this.statistics = statistics;
	}
	public void setStatistics(String str) {
		byte[] bytes = new byte[str.length()];
		int i;
		
		for(i = 0; i < str.length(); i++) {
			bytes[i] = Byte.parseByte(Character.toString(str.charAt(i)));
		}
		this.statistics = bytes;
	}
	public boolean same(Obs b) {
		if(b.getId().equals(this.id) && b.getDate().equals(this.dtime)) {
			return true;
		}
		return false;
	}
	public boolean equals(Obs b) {
		if(this.same(b) && Arrays.equals(this.statistics, b.getStatistics())) {
			return true;
		}
		return false;
	}
}
