package spring_jdbc.obs.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring_jdbc.obs.bean.Obs;

public class ObsRowMapper implements RowMapper<Obs>{
	
	public Obs mapRow(ResultSet rs, int index) throws SQLException {
		Obs obs = new Obs();
		obs.setId(rs.getString("id"));
		obs.setDate(rs.getDate("dtime"));
		obs.setStatistics(rs.getBytes("statistics"));
		return obs;
	}
}
