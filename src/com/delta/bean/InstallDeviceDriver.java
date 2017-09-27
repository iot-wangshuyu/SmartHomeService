package com.delta.bean;

import java.sql.Timestamp;

/**
 * 以安装的设备驱动
 * @author Shuyu.Wang
 *
 */
public class InstallDeviceDriver {
	public Integer id;
	public Integer user_id;
	public Integer driver_id;
	public String driver_version;
	public Timestamp installTime;
	public InstallDeviceDriver() {
		super();
	}
	public InstallDeviceDriver(Integer id, Integer user_id, Integer driver_id, String driver_version,
			Timestamp installTime) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.driver_id = driver_id;
		this.driver_version = driver_version;
		this.installTime = installTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}
	public String getDriver_version() {
		return driver_version;
	}
	public void setDriver_version(String driver_version) {
		this.driver_version = driver_version;
	}
	public Timestamp getInstallTime() {
		return installTime;
	}
	public void setInstallTime(Timestamp installTime) {
		this.installTime = installTime;
	}
	@Override
	public String toString() {
		return "{'id'=" + id + ", 'user_id'='" + user_id + "', 'driver_id'='" + driver_id
				+ "', 'driver_version'='" + driver_version + "', 'installTime'='" + installTime + "'}";
	}
	
}
