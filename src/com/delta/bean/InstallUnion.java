package com.delta.bean;

public class InstallUnion {
	public Integer id;
	public Integer user_id;
	public Integer driver_id;
	public String position;
	public String function;
	public String name;
	public InstallUnion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InstallUnion(Integer id, Integer user_id, Integer driver_id, String position, String function, String name) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.driver_id = driver_id;
		this.position = position;
		this.function = function;
		this.name = name;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
