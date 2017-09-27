package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.delta.bean.Application;
import com.delta.bean.InstallApplication;
import com.delta.dao.InstallApplicationDao;

/**
 * 安装的功能操作
 * @author Shuyu.Wang
 *
 */
public class  InstallApplicationDaoImpl implements  InstallApplicationDao {
	private static Logger logger = Logger.getLogger(InstallApplicationDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert( InstallApplication  installApplication) {
		String sql = "insert into t_install_application (user_id,application_id,application_version,position,speed,description2,setupTime) values(?,?,?,?,?,?,?)";
		Object[] params = { installApplication.getUser_id(),installApplication.getApplication_id(),installApplication.getApplication_version(),installApplication.getPosition(),
				installApplication.getSpeed(),installApplication.getDescription2(),
				installApplication.getSetupTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public boolean delete( InstallApplication  installApplication) {
		String sql = "delete from t_install_application where id=? and user_id=?";
		Object[] params = {installApplication.getId(),installApplication.getUser_id() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {		
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public boolean update( InstallApplication  installApplication) {
		String sql = "update  t_install_application set application_version=?,setupTime=? where id=? and user_id";
		Object[] params = { installApplication.getApplication_version(),installApplication.getSetupTime(),installApplication.getApplication_id()};

		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public boolean updateVersion( InstallApplication  installApplication) {

		String sql = "update  t_install_application set  application_version=?, setupTime=? where id=? and user_id=?";
		Object[] params = {  installApplication.getApplication_version(),  installApplication.getSetupTime(),
				 installApplication.getId(),  installApplication.getUser_id() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			
			logger.info(e.toString());
			return true;
		}

	}

	@Override
	public int selectId(Integer arg) {		
		String sql = "SELECT id FROM t_install_information WHERE user_id=? AND id ORDER BY setupTime DESC LIMIT 1";
		int num = -1;
		try {
			num = jdbcTemplate.queryForInt(sql, arg);
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return num;

	}

	/*列表
	 *  (non-Javadoc)
	 * @see com.delta.dao.InstallInformationDao#select(com.delta.bean.InstallInformation)
	 */
	@Override
	public List<InstallApplication> select( InstallApplication  installApplication) {	
		String sql = "SELECT app.id,ap.name,app.setupTime FROM t_install_application app,t_application ap  WHERE user_id=? AND ap.`id`=app.`application_id`";
		Object[] params = { installApplication.getUser_id() };
		try {
			List<InstallApplication> informationList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(InstallApplication.class), params);
			return informationList;
		} catch (Exception e) {			
			logger.info(e.toString());
			return null;
		}

	}

	@Override
	public  InstallApplication selectVersion( InstallApplication  installApplication) {
		
		String sql = "SELECT * FROM t_install_application WHERE id=?";
		Object[] params = { installApplication.getId() };

		try {
			Object installInformationVersion = jdbcTemplate.queryForObject(sql,
					ParameterizedBeanPropertyRowMapper.newInstance( InstallApplication.class), params);
			return ( InstallApplication) installInformationVersion;
		} catch (Exception e) {
	
			logger.info(e.toString());
			return null;
		}
	}

	/* (non-Javadoc)
	 * 详情
	 * @see com.delta.dao.InstallInformationDao#selectD(java.lang.Integer)
	 */
	@Override
	public  InstallApplication selectD(Integer arg) {
		String sql = "SELECT * FROM t_install_application WHERE  id=?";
		Object[] params = {arg};

		try {
			Object  installApplication = jdbcTemplate.queryForObject(sql,
					ParameterizedBeanPropertyRowMapper.newInstance( InstallApplication.class), params);
			return ( InstallApplication) installApplication;
		} catch (Exception e) {
		
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public InstallApplication selectId() {
		String sql = "SELECT * FROM t_application WHERE  id ORDER BY buildTime DESC LIMIT 1";
		try {
			Object applicationid = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(InstallApplication.class));
			return (InstallApplication) applicationid;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
