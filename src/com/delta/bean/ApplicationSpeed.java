package com.delta.bean;

import java.sql.Timestamp;

/**
 * 设备响应速度
 * @author Shuyu.Wang
 *
 */
public class ApplicationSpeed {
	public Integer id;
	public Integer application_id;
	public String speed;
	public Timestamp buildTime;
	public ApplicationSpeed() {
		super();
		
	}
	public ApplicationSpeed(Integer id, Integer application_id, String speed, Timestamp buildTime) {
		super();
		this.id = id;
		this.application_id = application_id;
		this.speed = speed;
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
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public Timestamp getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}

	

}
