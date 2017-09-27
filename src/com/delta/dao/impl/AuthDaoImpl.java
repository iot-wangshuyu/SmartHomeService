package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.User;
import com.delta.dao.UserDao;


/**
 * @author Shuyu.Wang
 *
 */

public class AuthDaoImpl implements UserDao {
	private static Logger logger = Logger.getLogger(AuthDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shuyu.dao.impl.UserDaoImpl#insert(com.shuyu.bean.User)
	 */
	@Override
	public boolean insert(User user) {
		
		String sql = "insert into t_user (userName,passWord,registerTime) values(?,?,?)";
		Object[] params = { user.getUserName(), user.getPassWord(), user.getRegisterTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shuyu.dao.impl.UserDaoImpl#update(com.shuyu.bean.User)
	 */
	@Override
	public boolean update(User user) {
		
		String sql = "update t_user set name=?,pwd=? where id=?";
		Object[] params = { user.getUserName(), user.getPassWord(), user.getId() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shuyu.dao.impl.UserDaoImpl#delete(com.shuyu.bean.User)
	 */
	@Override
	public boolean delete(int id) {
		
		String sql = "delete from t_user where id=?";
		try {
			jdbcTemplate.update(sql, id);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shuyu.dao.impl.UserDaoImpl#selectByName(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User selectByName(User user) {
		String sql = "select * from t_user where userName=? and passWord=?";
		// String sql = "select * from t_user where userName="+
		// user.getUserName()+" and passWord="+user.getPassWord()+"";
		Object[] params = { user.getUserName(), user.getPassWord() };
		try {
			Object user1 = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class),
					params);
			return (User) user1;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shuyu.dao.impl.UserDaoImpl#select(int)
	 */
	@Override
	public List<User> select(User user) {
		String sql = "select * from t_user where userName=?";
		Object[] params = { user.getUserName() };
		try {
			List<User> user1 = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class),
					params);
			return user1;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
