package com.delta.bean;

import java.sql.Timestamp;

/**
 * 安装的应用绑定的设备
 * @author Shuyu.Wang
 *
 */
public class InstallApplicationDevice {
	public Integer id;
	public Integer user_id;
	public Integer installApplication_id;
	public String device_ID;
	public Timestamp bindTime;
	public InstallApplicationDevice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InstallApplicationDevice(Integer user_id, Integer installApplication_id, String device_ID,
			Timestamp bindTime) {
		super();
		this.user_id = user_id;
		this.installApplication_id = installApplication_id;
		this.device_ID = device_ID;
		this.bindTime = bindTime;
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
	public Integer getInstallApplication_id() {
		return installApplication_id;
	}
	public void setInstallApplication_id(Integer installApplication_id) {
		this.installApplication_id = installApplication_id;
	}
	public String getDevice_ID() {
		return device_ID;
	}
	public void setDevice_ID(String device_ID) {
		this.device_ID = device_ID;
	}
	public Timestamp getBindTime() {
		return bindTime;
	}
	public void setBindTime(Timestamp bindTime) {
		this.bindTime = bindTime;
	}
	

}
