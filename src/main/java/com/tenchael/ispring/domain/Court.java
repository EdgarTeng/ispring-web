package com.tenchael.ispring.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tenchael.ispring.common.EntityUtil;
import com.tenchael.ispring.common.EntityLogable;

@Entity
@Table(name = "t_court")
public class Court implements Serializable, EntityLogable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@Column(name = "name")
	@Size(min = 1, max = 30)
	@NotNull
	private String name;

	@ManyToOne
	private SportType sportType;

	@ManyToOne
	private CourtStatus status;

	public Court() {
	}

	public Court(String name, SportType sportType, CourtStatus status) {
		super();
		this.name = name;
		this.sportType = sportType;
		this.status = status;
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

	public SportType getSportType() {
		return sportType;
	}

	public void setSportType(SportType sportType) {
		this.sportType = sportType;
	}

	public CourtStatus getStatus() {
		return status;
	}

	public void setStatus(CourtStatus status) {
		this.status = status;
	}

	public String printForLog() {
		String[] props = new String[] { "id", "name", "reservered", "sportType" };
		return EntityUtil.propertiesToString(this, props);
	}

}
