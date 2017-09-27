package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.InstallDeviceDriver;
import com.delta.bean.SelectInstallDeviceDriver;
import com.delta.dao.InstallDeviceDriverDao;

/**
 * 已安装的设备驱动操作
 * @author Shuyu.Wang
 *
 */
public class InstallDeviceDriverDaoImpl implements InstallDeviceDriverDao{
	private static Logger logger = Logger.getLogger(InstallDeviceDriverDaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(InstallDeviceDriver installDeviceDriver) {		
		String sql="insert into t_install_device_driver (user_id,driver_id,driver_version,installTime) values(?,?,?,?)";
		Object[] params={installDeviceDriver.getUser_id(),installDeviceDriver.getDriver_id(),installDeviceDriver.getDriver_version(),installDeviceDriver.getInstallTime()};
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean update(InstallDeviceDriver installDeviceDriver) {
        String sql = "update  t_install_device_driver set driver_version=?,installTime=? where user_id=? and driver_id=?";
		Object[] params = {installDeviceDriver.getDriver_version(),installDeviceDriver.getInstallTime(),installDeviceDriver.getUser_id(),installDeviceDriver.getDriver_id()};
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {
			logger.info(e.toString());
			return true;
		}
		
	}

	@Override
	public boolean delete(InstallDeviceDriver installDeviceDriver) {
        String sql = "delete from t_install_device_driver where id=? and user_id=?";
		Object[] params = {installDeviceDriver.getId(),installDeviceDriver.getUser_id() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}		
		
	}
	
	@Override
	public List<InstallDeviceDriver> selectBy(InstallDeviceDriver installDeviceDriver){
		
		String sql = "SELECT * FROM t_install_device_driver WHERE user_id=? and driver_id=?";
		Object[] params={installDeviceDriver.getUser_id(),installDeviceDriver.getDriver_id()};
		try {
			List<InstallDeviceDriver> installDevice= jdbcTemplate.query(sql,ParameterizedBeanPropertyRowMapper.newInstance(InstallDeviceDriver.class),params);
			return  installDevice;
		} catch (Exception e) {			
			logger.info(e.toString());
			return null;
		}
		
		
	}

	@Override
	public List<SelectInstallDeviceDriver> select(InstallDeviceDriver installDeviceDriver) {
		String sql = "SELECT ind.`id`,ind.`driver_id`,d.`name`,d.`deviceName`,d.`deviceVendor`,d.`deviceType` FROM t_install_device_driver ind,t_device_driver d WHERE user_id=? AND d.id=ind.`driver_id` ";
		Object[] params={installDeviceDriver.getUser_id()};		
		try {
			List<SelectInstallDeviceDriver> installDevice = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(SelectInstallDeviceDriver.class),params);
			return  installDevice;
		} catch (Exception e) {	
			logger.info(e.toString());
			return null;
		}
	}
	@Override
	public int selectid(InstallDeviceDriver installDeviceDriver){
		String sql = "SELECT id FROM t_install_device_driver WHERE user_id=? and driver_id=?";
		Object[] params={installDeviceDriver.getUser_id(),installDeviceDriver.getDriver_id()};
		int num=-1;
		try {
			return	num= jdbcTemplate.queryForInt(sql,params);
		} catch (Exception e) {
			logger.info(e.toString());
		}		
		return num;
	}
	@Override
	public InstallDeviceDriver selectVersion(InstallDeviceDriver installDeviceDriver){
		String sql = "SELECT * FROM t_install_device_driver WHERE id=?";
		Object[] params={installDeviceDriver.getId()};
		
		try {
			Object installDevice = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(InstallDeviceDriver.class),params);
			return (InstallDeviceDriver) installDevice;
		} catch (Exception e) {		
			logger.info(e.toString());
			return null;
		}
	}
	@Override
	public List<InstallDeviceDriver> findAll() {		
		return null;
	}

	@Override
	public SelectInstallDeviceDriver selectD(Integer arg) {		
		String sql = "SELECT id.`id`,id.`driver_version`,id.`installTime`,d.`name`, d.`author`,d.`deviceProtocol`,d.`deviceType`,d.`deviceVendor` FROM t_install_device_driver id,t_device_driver d WHERE id.id=? AND d.`id`=id.`driver_id`";
		Object[] params={arg};
		try {
			Object installDeviceDriver= jdbcTemplate.queryForObject(sql,ParameterizedBeanPropertyRowMapper.newInstance(SelectInstallDeviceDriver.class),params);
			return (SelectInstallDeviceDriver) installDeviceDriver;
		} catch (Exception e) {			
			logger.info(e.toString());
			return null;
		}
		
	}

}
