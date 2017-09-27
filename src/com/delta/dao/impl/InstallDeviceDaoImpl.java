package com.delta.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.delta.bean.QrCodeInfo;
import com.delta.dao.InstallDeviceDao;

/**
 * 已安装的设备操作
 * @author Shuyu.Wang
 *
 */
public class InstallDeviceDaoImpl implements InstallDeviceDao{
	private static Logger logger = Logger.getLogger(InstallDeviceDaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public boolean insert(QrCodeInfo qrCodeInfo) {	
		String sql="insert into t_install_device(user_id,device_ID,device_NAME,version,describes,mac,vendor,type,protocol,installTime) values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={qrCodeInfo.getUser_id(),qrCodeInfo.getDevice_ID(),qrCodeInfo.getDevice_NAME(),qrCodeInfo.getVersion(),qrCodeInfo.getDescribes(),qrCodeInfo.getMac(),qrCodeInfo.getVendor(),qrCodeInfo.getType(),qrCodeInfo.getProtocol(),qrCodeInfo.getInstallTime()};
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
	}

	@Override
	public boolean update(QrCodeInfo qrCodeInfo) {		
		String sql = "update  t_install_device set driver_version=?,installTime=? where user_id=? and driver_id=?";
		Object[] params={qrCodeInfo.getUser_id(),qrCodeInfo.getMac(),qrCodeInfo.getVendor(),qrCodeInfo.getType(),qrCodeInfo.getProtocol(),qrCodeInfo.getInstallTime()};
			try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {			
			logger.info(e.toString());
			return true;
		}
	}

	@Override
	public boolean delete(QrCodeInfo qrCodeInfo) {		
		String sql = "delete from t_install_device where id=? and user_id=?";
		Object[] params = {qrCodeInfo.getId(),qrCodeInfo.getUser_id() };
		try {
			jdbcTemplate.update(sql, params);
			return false;
		} catch (Exception e) {					
			logger.info(e.toString());
			return true;
		}		
	}

	@Override
	public List<QrCodeInfo> selectBy(QrCodeInfo qrCodeInfo) {	
		String sql = "SELECT * FROM t_install_device WHERE device_ID=? and user_id=?";
		Object[] params={qrCodeInfo.getDevice_ID(),qrCodeInfo.getUser_id()};
		try {
			List<QrCodeInfo> installDevice= jdbcTemplate.query(sql,ParameterizedBeanPropertyRowMapper.newInstance(QrCodeInfo .class),params);
			return  installDevice;
		} catch (Exception e) {			
			logger.info(e.toString());
			return null;
		}
		
	}

	@Override
	public List<QrCodeInfo> select(QrCodeInfo qrCodeInfo) {		
		String sql = "SELECT * FROM t_install_device WHERE user_id=?";
		Object[] params={qrCodeInfo.getUser_id()};
		
		try {
			List<QrCodeInfo> installDevice = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(QrCodeInfo.class),params);
			return  installDevice;
		} catch (Exception e) {			
			logger.info(e.toString());
			return null;
		}
	}

	@Override
	public QrCodeInfo selectD(Integer arg) {		
		String sql = "SELECT * FROM t_install_device WHERE id=?";
		Object[] params={arg};
		try {
			Object installDevice= jdbcTemplate.queryForObject(sql,ParameterizedBeanPropertyRowMapper.newInstance(QrCodeInfo .class),params);
			return (QrCodeInfo ) installDevice;
		} catch (Exception e) {			
			logger.info(e.toString());
			return null;
		}
	}

}
