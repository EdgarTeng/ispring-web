package com.tenchael.ispring.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.tenchael.ispring.common.EntityUtil;

@Entity
@Table(name = "t_sportType")
public class SportType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@Column(name = "name")
	@Size(min = 1, max = 10)
	private String name;

	@OneToMany
	private Set<Court> courts;

	public SportType() {
		super();
	}

	public SportType(String name) {
		super();
		this.name = name;
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

	public Set<Court> getCourts() {
		return courts;
	}

	public void setCourts(Set<Court> courts) {
		this.courts = courts;
	}

	@Override
	public String toString() {
		return EntityUtil.propertiesToString(this);
	}
}
