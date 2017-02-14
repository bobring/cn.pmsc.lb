package spring_jdbc.obs.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring_jdbc.obs.bean.Sta;


public class StaRowMapper implements RowMapper<Sta> {
	
	public Sta mapRow(ResultSet rs, int index) throws SQLException {
		Sta sta = new Sta();
		sta.setDatatime(rs.getTimestamp("datatime"));
		sta.setchecktime(rs.getTimestamp("checktime"));
		sta.setList(rs.getString("list"));
		sta.setCount(rs.getLong("count"));
		return sta;
	}
}
