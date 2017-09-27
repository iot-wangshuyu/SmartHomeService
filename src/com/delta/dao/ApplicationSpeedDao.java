package com.delta.dao;

import java.util.List;

import com.delta.bean.ApplicationSpeed;



public interface ApplicationSpeedDao {
	
	public boolean insert(ApplicationSpeed applicationSpeed);
	public boolean delete(ApplicationSpeed applicationSpeed);
	public boolean update(ApplicationSpeed applicationSpeed);
	public List<ApplicationSpeed> selects(String arg);
	public List<ApplicationSpeed> selectId(ApplicationSpeed applicationSpeed);

}
