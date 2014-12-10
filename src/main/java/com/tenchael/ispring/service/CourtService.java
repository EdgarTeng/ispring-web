package com.tenchael.ispring.service;

import java.util.List;

import com.tenchael.ispring.domain.Court;

public interface CourtService extends BasicService<Court, Integer> {

	List<Court> findByNameLike(String name);

	List<Court> findBySportTypeName(String sportTypeName);

}
