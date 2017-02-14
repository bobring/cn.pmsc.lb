package spring_jdbc.obs.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import spring_jdbc.obs.bean.Sta;
import spring_jdbc.obs.service.StaService;

//声明事务,这样就会自动的打开事务和关闭事务
@Transactional
public class StaServiceBean implements StaService {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int insert(Sta sta) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("insert into OBS_STAMISSING(datatime, checktime, list, count) values(?, ?, ?, ?)", 
				new Object[]{sta.getDatatime(), sta.getchecktime(), sta.getList(), sta.getCount()});
	}

	@Override
	public int update(Sta sta) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update OBS_STAMISSING set list=?,count=? where checktime=? and datatime=?", 
				new Object[]{sta.getList(), sta.getCount(), sta.getchecktime(), sta.getDatatime()});
	}

	@Override
	public List<Sta> getSta(Timestamp timestamp, Timestamp timestamp2) {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STAMISSING where datatime=? and checktime=?", 
				new Object[]{timestamp, timestamp2}, new StaRowMapper());
	}

	@Override
	public List<Sta> getStas(Timestamp datatime) {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STAMISSING where datatime=?", 
				new Object[]{datatime}, new StaRowMapper());
	}

	@Override
	public List<Sta> getStas(Timestamp begintime, Timestamp endtime) {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STAMISSING where datatime between ? and ?", 
				new Object[]{begintime, endtime}, new StaRowMapper());
	}

	@Override
	public List<Sta> getAllStas() {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STAMISSING", new StaRowMapper());
	}

	@Override
	public int delete(Timestamp datatime, Timestamp checktime) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("delete from OBS_STAMISSING where datatime=? and checktime=?", new Object[]{datatime, checktime});
	}

}
