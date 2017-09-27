package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.DeviceDriver;
import com.delta.dao.DeviceDriverDao;

/**
 * 设备驱动操作
 * @author Shuyu.Wang
 *
 */
public class DeviceDriverDaoImpl implements DeviceDriverDao {
	private static Logger logger = Logger.getLogger(DeviceDriverDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(DeviceDriver deviceDriver) {	
		String sql = "insert into t_device_driver (name,address,version,updateTime,author,deviceName,deviceVendor,deviceType,deviceProtocol) values(?,?,?,?,?,?,?,?,?)";
		Object[] params = { deviceDriver.getName(), deviceDriver.getAddress(), deviceDriver.getVersion(),
				deviceDriver.getUpdateTime(), deviceDriver.getAuthor(), deviceDriver.getDeviceName(),
				deviceDriver.getDeviceVendor(), deviceDriver.getDeviceType(), deviceDriver.getDeviceProtocol() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean delete(DeviceDriver deviceDriver) {
		String sql = "delete from t_device_driver where id=?";
		Object[] params = { deviceDriver.getId() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean update(DeviceDriver deviceDriver) {
	    String sql = "update  t_device_driver set address=?,version=?,updateTime=?,deviceName=? where id=?";
		Object[] params = {deviceDriver.getAddress(),
				deviceDriver.getVersion(), deviceDriver.getUpdateTime(),
				deviceDriver.getDeviceName(),deviceDriver.getId() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public List<DeviceDriver> select(DeviceDriver deviceDriver) {
		String sql = "select * from t_device_driver where deviceType=? and deviceVendor=? and deviceProtocol=?";
		Object[] params = { deviceDriver.getDeviceType(), deviceDriver.getDeviceVendor(),
				deviceDriver.getDeviceProtocol() };
		try {
			List<DeviceDriver> uploadFile = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class), params);
			return uploadFile;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}
	
	@Override
	public DeviceDriver selectId(DeviceDriver deviceDriver) {
		String sql = "SELECT * FROM t_device_driver WHERE  name=?";
		Object[] params = {deviceDriver.getName()};
		try {
			Object DeviceDriverid = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class),params);
			return (DeviceDriver) DeviceDriverid;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}
	@Override
	public DeviceDriver selectid(DeviceDriver deviceDriver) {
		String sql = "SELECT * FROM t_device_driver WHERE  deviceType=? and deviceVendor=? and deviceProtocol=?";
		Object[] params = { deviceDriver.getDeviceType(), deviceDriver.getDeviceVendor(),
				deviceDriver.getDeviceProtocol()};
		try {
			Object DeviceDriverid = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class),params);
			return (DeviceDriver) DeviceDriverid;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}
	@Override
	public DeviceDriver selectVersion(DeviceDriver deviceDriver){
		String sql = "SELECT * FROM t_device_driver WHERE  id=?";
		Object[] params={deviceDriver.getId()};
		
		try {
			Object DeviceDriverid = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class),params);
			return (DeviceDriver) DeviceDriverid;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
		
		
	}
	@Override
	public List<DeviceDriver> findAll() {
		String sql = "select * from t_device_driver";		
		try {
			List<DeviceDriver> deviceDriverList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class));
			return deviceDriverList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public List<DeviceDriver> findByUser(Integer arg) {
		String sql = "select * from t_device_driver where ";		
		try {
			List<DeviceDriver> deviceDriverList = jdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(DeviceDriver.class));
			return deviceDriverList;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

}
