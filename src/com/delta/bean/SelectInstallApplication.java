package com.delta.bean;

/**
 * 查找已安装的应用
 * @author Shuyu.Wang
 *
 */
public class SelectInstallApplication {
	public Integer id;
	public Integer function_id;
	public Integer application_id;
	public String position;
	public String description;
	public String description2;
	public String name;
	public String functions;
	public SelectInstallApplication() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFunction_id() {
		return function_id;
	}

	public void setFunction_id(Integer function_id) {
		this.function_id = function_id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public SelectInstallApplication(Integer id, Integer function_id, Integer application_id, String position,
			String description, String description2, String name, String functions) {
		super();
		this.id = id;
		this.function_id = function_id;
		this.application_id = application_id;
		this.position = position;
		this.description = description;
		this.description2 = description2;
		this.name = name;
		this.functions = functions;
	}

	@Override
	public String toString() {
		return "{'id'=" + id + ", 'name'='" + name + "', 'functions'='" + functions + "'}";
	}
	

}
