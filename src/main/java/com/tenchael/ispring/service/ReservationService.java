package com.tenchael.ispring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.Court;
import com.tenchael.ispring.domain.Reservation;

public interface ReservationService extends BasicService<Reservation, Integer> {

	public Page<Reservation> findBySportTpyeName(String sportTypeName,
			Pageable pageable);

	public Page<Reservation> findByPlayerName(String playerName,Pageable pageable);

	public Reservation findByCourt(Court court);

}
