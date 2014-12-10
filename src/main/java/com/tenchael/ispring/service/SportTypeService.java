package com.tenchael.ispring.service;

import java.util.List;

import com.tenchael.ispring.domain.SportType;

public interface SportTypeService extends BasicService<SportType, Integer> {

	List<SportType> findByNameLike(String name);

}
