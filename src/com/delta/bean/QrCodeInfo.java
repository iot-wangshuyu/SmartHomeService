package com.delta.bean;

import java.sql.Timestamp;

/**
 * 设备信息表
 * @author Shuyu.Wang
 *
 */
public class QrCodeInfo {
	public Integer id;
	public Integer user_id;
	/** 设备id */
	public String device_ID;
	/** 设备名称 */
	public String device_NAME;
	/** 设备Mac地址 */
	public String mac;
	/** 设备类型 */
	public String type;
	/** 设备提供商 */
	public String vendor;
	/** 设备通信协议 */
	public String protocol;
	public String describes;
	public String version;
    public Timestamp installTime;

	public QrCodeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}




	public QrCodeInfo(Integer id, Integer user_id, String device_ID, String device_NAME, String mac, String type,
			String vendor, String protocol, String describes, String version, Timestamp installTime) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.device_ID = device_ID;
		this.device_NAME = device_NAME;
		this.mac = mac;
		this.type = type;
		this.vendor = vendor;
		this.protocol = protocol;
		this.describes = describes;
		this.version = version;
		this.installTime = installTime;
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




	public String getDevice_ID() {
		return device_ID;
	}




	public void setDevice_ID(String device_ID) {
		this.device_ID = device_ID;
	}




	public String getDevice_NAME() {
		return device_NAME;
	}




	public void setDevice_NAME(String device_NAME) {
		this.device_NAME = device_NAME;
	}




	public String getMac() {
		return mac;
	}




	public void setMac(String mac) {
		this.mac = mac;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public String getVendor() {
		return vendor;
	}




	public void setVendor(String vendor) {
		this.vendor = vendor;
	}




	public String getProtocol() {
		return protocol;
	}




	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}




	public String getDescribes() {
		return describes;
	}




	public void setDescribes(String describes) {
		this.describes = describes;
	}




	public String getVersion() {
		return version;
	}




	public void setVersion(String version) {
		this.version = version;
	}




	public Timestamp getInstallTime() {
		return installTime;
	}




	public void setInstallTime(Timestamp installTime) {
		this.installTime = installTime;
	}




	@Override
	public String toString() {
		return "{'id'=" + id + ", 'type'='" + type + "'}";
	}

	
	
	
	
}
