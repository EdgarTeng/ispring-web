package com.tenchael.ispring.domain;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String phone;

	public Player() {
		super();
	}

	public Player(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
