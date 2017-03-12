package spring_jdbc.obs.impl;

//import java.sql.Clob;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
//import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.annotation.Transactional;

import spring_jdbc.obs.bean.Sta;
import spring_jdbc.obs.service.StaService;

//声明事务,这样就会自动的打开事务和关闭事务
@Transactional
public class StaServiceBean implements StaService {
	
	private JdbcTemplate jdbcTemplate;
	final LobHandler lobHandler = new  DefaultLobHandler();
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int insert(final Sta sta) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("insert into OBS_STA_RECORDS(datatime, checktime, count, list) values(?, ?, ?, ?)",
				new Object[] { sta.getDatatime(), sta.getchecktime(), sta.getCount(),
						new SqlLobValue(sta.getList(), lobHandler) },
				new int[] { Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT, Types.CLOB }
//				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
//					@Override
//					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
//						ps.setTimestamp(1, sta.getDatatime());
//						ps.setTimestamp(2, sta.getchecktime());
//						ps.setLong(3, sta.getCount());
//						lobCreator.setClobAsString(ps, 4, sta.getList());
//					}}
		);
	}

	@Override
	public int update(final Sta sta) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("update OBS_STA_RECORDS set list=?,count=? where checktime=? and datatime=?",
				new Object[] { new SqlLobValue(sta.getList(), lobHandler), 
						sta.getCount(),sta.getchecktime(),sta.getDatatime() 
						 },
				new int[] { Types.CLOB, Types.BIGINT, Types.TIMESTAMP, Types.TIMESTAMP }
//				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
//					@Override
//					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
//						lobCreator.setClobAsString(ps, 1, sta.getList());
//						ps.setLong(2, sta.getCount());
//						ps.setTimestamp(3, sta.getchecktime());
//						ps.setTimestamp(4, sta.getDatatime());
//					}}
				);
	}

	@Override
	public List<Sta> getSta(Timestamp timestamp, Timestamp timestamp2) {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STA_RECORDS where datatime=? and checktime=?", 
				new Object[]{timestamp, timestamp2}, new StaRowMapper());
	}

	@Override
	public List<Sta> getStas(Timestamp datatime) {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STA_RECORDS where datatime=?", 
				new Object[]{datatime}, new StaRowMapper());
	}

	@Override
	public List<Sta> getStas(Timestamp begintime, Timestamp endtime) {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STA_RECORDS where datatime between ? and ?", 
				new Object[]{begintime, endtime}, new StaRowMapper());
	}

	@Override
	public List<Sta> getAllStas() {
		// TODO Auto-generated method stub
		return (List<Sta>)jdbcTemplate.query("select * from OBS_STA_RECORDS", new StaRowMapper());
	}

	@Override
	public int delete(Timestamp datatime, Timestamp checktime) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("delete from OBS_STA_RECORDS where datatime=? and checktime=?", new Object[]{datatime, checktime});
	}

}
