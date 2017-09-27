package com.delta.dao;

import java.util.List;

import com.delta.bean.ApplicationDeviceDriver;
import com.delta.bean.DeviceDriver;

public interface ApplicationDeviceDriverDao {
	public boolean insert(ApplicationDeviceDriver applicationDeviceDriver);
	public boolean delete(ApplicationDeviceDriver applicationDeviceDriver);
	public boolean update(ApplicationDeviceDriver applicationDeviceDriver);
	public List<ApplicationDeviceDriver> select(ApplicationDeviceDriver applicationDeviceDriver);
	public List<DeviceDriver> selectByA(Integer arg);
}
