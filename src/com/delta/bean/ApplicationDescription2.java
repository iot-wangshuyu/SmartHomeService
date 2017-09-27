package com.delta.bean;

import java.sql.Timestamp;

public class ApplicationDescription2 {
	public Integer id;
	public String application_id;
	public String description;
	public Timestamp buildTime;
	public ApplicationDescription2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApplicationDescription2(Integer id, String application_id, String description, Timestamp buildTime) {
		super();
		this.id = id;
		this.application_id = application_id;
		this.description = description;
		this.buildTime = buildTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApplication_id() {
		return application_id;
	}
	public void setApplication_id(String application_id) {
		this.application_id = application_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}
	

}
