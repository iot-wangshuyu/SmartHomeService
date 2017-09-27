package com.delta.bean;

import java.sql.Timestamp;

/**
 * 应用所对应的驱动表
 * @author Shuyu.Wang
 *
 */
public class ApplicationDeviceDriver {
	public Integer id;
	public Integer application_id;
	public Integer driver_id;
	public Timestamp buildTime;
	public ApplicationDeviceDriver() {
		super();
	}
	public ApplicationDeviceDriver(Integer application_id, Integer driver_id, Timestamp buildTime) {
		super();
		this.application_id = application_id;
		this.driver_id = driver_id;
		this.buildTime = buildTime;
	}
	
	public ApplicationDeviceDriver(Integer id, Integer application_id, Integer driver_id, Timestamp buildTime) {
		super();
		this.id = id;
		this.application_id = application_id;
		this.driver_id = driver_id;
		this.buildTime = buildTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getApplication_id() {
		return application_id;
	}
	public void setApplication_id(Integer application_id) {
		this.application_id = application_id;
	}
	public Integer getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}
	public Timestamp getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}
	
	

}
