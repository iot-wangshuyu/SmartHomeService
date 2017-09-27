package com.delta.dao;

import java.util.List;

import com.delta.bean.User;


public interface UserDao {
	public boolean insert(User user);
	public boolean delete(int id);
	public boolean update(User user);
	public List<User> select(User user);
	public User selectByName(User user);

}
