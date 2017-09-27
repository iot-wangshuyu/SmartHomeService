package com.delta.bean;

import java.sql.Timestamp;

/**
 * 设备驱动表
 * @author Shuyu.Wang
 *
 */
public class DeviceDriver {
	// 主键
	public Integer id;
	// 驱动名称
	public String name;
	// 驱动位置
	public String address;
	// 驱动版本
	public String version;
	// 驱动更新时间
	public Timestamp updateTime;
	// 驱动开发者
	public String author;
	// 该驱动所支持的设备名称
	public String deviceName;
	// 设备的制造厂商
	public String deviceVendor;
	// 设备的类型
	public String deviceType;
	// 设备的传输协议
	public String deviceProtocol;
	
	
	public DeviceDriver() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceDriver(String name, String address, String version, Timestamp updateTime, String author,
			String deviceName, String deviceVendor, String deviceType, String deviceProtocol) {
		super();
		this.name = name;
		this.address = address;
		this.version = version;
		this.updateTime = updateTime;
		this.author = author;
		this.deviceName = deviceName;
		this.deviceVendor = deviceVendor;
		this.deviceType = deviceType;
		this.deviceProtocol = deviceProtocol;
	}

	public DeviceDriver(Integer id, String name, String address, String version, Timestamp updateTime, String author,
			String deviceName, String deviceVendor, String deviceType, String deviceProtocol) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.version = version;
		this.updateTime = updateTime;
		this.author = author;
		this.deviceName = deviceName;
		this.deviceVendor = deviceVendor;
		this.deviceType = deviceType;
		this.deviceProtocol = deviceProtocol;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getAuthor(){
		return author;
	}
	public void setAuthor(String author){
		this.author=author;
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

	@Override
	public String toString() {
		return "{'id'=" + id + ", 'name'='" + name + "', 'version'='" + version
				+ "', 'updateTime'='" + updateTime + "','author'='" + author + "', 'deviceName'='" + deviceName + "', 'deviceVendor'='"
				+ deviceVendor + "', 'deviceType'='" + deviceType + "', 'deviceProtocol'='" + deviceProtocol + "'}";
	}
	
}
