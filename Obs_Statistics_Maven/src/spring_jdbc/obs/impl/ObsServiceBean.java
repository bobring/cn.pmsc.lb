package spring_jdbc.obs.impl;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import spring_jdbc.obs.bean.*;
import spring_jdbc.obs.service.*;

//声明事务,这样就会自动的打开事务和关闭事务
@Transactional
public class ObsServiceBean implements ObsService {
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int delete(String id, Date date) {
//		jdbcTemplate.update("delete from person where id=?", new Object[]{id},
//				new int[]{java.sql.Types.INTEGER});
		return jdbcTemplate.update("delete from OBS_STATISTICS where id=? and dtime=?", new Object[]{id, date});
	}

	@Override
	public int insert(Obs obs) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("insert into OBS_STATISTICS(id, dtime, statistics) value(?, ?, ?)", 
				new Object[]{obs.getId(), obs.getDate(), obs.getStatistics()});
	}

	@Override
	public int update(Obs obs) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update OBS_STATISTICS set statistics=? where id=? and dtime=?", 
				new Object[]{obs.getStatistics(), obs.getId(), obs.getDate()});
	}

	@Override
	public Obs getObs(String id, Date date) {
		// TODO Auto-generated method stub
		return (Obs)jdbcTemplate.query("select * from OBS_STATISTICS where id=? and dtime=?", 
				new Object[]{id, date}, new ObsRowMapper());
	}

	@Override
	public List<Obs> getObss(Date date) {
		// TODO Auto-generated method stub
		return (List<Obs>)jdbcTemplate.query("select * from OBS_STATISTICS where dtime=?", 
				new Object[]{date}, new ObsRowMapper());
	}

	@Override
	public List<Obs> getAllObss() {
		// TODO Auto-generated method stub
		return (List<Obs>)jdbcTemplate.query("select * from OBS_STATISTICS", new ObsRowMapper());
	}

	@Override
	public List<Obs> getObss(String id) {
		// TODO Auto-generated method stub
		return (List<Obs>)jdbcTemplate.query("select * from OBS_STATISTICS where id=?", 
				new Object[]{id}, new ObsRowMapper());
	}
}
