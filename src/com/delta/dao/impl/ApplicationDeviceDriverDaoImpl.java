package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.delta.bean.ApplicationDeviceDriver;
import com.delta.bean.DeviceDriver;
import com.delta.dao.ApplicationDeviceDriverDao;

/**
 * 应用和驱动关联操作
 * @author Shuyu.Wang
 *
 */
public class ApplicationDeviceDriverDaoImpl implements ApplicationDeviceDriverDao{
	private static Logger logger = Logger.getLogger( ApplicationDeviceDriverDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(ApplicationDeviceDriver applicationDeviceDriver) {	
		String sql = "insert into t_application_driver (application_id,driver_id,buildTime) values(?,?,?)";
		Object[] params = { applicationDeviceDriver.getApplication_id(), applicationDeviceDriver.getDriver_id(),applicationDeviceDriver.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {		
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean delete(ApplicationDeviceDriver applicationDeviceDriver) {
		String sql = "delete from t_application_driver where application_id=?";
		Object[] params = { applicationDeviceDriver.getApplication_id() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {				
			logger.info(e.toString());
			return true;
		}		
		
	}

	@Override
	public boolean update(ApplicationDeviceDriver applicationDeviceDriver) {	
		String sql = "update  t_application_driver set application_id=?,driver_id=?,buildTime=? where id=?";
		Object[] params = { applicationDeviceDriver.getApplication_id(), applicationDeviceDriver.getDriver_id(),applicationDeviceDriver.getBuildTime() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
		    return true;
		}
		
	}

	@Override
	public List<ApplicationDeviceDriver> select(ApplicationDeviceDriver applicationDeviceDriver) {		
		String sql = "select * from t_application_driver where driver_id=? ";
		Object[] params = {applicationDeviceDriver.getDriver_id()};
		try {
			 List<ApplicationDeviceDriver> applicationDeviceDriverList=jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ApplicationDeviceDriver.class),params);			
			return  applicationDeviceDriverList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	/* (non-Javadoc)
	 * 根据应用id查询记录
	 * @see com.delta.dao.ApplicationDeviceDriverDao#selectByA(com.delta.bean.ApplicationDeviceDriver)
	 */
	@Override
	public List<DeviceDriver> selectByA(Integer arg){ 		
		String sql = "SELECT d.name FROM t_application_driver ad,t_device_driver d WHERE ad.application_id=? AND d.`id`=ad.`driver_id` ";
		Object[] params = {arg};
		try {
			List<DeviceDriver>  applicationDevice=jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class),params);			
			return  applicationDevice;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
