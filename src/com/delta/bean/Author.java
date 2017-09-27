package com.delta.bean;

import java.sql.Timestamp;

/**驱动。应用作者表
 * @author Shuyu.Wang
 *
 */
public class Author {
	private Integer id;
	private String userName;
	private String passWord;
	private Timestamp registerTime;
	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Author(String userName, String passWord, Timestamp registerTime) {
		super();
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
