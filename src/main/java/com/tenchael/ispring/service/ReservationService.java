package com.tenchael.ispring.service;

import java.util.List;

import com.tenchael.ispring.domain.Reservation;

public interface ReservationService {
	
	public List<Reservation> query(String courtName);
	
	public List<Reservation> list();

}
