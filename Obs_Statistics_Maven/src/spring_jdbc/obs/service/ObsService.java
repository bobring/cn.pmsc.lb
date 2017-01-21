package spring_jdbc.obs.service;

import java.sql.Date;
import java.util.List;
import spring_jdbc.obs.bean.*;

public interface ObsService {
	public void save(Obs obs);//保存用户进入数据库
	public void update(Obs obs);//更新
	public Obs getObs(String id, Date date); //查询
	public List<Obs> getObss(Date date);
	public List<Obs> getAllObss();
	public void delete(String id, Date date);
}
