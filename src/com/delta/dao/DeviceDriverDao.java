package com.delta.dao;

import java.util.List;

import com.delta.bean.DeviceDriver;

public interface DeviceDriverDao {
	public boolean insert(DeviceDriver deviceDriver);
	public boolean delete(DeviceDriver deviceDriver);
	public boolean update(DeviceDriver deviceDriver);
	public List<DeviceDriver> select(DeviceDriver deviceDriver);
	public DeviceDriver selectId(DeviceDriver deviceDriver);
	public DeviceDriver selectid(DeviceDriver deviceDriver);
	public DeviceDriver selectVersion(DeviceDriver deviceDriver);
	public List<DeviceDriver> findAll();
	public List<DeviceDriver> findByUser(Integer arg);

}
