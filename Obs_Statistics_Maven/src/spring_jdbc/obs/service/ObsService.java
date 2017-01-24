package spring_jdbc.obs.service;

import java.sql.Date;
import java.util.List;
import spring_jdbc.obs.bean.*;

public interface ObsService {
	public int insert(Obs obs);//插入新数据
	public int update(Obs obs);//更新已有数据
	public List<Obs> getObss(String id, Date date); //查询
	public List<Obs> getObss(Date date);
	public List<Obs> getObss(String id);
	public List<Obs> getAllObss();
	public int delete(String id, Date date);
}
