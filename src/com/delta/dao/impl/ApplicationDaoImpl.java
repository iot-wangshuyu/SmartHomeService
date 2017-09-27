package com.delta.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.Application;
import com.delta.bean.ApplicationUnion;
import com.delta.dao.ApplicationDao;

/**
 * 应用数据库操作
 * @author Shuyu.Wang
 *
 */
public class ApplicationDaoImpl implements ApplicationDao{
	private static Logger logger = Logger.getLogger( ApplicationDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean insert(Application application) {	
		String sql = "insert into t_application (name,describes,author,fileName,version,image,address,buildTime) values(?,?,?,?,?,?,?,?)";
		Object[] params = { application.getName(), application.getDescribes(),application.getAuthor(),application.getFileName(),application.getVersion(),application.getImage(),application.getAddress(),application.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;			
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean delete(Application application) {	
		String sql = "delete from t_application where id=?";
		Object[] params = { application.getId() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {					
			logger.info(e.toString());
			return true;
		}		
		
	}

	@Override
	public boolean update(Application application) {
		String sql = "update  t_application set fileName=?,version=?,address=?,buildTime=? where id=?";
		Object[] params = { application.getFileName(),application.getVersion(), application.getAddress(),application.getBuildTime(),application.getId() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public Application select(Application application) {
		String sql = "select * from t_application where name=?";
		Object[] params = {application.getName()};
		try {
			Object applicationid = jdbcTemplate.queryForObject(sql,ParameterizedBeanPropertyRowMapper.newInstance(Application.class),params);
			return (Application) applicationid;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public Application selectId() {
		String sql = "SELECT * FROM t_application WHERE  id ORDER BY buildTime DESC LIMIT 1";
		try {
			Object applicationid = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(Application.class));
			return (Application) applicationid;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}
	
	@Override
	public List<ApplicationUnion> findAll() {
	
		String sql = "SELECT app.id,app.name,app.describes,app.author,app.version,asp.speed,app.buildTime FROM t_application app,t_application_speed asp WHERE  asp.application_id=app.id ";		
		try {
			List<ApplicationUnion> applicationList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(ApplicationUnion.class));
			return applicationList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public List<Application> selectadd(String arg) {
		String sql = "select * from t_application where name=? ";
		Object[] params = {arg};
		try {
			List<Application> address = jdbcTemplate.query(sql,ParameterizedBeanPropertyRowMapper.newInstance(Application.class), params);
			return address;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public List<Application> selectName(String arg) {
		String[] arr = arg.split(",");
		List<String> list = Arrays.asList(arr);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("application_id", list);
		StringBuffer buf = new StringBuffer(
				"select name from t_application where id in (");
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
			
			List<Application> applicationPositionList = jdbcTemplate.query(buf.toString(),
					ParameterizedBeanPropertyRowMapper.newInstance(Application.class));
			return applicationPositionList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}
	@Override
	public Application selectVersion(Application application) {
		String sql = "SELECT * FROM t_application WHERE  id=?";
		Object[] params={application.getId()};
		
		try {
			Object applicationVersion = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(Application.class),params);
			return (Application) applicationVersion;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
