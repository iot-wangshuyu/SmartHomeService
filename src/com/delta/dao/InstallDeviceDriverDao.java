package com.delta.dao;

import java.util.List;
import com.delta.bean.InstallDeviceDriver;
import com.delta.bean.SelectInstallDeviceDriver;

public interface InstallDeviceDriverDao {
	public boolean insert(InstallDeviceDriver installDeviceDriver);
	public boolean update(InstallDeviceDriver installDeviceDriver);
	public boolean delete(InstallDeviceDriver installDeviceDriver);
	public List<InstallDeviceDriver> selectBy(InstallDeviceDriver installDeviceDriver);
	public List<SelectInstallDeviceDriver> select(InstallDeviceDriver installDeviceDriver);
	public int selectid(InstallDeviceDriver installDeviceDriver);
	public InstallDeviceDriver selectVersion(InstallDeviceDriver installDeviceDriver);
	public List<InstallDeviceDriver> findAll();
	public SelectInstallDeviceDriver selectD(Integer arg);

}
