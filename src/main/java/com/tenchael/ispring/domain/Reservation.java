package com.tenchael.ispring.domain;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String courtName;
	private Date date;
	private Integer hour;
	private Player player;
	private SportType sportType;

	public Reservation() {
		super();
	}

	public Reservation(String courtName, Date date, Integer hour,
			Player player, SportType sportType) {
		super();
		this.courtName = courtName;
		this.date = date;
		this.hour = hour;
		this.player = player;
		this.sportType = sportType;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
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

}
