package com.delta.bean;

import java.sql.Timestamp;

/**
 * 已安装的应用功能
 * @author Shuyu.Wang
 *
 */
public class InstallApplication {
	public Integer id;
	public Integer user_id;
	public String name;
	public Integer application_id;
	public String application_version;
	public String position;
	public String speed;
	public String description2;	
	public Timestamp setupTime;
	
	public InstallApplication() {
		super();
		
	}

	
	public InstallApplication(Integer id, Integer user_id, String name, Integer application_id,
			String application_version, String position, String speed, String description2, Timestamp setupTime) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.application_id = application_id;
		this.application_version = application_version;
		this.position = position;
		this.speed = speed;
		this.description2 = description2;
		this.setupTime = setupTime;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getApplication_id() {
		return application_id;
	}


	public void setApplication_id(Integer application_id) {
		this.application_id = application_id;
	}


	public String getApplication_version() {
		return application_version;
	}


	public void setApplication_version(String application_version) {
		this.application_version = application_version;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getSpeed() {
		return speed;
	}


	public void setSpeed(String speed) {
		this.speed = speed;
	}


	public String getDescription2() {
		return description2;
	}


	public void setDescription2(String description2) {
		this.description2 = description2;
	}


	public Timestamp getSetupTime() {
		return setupTime;
	}


	public void setSetupTime(Timestamp setupTime) {
		this.setupTime = setupTime;
	}


	@Override
	public String toString() {
		return "{'id'=" + id + ", 'name'='" +name + "',   'setupTime'='" + setupTime + "'}";
	}
	


}
