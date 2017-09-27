package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import com.delta.bean.Access;
import com.delta.dao.AccessDao;

/**
 * 用户访问次数操作
 * @author Shuyu.Wang
 *
 */
public class AccessDaoImpl implements AccessDao{
	private static Logger logger = Logger.getLogger(AccessDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(Integer arg) {		
		boolean flag = false;
		String sql = "insert into t_access (user_id) values(?)";	
		try {
			jdbcTemplate.update(sql, arg);
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return flag;
	}

	@Override
	public boolean delete(Integer arg) {		
		String sql="delete from t_access where user_id=?";		
		try {
			jdbcTemplate.update(sql,arg);
			return false;			
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
	}

	@Override
	public boolean update(Integer arg) {		
		return false;
	}

	@Override
	public int select(Integer arg) {		
		String sql=" select count(*) from t_access where user_id=?";		
		return jdbcTemplate.queryForInt(sql, arg);		
	}

	@Override
	public List<Access> findAll() {		
		return null;
	}

}
