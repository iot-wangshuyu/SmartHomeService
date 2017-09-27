package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.delta.bean.Application;
import com.delta.bean.InstallApplicationDevice;
import com.delta.bean.QrCodeInfo;
import com.delta.dao.InstallApplicationDeviceDao;

public class InstallApplicationDeviceDaoImpl implements InstallApplicationDeviceDao{
	private static Logger logger = Logger.getLogger(InstallApplicationDeviceDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insert(InstallApplicationDevice installApplicationDevice) {
		String sql = "insert into t_install_application_device(user_id,installApplication_id,device_ID,bindTime) values(?,?,?,?)";
		Object[] params = {installApplicationDevice.getUser_id(),installApplicationDevice.getInstallApplication_id(),installApplicationDevice.getDevice_ID(),installApplicationDevice.getBindTime()};
		try {
			jdbcTemplate.update(sql, params);
			return false;			
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
	}

	@Override
	public boolean delete(Integer id) {
		String sql = "delete from  t_install_application_device where id=?";
		Object[] params = {id};
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {					
			logger.info(e.toString());
			return true;
		}		
	}

	@Override
	public boolean update(InstallApplicationDevice installApplicationDevice) {
		String sql = "update t_install_application_device setdevice_ID=? where id=?";
		Object[] params = {installApplicationDevice.getDevice_ID(),installApplicationDevice.getId() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
	}

	@Override
	public List<QrCodeInfo> select(InstallApplicationDevice installApplicationDevice) {
		//TODO  查询语句需要完善
		String sql = "select t_application.* from t_install_application_device,t_application where t_application.id=installApplication_id=? ";
		Object[] params = {installApplicationDevice.getId()};
		try {
			List<QrCodeInfo> address = jdbcTemplate.query(sql,ParameterizedBeanPropertyRowMapper.newInstance(QrCodeInfo.class), params);
			return address;
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}



}
