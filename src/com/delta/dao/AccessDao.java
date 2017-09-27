package com.delta.dao;

import java.util.List;

import com.delta.bean.Access;

public interface AccessDao {
	public boolean insert(Integer arg);
	public boolean delete(Integer arg);
	public boolean update(Integer arg);
	public int select(Integer arg);
	public List<Access> findAll();

}
