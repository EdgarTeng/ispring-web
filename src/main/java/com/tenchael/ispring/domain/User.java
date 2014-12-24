package com.tenchael.ispring.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.tenchael.ispring.common.EntityUtil;

@Entity(name = "user")
@Table(name = "t_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	@Column(name = "username", unique = true)
	private String userName;

	@Column(name = "password")
	private String passWord;

	@Column(name = "createTime")
	private Date createTime;

	@Column(name = "lastUpdate")
	private Date lastUpdate;

	/**
	 * 1:∆Ù”√;0:Ω˚”√
	 */
	@Column(name = "status")
	private Short status = 1;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	private Set<Role> roles;

	public User() {
		super();
	}

	public User(String userName, String passWord, Date createTime,
			Date lastUpdate, Short status, Set<Role> roles) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.createTime = createTime;
		this.lastUpdate = lastUpdate;
		this.status = status;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		String[] props = new String[] { "id", "username", "password", "role" };
		return EntityUtil.propertiesToString(this, props);
	}
}
