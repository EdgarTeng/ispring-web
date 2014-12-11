package com.tenchael.ispring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.CourtStatus;
import com.tenchael.ispring.repository.CourtStatusDao;
import com.tenchael.ispring.service.CourtStatusService;

@Service
@Transactional(readOnly = true)
public class CourtStatusServiceImpl implements CourtStatusService {

	@Autowired
	private CourtStatusDao courtStatusDao;

	public List<CourtStatus> findAll() {
		return courtStatusDao.findAll();
	}

	public Page<CourtStatus> findAll(Pageable page) {
		return courtStatusDao.findAll(page);
	}

	public CourtStatus findById(Short id) {
		return courtStatusDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public CourtStatus save(CourtStatus entity) {
		return courtStatusDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Short id) {
		courtStatusDao.delete(id);
	}

}
