package com.tenchael.ispring.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tenchael.ispring.common.EntityUtil;

@Entity
@Table(name = "t_court_status")
public class CourtStatus implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Short id;

	@Column(name = "name")
	@Size(min = 1, max = 10)
	@NotNull
	private String name;

	public CourtStatus() {
		super();
	}

	public CourtStatus(String name) {
		super();
		this.name = name;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		String[] props = new String[] { "id", "name" };
		return EntityUtil.propertiesToString(this, props);
	}

}
