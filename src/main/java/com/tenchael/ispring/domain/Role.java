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

@Entity(name = "role")
@Table(name = "t_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Short id;

	@Column(name = "roleName")
	private String roleName;

	@Column(name = "createTime")
	private Date createTime;
	@Column(name = "lastUpdate")
	private Date lastUpdate;

	@Column(name = "status")
	private Short status;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(name = "t_role_menu", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "menuId") })
	private Set<Menu> menus;

	public Role() {
		super();
	}

	public Role(String roleName, Date createTime, Date lastUpdate,
			Short status, Set<Menu> menus) {
		super();
		this.roleName = roleName;
		this.createTime = createTime;
		this.lastUpdate = lastUpdate;
		this.status = status;
		this.menus = menus;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		String[] props = new String[] { "id", "roleName" };
		return EntityUtil.propertiesToString(this, props);
	}

}