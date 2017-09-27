package com.delta.dao;

import java.util.List;
import java.util.Map;
import com.delta.bean.ApplicationDescription2;


public interface ApplicationDescriptionDao2 {
	
	public boolean insert(ApplicationDescription2 applicationDescription);
	public boolean delete(ApplicationDescription2 applicationDescription);
	public boolean update(ApplicationDescription2 applicationDescription);
	public List<Map<String, Object>> select(String arg);
	public List<ApplicationDescription2> selects(String arg);
	public List<ApplicationDescription2> selectId(ApplicationDescription2 applicationDescription);

}
