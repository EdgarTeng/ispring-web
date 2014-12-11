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

@Entity
@Table(name = "t_court")
public class Court implements Serializable {

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
	private CourtStatus courtStatus;

	public Court() {
	}

	public Court(String name, SportType sportType, CourtStatus courtStatus) {
		super();
		this.name = name;
		this.sportType = sportType;
		this.courtStatus = courtStatus;
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

	public CourtStatus getCourtStatus() {
		return courtStatus;
	}

	public void setCourtStatus(CourtStatus courtStatus) {
		this.courtStatus = courtStatus;
	}

	@Override
	public String toString() {
		String[] props = new String[] { "id", "name", "sportType",
				"courtStatus" };
		return EntityUtil.propertiesToString(this, props);
	}

}
