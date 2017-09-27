package com.delta.dao;

import java.util.List;

import com.delta.bean.Application;
import com.delta.bean.ApplicationUnion;




public interface ApplicationDao {
	public boolean insert(Application application);
	public boolean delete(Application application);
	public boolean update(Application application);
	public Application select(Application application);
	public Application selectId();
	public List<Application> selectadd(String arg);
	public List<Application> selectName(String arg);
	public List<ApplicationUnion> findAll();
	public Application selectVersion(Application application);
}
