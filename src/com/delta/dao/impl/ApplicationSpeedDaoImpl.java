package com.delta.dao.impl;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.delta.bean.ApplicationSpeed;
import com.delta.dao.ApplicationSpeedDao;


/**
 * 应用描述一
 * @author Shuyu.Wang
 *
 */
public class ApplicationSpeedDaoImpl implements ApplicationSpeedDao {
	private static Logger logger = Logger.getLogger(ApplicationSpeedDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(ApplicationSpeed applicationSpeed) {
		String sql = "insert into t_application_speed (application_id,speed,buildTime) values(?,?,?)";
		Object[] params = {applicationSpeed.getApplication_id(), applicationSpeed.getSpeed(),applicationSpeed.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
				logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean delete(ApplicationSpeed applicationSpeed) {	
		String sql = "delete from t_application_description where id=?";
		Object[] params = {applicationSpeed.getId() };
		
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean update(ApplicationSpeed applicationSpeed) {
		String sql = "update  t_application_speed set application_id=?,speed=?,buildTime=? where id=?";
		Object[] params = {applicationSpeed.getApplication_id(),applicationSpeed.getSpeed(),applicationSpeed.getBuildTime() };			
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {		
			logger.info(e.toString());
			return true;
		}
		
	}
	@Override
	public List<ApplicationSpeed> selects(String params) {		
		String[] arr = params.split(",");
		List<String> list = Arrays.asList(arr);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("application_id", list);
		StringBuffer buf = new StringBuffer(
				"select  DISTINCT speed from t_application_speed where application_id in (");
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				buf.append(list.get(i));
				
			} else {				
				buf.append(", ");
				buf.append(String.valueOf(list.get(i)));
			}

		}
		buf.append(")");
		try {
			List<ApplicationSpeed> descriptionList = jdbcTemplate.query(buf.toString(),
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationSpeed.class));
			return descriptionList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}

	}

	@Override
	public List<ApplicationSpeed> selectId(ApplicationSpeed applicationSpeed) {
		String sql = "SELECT DISTINCT application_id FROM t_application_speed WHERE speed=?;";
		Object[] params = {applicationSpeed.getSpeed()};
		try {
			List<ApplicationSpeed> idList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationSpeed.class), params);
			return idList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
