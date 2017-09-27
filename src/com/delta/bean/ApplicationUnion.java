package com.delta.bean;

import java.sql.Timestamp;

public class ApplicationUnion {
	public Integer id;
	public String name;
	public String describes;
	public String author;
	public String version; 
	public String speed;
	public Timestamp buildTime;
	public ApplicationUnion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApplicationUnion(Integer id, String name, String describes, String author, String version, String speed,
			Timestamp buildTime) {
		super();
		this.id = id;
		this.name = name;
		this.describes = describes;
		this.author = author;
		this.version = version;
		this.speed = speed;
		this.buildTime = buildTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	@Override
	public String toString() {
		return "{'id'=" + id + ", 'name'='" + name + "', 'describes'='" + describes + "', 'author'='" + author
				+ "', 'version'='" + version + "', 'speed'='" + speed + "', 'buildTime'='" + buildTime + "'}";
	}
	
	
	
}
