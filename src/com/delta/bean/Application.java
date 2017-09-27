package com.delta.bean;

import java.sql.Timestamp;

/**
 * 应用表
 * @author Shuyu.Wang
 *
 */
public class Application {
	public Integer id;
	public String name;
	public String describes;
	public String author;
	public String version;
	public String fileName;
	public String image;
	public String address;
	public Timestamp buildTime;
	public Application() {
		super();
	}


	public Application(Integer id, String name, String describes, String author, String version, String fileName,
			String image, String address, Timestamp buildTime) {
		super();
		this.id = id;
		this.name = name;
		this.describes = describes;
		this.author = author;
		this.version = version;
		this.fileName = fileName;
		this.image = image;
		this.address = address;
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


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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
				+ "', 'buildTime'='" + buildTime + "'}";
	}
	
	
}
