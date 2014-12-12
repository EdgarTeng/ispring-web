package com.tenchael.ispring.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tenchael.ispring.common.EntityUtil;

@Entity(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@OneToOne
	private User user;
	private Integer role;

	public Role() {
		super();
	}

	public Role(User user, Integer role) {
		super();
		this.user = user;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Override
	public String toString() {
		String[] props = new String[] { "id", "user", "role" };
		return EntityUtil.propertiesToString(this, props);
	}

}