package com.delta.dao;

import java.util.List;

import com.delta.bean.InstallApplicationDevice;
import com.delta.bean.QrCodeInfo;

public interface InstallApplicationDeviceDao {
	public boolean insert(InstallApplicationDevice installApplicationDevice);
	public boolean delete(Integer id);
	public boolean update(InstallApplicationDevice installApplicationDevice);
	/**
	 * 查询该应用绑定了哪些设备
	 * @param installApplication_id
	 * @return
	 */
	public List<QrCodeInfo> select(InstallApplicationDevice installApplicationDevice);
	


}
