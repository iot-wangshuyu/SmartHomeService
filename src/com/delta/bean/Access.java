package com.delta.bean;

/**
 * 记录用户访问次数
 * @author Shuyu.Wang
 *
 */
public class Access {
	public Integer id;
	public Integer user_id;
	public Access() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Access(Integer id, Integer user_id) {
		super();
		this.id = id;
		this.user_id = user_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	

}
