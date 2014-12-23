package com.tenchael.ispring.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Court;
import com.tenchael.ispring.domain.Reservation;
import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.repository.ReservationDao;
import com.tenchael.ispring.service.ReservationService;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDao reservationDao;

	public List<Reservation> findAll() {
		return reservationDao.findAll();
	}

	public Page<Reservation> findAll(Pageable page) {
		return reservationDao.findAll(page);
	}

	public Reservation getById(Integer id) {
		return reservationDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Reservation save(Reservation entity) {
		return reservationDao.save(entity);
	}

	@Transactional(readOnly = false)
	public int delete(Integer id) {
		int result = 1;
		try {
			reservationDao.delete(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public Page<Reservation> findBySportTpyeName(final String sportTypeName,
			Pageable pageable) {
		Specification<Reservation> spec = new Specification<Reservation>() {

			public Predicate toPredicate(Root<Reservation> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				cb.like(root.<SportType> get("sportType").<String> get("name"),
						sportTypeName + "%");
				return cb.conjunction();
			}
		};
		return reservationDao.findAll(spec, pageable);
	}

	public Page<Reservation> findByPlayerName(String playerName,
			Pageable pageable) {
		String qName = "%" + playerName + "%";
		return reservationDao.findByPlayerName(qName, pageable);
	}

	public Reservation findByCourt(Court court) {
		/*
		 * List<Reservation> reservations = reservationDao.findByCourt(court);
		 * if (null != reservations || reservations.isEmpty()) { return null; }
		 * return reservations.get(0);
		 */
		return null;
	}

}
