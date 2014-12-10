package com.tenchael.ispring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.repository.SportTypeDao;
import com.tenchael.ispring.service.SportTypeService;

@Service
@Transactional(readOnly = true)
public class SportTypeServiceImpl implements SportTypeService {

	@Autowired
	private SportTypeDao sportTypeDao;

	public List<SportType> findAll() {
		return sportTypeDao.findAll();
	}

	public Page<SportType> findAll(Pageable page) {
		return sportTypeDao.findAll(page);
	}

	public SportType findById(Integer id) {
		return sportTypeDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public SportType save(SportType entity) {
		return sportTypeDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		sportTypeDao.delete(id);
	}

	public List<SportType> findByNameLike(String name) {
		//return sportTypeDao.findByName();
		return null;
	}

}
