package com.tenchael.ispring.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tenchael.ispring.common.EntityUtil;

@Entity(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "enable")
	private Short enable = 1;

	@OneToOne(mappedBy = "user", cascade = { CascadeType.ALL })
	private Role role;

	public User() {
		super();
	}

	public User(String username, String password, Short enable, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.enable = enable;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		String[] props = new String[] { "id", "username", "password", "role" };
		return EntityUtil.propertiesToString(this, props);
	}
}
