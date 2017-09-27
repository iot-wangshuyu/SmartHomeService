package com.delta.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.ApplicationPosition;
import com.delta.dao.ApplicationPositionDao;

public class ApplicationPositionDaoImpl implements ApplicationPositionDao {
	private static Logger logger = Logger.getLogger(ApplicationPositionDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(ApplicationPosition applicationPosition) {
		String sql = "insert into t_application_position (application_id,position,buildTime) values(?,?,?)";
		Object[] params = { applicationPosition.getApplication_id(), applicationPosition.getPosition(),
				applicationPosition.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public boolean delete(ApplicationPosition applicationPosition) {
		String sql = "delete from t_application_position where id=?";
		Object[] params = { applicationPosition.getId() };

		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public boolean update(ApplicationPosition applicationPosition) {
		String sql = "update  t_application_position set application_id=?,position=?,buildTime=? where id=?";
		Object[] params = { applicationPosition.getId(), applicationPosition.getApplication_id(),
				applicationPosition.getPosition(), applicationPosition.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public List<ApplicationPosition> selects(String arg) {
		String[] arr = arg.split(",");
		List<String> list = Arrays.asList(arr);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("application_id", list);
		StringBuffer buf = new StringBuffer(
				"select DISTINCT position from t_application_position where application_id in (");
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
			List<ApplicationPosition> applicationPositionList = jdbcTemplate.query(buf.toString(),
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationPosition.class));
			return applicationPositionList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public List<ApplicationPosition> selectId(ApplicationPosition applicationPosition) {
		String sql = "SELECT DISTINCT application_id FROM t_application_position WHERE position=?;";
		Object[] params = { applicationPosition.getPosition() };
		try {
			List<ApplicationPosition> idList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationPosition.class), params);
			return idList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public List<ApplicationPosition> selectByID(Integer arg) {
		String sql = "SELECT position FROM t_application_position WHERE application_id=?;";
		Object[] params = { arg };
		try {
			List<ApplicationPosition> positionList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationPosition.class), params);
			return positionList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
