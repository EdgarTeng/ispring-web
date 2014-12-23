package com.tenchael.ispring.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Player;
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

	public SportType getById(Integer id) {
		return sportTypeDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public SportType save(SportType entity) {
		return sportTypeDao.save(entity);
	}

	@Transactional(readOnly = false)
	public int delete(Integer id) {
		int result = 1;
		try {
			sportTypeDao.delete(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public Page<SportType> findByNameLike(final String name, Pageable pageable) {
		Specification<SportType> spec = new Specification<SportType>() {
			public Predicate toPredicate(Root<SportType> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("name"), "%" + name.trim()
						+ "%");
			}
		};
		return sportTypeDao.findAll(spec, pageable);
	}

}
