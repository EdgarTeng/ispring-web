package com.tenchael.ispring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tenchael.ispring.domain.Court;

public interface CourtDao extends JpaRepository<Court, Integer> {

/*	@Query("select c from Court c where c.name like ?1%")
	List<Court> findByNameLike(String name);
	
	@Query("select c from Court c where c.sportType.name like ?1%")
	List<Court> findBySportTypeNameLike(String name);*/
}
