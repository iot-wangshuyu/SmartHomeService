package com.delta.dao;

import java.util.List;
import com.delta.bean.QrCodeInfo;

public interface InstallDeviceDao {
	public boolean insert(QrCodeInfo qrCodeInfo);
	public boolean update(QrCodeInfo qrCodeInfo);
	public boolean delete(QrCodeInfo qrCodeInfo);
	public List<QrCodeInfo> selectBy(QrCodeInfo qrCodeInfo);
	public List<QrCodeInfo> select(QrCodeInfo qrCodeInfo);
	public QrCodeInfo selectD(Integer arg);
}
