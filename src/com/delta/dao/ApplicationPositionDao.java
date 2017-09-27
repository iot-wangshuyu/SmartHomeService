package com.delta.dao;

import java.util.List;
import com.delta.bean.ApplicationPosition;

public interface ApplicationPositionDao {
	public boolean insert(ApplicationPosition applicationPosition);
	public boolean delete(ApplicationPosition applicationPosition);
	public boolean update(ApplicationPosition applicationPosition);
	public List<ApplicationPosition> selects(String arg);
	public List<ApplicationPosition> selectId(ApplicationPosition applicationPosition);
	public List<ApplicationPosition> selectByID(Integer arg);

}
