package com.delta.bean;

import java.sql.Timestamp;

/**
 * 查找已安装的设备驱动
 * @author Shuyu.Wang
 *
 */
public class SelectInstallDeviceDriver {
	public Integer id;
	public Integer driver_id;
	public String version;
	public String name;
	public String author;
	public String deviceName;
	public String deviceVendor;
	public String deviceType;
	public String deviceProtocol;
	public Timestamp installTime;
	public SelectInstallDeviceDriver() {
		super();
	}
	
	public SelectInstallDeviceDriver(Integer id, Integer driver_id, String version, String name, String author,
			String deviceName, String deviceVendor, String deviceType, String deviceProtocol, Timestamp installTime) {
		super();
		this.id = id;
		this.driver_id = driver_id;
		this.version = version;
		this.name = name;
		this.author = author;
		this.deviceName = deviceName;
		this.deviceVendor = deviceVendor;
		this.deviceType = deviceType;
		this.deviceProtocol = deviceProtocol;
		this.installTime = installTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceVendor() {
		return deviceVendor;
	}

	public void setDeviceVendor(String deviceVendor) {
		this.deviceVendor = deviceVendor;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceProtocol() {
		return deviceProtocol;
	}

	public void setDeviceProtocol(String deviceProtocol) {
		this.deviceProtocol = deviceProtocol;
	}

	public Timestamp getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Timestamp installTime) {
		this.installTime = installTime;
	}

	@Override
	public String toString() {
		return "{'id'=" + id + ", 'name'='" + name + "', 'deviceName'='"
				+ deviceName + /*"', 'deviceVendor'='" + deviceVendor + "', 'deviceType'='" + deviceType + */"'}";
	}
	

}
