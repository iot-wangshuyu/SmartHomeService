package com.delta.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.ApplicationDescription2;
import com.delta.dao.ApplicationDescriptionDao2;
 
/**
 * 应用描述2
 * @author Shuyu.Wang
 *
 */
public class ApplicationDescriptionDaoImpl2 implements ApplicationDescriptionDao2 {
	private static Logger logger = Logger.getLogger(ApplicationDescriptionDaoImpl2.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(ApplicationDescription2 applicationDescription) {
		String sql = "insert into t_application_description2 (application_id,description,buildTime) values(?,?,?)";
		Object[] params = {applicationDescription.getApplication_id(), applicationDescription.getDescription(),applicationDescription.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean delete(ApplicationDescription2 applicationDescription) {	
		String sql = "delete from t_application_description2 where id=?";
		Object[] params = { applicationDescription.getId() };
		
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {		
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean update(ApplicationDescription2 applicationDescription) {
		String sql = "update  t_application_description2 set application_id=?,description=?,buildTime=? where id=?";
		Object[] params = {applicationDescription.getApplication_id(), applicationDescription.getDescription(),applicationDescription.getBuildTime() };			
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public List<Map<String, Object>> select(String arg) {		
		String[] arr = arg.split(",");
		List<String> list = Arrays.asList(arr);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
		String sql = "SELECT DISTINCT description FROM t_application_description2 WHERE application_id IN(application_id);";
		// Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("application_id", list);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("application_id", list);
		try {
			List<Map<String, Object>> descriptionList = namedParameterJdbcTemplate.queryForList(sql, parameters);
			return descriptionList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}

	}

	@Override
	public List<ApplicationDescription2> selects(String params) {		
		String[] arr = params.split(",");
		List<String> list = Arrays.asList(arr);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("application_id", list);
		StringBuffer buf = new StringBuffer(
				"select DISTINCT description from t_application_description2 where application_id in (");
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
			List<ApplicationDescription2> descriptionList = jdbcTemplate.query(buf.toString(),
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationDescription2.class));
			return descriptionList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}

	}

	@Override
	public List<ApplicationDescription2> selectId(ApplicationDescription2 applicationDescription) {		
		String sql = "SELECT DISTINCT application_id FROM t_application_description2 WHERE description=?;";
		Object[] params = { applicationDescription.getDescription() };
		try {
			List<ApplicationDescription2> idList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationDescription2.class), params);
			return idList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
