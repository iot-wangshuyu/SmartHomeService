package com.delta.dao;

import java.util.List;

import com.delta.bean.InstallApplication;
import com.delta.bean.InstallApplicationDevice;


public interface InstallApplicationDao {
	public boolean insert( InstallApplication  installApplication);
	public boolean delete( InstallApplication  installApplication);
	public boolean update( InstallApplication  installApplication);
	public boolean updateVersion(  InstallApplication  installApplication);
	public int selectId( Integer arg);
	public InstallApplication selectVersion( InstallApplication  installApplication);
	public List<InstallApplication> select(  InstallApplication  installApplication);
	public InstallApplication selectD(Integer arg);
	/**
	 * 查询最新一条记录
	 * @return
	 */
	public InstallApplication selectId();

}
