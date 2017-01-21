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
	public void delete(String id, Date date) {
//		jdbcTemplate.update("delete from person where id=?", new Object[]{id},
//				new int[]{java.sql.Types.INTEGER});
		jdbcTemplate.update("delete from obs where id=? and date=?", new Object[]{id, date});
	}

	@Override
	public void save(Obs obs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Obs obs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Obs getObs(String id, Date date) {
		// TODO Auto-generated method stub
		return (Obs)jdbcTemplate.query("select * from obs where id=? and date=?", 
				new Object[]{id, date}, new ObsRowMapper());
	}

	@Override
	public List<Obs> getObss(Date date) {
		// TODO Auto-generated method stub
		return (List<Obs>)jdbcTemplate.query("select * from obs where date=?", 
				new Object[]{date}, new ObsRowMapper());
	}

	@Override
	public List<Obs> getAllObss() {
		// TODO Auto-generated method stub
		return (List<Obs>)jdbcTemplate.query("select * from obs", new ObsRowMapper());
	}
}
