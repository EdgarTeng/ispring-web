package com.tenchael.ispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.SportType;

public interface SportTypeDao extends JpaRepository<SportType, Integer> {
	//@Query("select s from SportType s where s.name like=1%")
	//public List<SportType> findByName();
}
