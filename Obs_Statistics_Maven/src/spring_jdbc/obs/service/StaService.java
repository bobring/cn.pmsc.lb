package spring_jdbc.obs.service;

import java.sql.Timestamp;
import java.util.List;

import spring_jdbc.obs.bean.Sta;

public interface StaService {
	public int insert(Sta sta);//插入新数据
	public int update(Sta sta);//更新已有数据
	public List<Sta> getSta(Timestamp datatime, Timestamp checktime); //查询，假定数据库datatime和checktime列为主索引，只可能存在一条数据
	public List<Sta> getStas(Timestamp datatime); //查询某一个时次数据的多条到站情况
	//查询某一段时间内的所有数据到站情况，[begintime, endtime]，区间为数据时次
	public List<Sta> getStas(Timestamp begintime, Timestamp endtime); 
	public List<Sta> getAllStas();
	public int delete(Timestamp datatime, Timestamp checktime);
}
