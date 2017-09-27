package com.delta.bean;

import java.sql.Timestamp;

/**
 * 查找应用功能
 * @author Shuyu.Wang
 *
 */
public class FindApplicationFunction {
	public Integer id;
    public Integer application_id;
    public String name;
    public String describes;
    public String functions;
    public String version;
    public Timestamp buildTime;
	public FindApplicationFunction() {
		super();
	}
	
	public FindApplicationFunction(Integer id, Integer application_id, String name, String describes, String functions,
			String version, Timestamp buildTime) {
		super();
		this.id = id;
		this.application_id = application_id;
		this.name = name;
		this.describes = describes;
		this.functions = functions;
		this.version = version;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Timestamp getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}

	@Override
	public String toString() {
		return "{'id'=" + id + ", 'application_id'='" + application_id + "', 'functions'='" + functions
				+ "', 'version'='" + version + "', 'buildTime'='" + buildTime
				+ "'}";
	}

}
