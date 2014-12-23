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

import com.tenchael.ispring.domain.Court;
import com.tenchael.ispring.domain.SportType;
import com.tenchael.ispring.repository.CourtDao;
import com.tenchael.ispring.service.CourtService;

@Service
@Transactional(readOnly = true)
public class CourtServiceImpl implements CourtService {
	@Autowired
	private CourtDao courtDao;

	public List<Court> findAll() {
		return courtDao.findAll();
	}

	public Page<Court> findByNameLike(String name, Pageable pageable) {
		String qName = "%" + name + "%";
		return courtDao.findByNameLike(qName, pageable);
	}

	public Page<Court> findBySportTypeName(String sportTypeName,
			Pageable pageable) {
		String qName = "%" + sportTypeName + "%";
		return courtDao.findBySportTypeNameLike(qName, pageable);
	}

	public Page<Court> findAll(Pageable page) {
		return courtDao.findAll(page);
	}

	public Court getById(Integer id) {
		return courtDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Court save(Court entity) {
		return courtDao.save(entity);
	}

	@Transactional(readOnly = false)
	public int delete(Integer id) {
		int result = 1;
		try {
			courtDao.delete(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;

	}

	public Page<Court> search(final String condition, Pageable pageable) {
		Specification<Court> spec = new Specification<Court>() {

			public Predicate toPredicate(Root<Court> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predictes = new ArrayList<Predicate>();
				predictes.add(cb.like(root.<String> get("name"), "%"
						+ condition.trim() + "%"));
				predictes.add(cb.like(root.<SportType> get("sportType")
						.<String> get("name"), "%" + condition.trim() + "%"));
				if (predictes.size() > 0) {
					return cb.or(predictes.toArray(new Predicate[predictes
							.size()]));
				}
				return cb.conjunction();
			}
		};
		return courtDao.findAll(spec, pageable);
	}

}
