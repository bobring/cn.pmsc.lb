package spring_jdbc.obs.bean;

import java.sql.Date;

public class Obs {
	private String id;
	private Date date;
	private byte[] statistics;
	
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
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte[] getStatistics() {
		return statistics;
	}
	public void setStatistics(byte[] statistics) {
		this.statistics = statistics;
	}
}
