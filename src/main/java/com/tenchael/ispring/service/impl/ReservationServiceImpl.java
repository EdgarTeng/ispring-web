package com.tenchael.ispring.service.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenchael.ispring.domain.Player;
import com.tenchael.ispring.domain.Reservation;
import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	public static final SportType TENNIS = new SportType(1, "Tennis");
	public static final SportType SOCCER = new SportType(2, "Soccer");

	private List<Reservation> reservations = null;

	public ReservationServiceImpl() {
		reservations = new ArrayList<Reservation>();
		reservations.add(new Reservation("Tennis #1", new GregorianCalendar(
				2014, 12, 5).getTime(), 4, new Player("Alice", "13212123145"),
				TENNIS));
		reservations.add(new Reservation("Tennis #2", new GregorianCalendar(
				2014, 12, 1).getTime(), 4, new Player("Ann", "N/A"), TENNIS));
	}

	public List<Reservation> query(String courtName) {
		List<Reservation> result = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getCourtName().equals(courtName)) {
				result.add(reservation);
			}
		}
		return result;
	}

	public List<Reservation> list() {
		return reservations;
	}

}
