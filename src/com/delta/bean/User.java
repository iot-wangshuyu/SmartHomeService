package com.delta.bean;

import java.sql.Timestamp;

/**
 * 用户表
 * @author Shuyu.Wang
 *
 */
public class User {
	private Integer id;
	private String userName;
	private String passWord;
	private Timestamp registerTime;
	public User() {
		super();
	}
	public User(String userName, String passWord, Timestamp registerTime) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.registerTime = registerTime;
	}
	public User(Integer id, String userName, String passWord, Timestamp registerTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.registerTime = registerTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	
	

}
