package com.tenchael.ispring.service.impl;

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

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.repository.RoleDao;
import com.tenchael.ispring.service.MenuService;
import com.tenchael.ispring.service.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private MenuService menuService;

	public Role getByUser(final User user) {
		Specification<Role> spec = new Specification<Role>() {
			public Predicate toPredicate(Root<Role> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<User> get("user").<Long> get("id"),
						user.getId());
			}
		};
		return roleDao.findOne(spec);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}

	public Page<Role> findAll(Pageable page) {
		return roleDao.findAll(page);
	}

	public Role getById(Short id) {
		return roleDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Role save(Role entity) {
		return roleDao.save(entity);
	}

	@Transactional(readOnly = false)
	public int delete(Short id) {
		int result = 1;
		try {
			roleDao.delete(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public List<Role> findByStatus(Short status) {
		return roleDao.findByStatus(status);
	}

	public List<Role> getAvailableRoles() {
		short status = 1;
		return findByStatus(status);
	}

	public Page<Role> findByRoleName(final String roleName, Pageable pageable) {
		Specification<Role> spec = new Specification<Role>() {

			public Predicate toPredicate(Root<Role> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get("roleName"), roleName);
			}
		};
		return roleDao.findAll(spec, pageable);
	}

}
