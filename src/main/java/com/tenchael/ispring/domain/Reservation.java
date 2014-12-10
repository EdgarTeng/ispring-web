package com.tenchael.ispring.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tenchael.ispring.common.EntityUtil;

@Entity
@Table(name = "t_reservation")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@OneToOne
	private Court court;

	@Column(name = "date")
	private Date date;

	@Column(name = "duringHour")
	private Integer duringHour = 1;

	@ManyToOne
	private Player player;

	@OneToOne
	private SportType sportType;

	public Reservation() {
		super();
	}

	public Reservation(Court court, Date date, Integer duringHour,
			Player player, SportType sportType) {
		super();
		this.court = court;
		this.date = date;
		this.duringHour = duringHour;
		this.player = player;
		this.sportType = sportType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getDuringHour() {
		return duringHour;
	}

	public void setDuringHour(Integer duringHour) {
		this.duringHour = duringHour;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public SportType getSportType() {
		return sportType;
	}

	public void setSportType(SportType sportType) {
		this.sportType = sportType;
	}

	@Override
	public String toString() {
		return EntityUtil.propertiesToString(this);
	}

}
