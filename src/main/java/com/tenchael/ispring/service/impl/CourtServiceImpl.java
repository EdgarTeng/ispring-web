package com.tenchael.ispring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Court;
import com.tenchael.ispring.repository.CourtDao;
import com.tenchael.ispring.service.CourtService;

@Service
@Transactional(readOnly = true)
public class CourtServiceImpl implements CourtService {
	@Autowired
	private CourtDao courtDao;

	public List<Court> findByNameLike(String name) {
		//return courtDao.findByNameLike(name);
		return null;
	}

	public List<Court> findBySportTypeName(String sportTypeName) {
		//return courtDao.findBySportTypeNameLike(sportTypeName);
		return null;
	}

	public List<Court> findAll() {
		return courtDao.findAll();
	}

	public Page<Court> findAll(Pageable page) {
		return courtDao.findAll(page);
	}

	public Court findById(Integer id) {
		return courtDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Court save(Court entity) {
		return courtDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		courtDao.delete(id);

	}

}
