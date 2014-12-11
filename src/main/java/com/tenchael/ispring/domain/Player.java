package com.tenchael.ispring.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tenchael.ispring.common.EntityUtil;

@Entity
@Table(name = "t_player")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@Column(name = "name")
	@Size(min = 1, max = 30)
	@NotNull
	private String name;

	@Column(name = "phone")
	@Size(min = 7, max = 13)
	private String phone;

	@OneToMany
	private Set<Reservation> reservations;

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

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		String[] props = new String[] { "id", "name", "phone" };
		return EntityUtil.propertiesToString(this, props);
	}
}
