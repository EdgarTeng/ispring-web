package com.tenchael.ispring.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

	private long id;
	private String userName;
	private List<Short> roles = new ArrayList<Short>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Short> getRoles() {
		return roles;
	}

	public void setRoles(List<Short> roles) {
		this.roles = roles;
	}

}
