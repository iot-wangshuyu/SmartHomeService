package com.delta.bean;

import java.sql.Timestamp;
/**
 * 应用推荐的安装位置表
 * @author Shuyu.Wang
 *
 */
public class ApplicationPosition {
	public Integer id;
	public Integer application_id;
	public String position;
	public Timestamp buildTime;
	public ApplicationPosition() {
		super();
	}
	public ApplicationPosition(Integer application_id, String position, Timestamp buildTime) {
		super();
		this.application_id = application_id;
		this.position = position;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Timestamp getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}
	
	

	
	

}
