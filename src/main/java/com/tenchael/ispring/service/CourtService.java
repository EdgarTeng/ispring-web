package com.tenchael.ispring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.Court;

public interface CourtService extends BasicService<Court, Integer> {

	Page<Court> findByNameLike(String name,Pageable pageable);

	Page<Court> findBySportTypeName(String sportTypeName,Pageable pageable);
	
	Page<Court> search(String condition,Pageable pageable);

}
