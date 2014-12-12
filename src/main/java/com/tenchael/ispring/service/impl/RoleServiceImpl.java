package com.tenchael.ispring.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.repository.RoleDao;
import com.tenchael.ispring.service.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

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

}
