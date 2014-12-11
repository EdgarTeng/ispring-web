package com.tenchael.ispring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.SportType;

public interface SportTypeService extends BasicService<SportType, Integer> {

	Page<SportType> findByNameLike(String name, Pageable pageable);

}
