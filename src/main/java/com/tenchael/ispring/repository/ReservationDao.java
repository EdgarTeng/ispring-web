package com.tenchael.ispring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.Reservation;

public interface ReservationDao extends JpaRepository<Reservation, Integer> {

	public Page<Reservation> findAll(Specification<Reservation> spec,
			Pageable pageable);

	/*@Query("select r from Reservation r where r.player.name like ?1%")
	public List<Reservation> findByPlayerName(String name);

	@Query("select r from Reservation r where r.court.name like=?1")
	public List<Reservation> findByCourt(Court court);*/

}
