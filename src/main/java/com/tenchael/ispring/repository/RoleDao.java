package com.tenchael.ispring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;

public interface RoleDao extends JpaRepository<Role, Short> {
	public Page<Role> findAll(Specification<Role> spec, Pageable pageable);

	public Role findOne(Specification<Role> spec);

	public List<Role> findByStatus(Short status);

}
