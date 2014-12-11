package com.tenchael.ispring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tenchael.ispring.domain.Court;

public interface CourtDao extends JpaRepository<Court, Integer> {

	@Query("select c from Court c where c.name like ?1")
	Page<Court> findByNameLike(String name, Pageable pageable);

	@Query("select c from Court c where c.sportType.name like ?1")
	Page<Court> findBySportTypeNameLike(String name, Pageable pageable);

	public Page<Court> findAll(Specification<Court> spec, Pageable pageable);
}
