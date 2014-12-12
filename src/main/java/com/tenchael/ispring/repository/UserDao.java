package com.tenchael.ispring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
	public Page<User> findAll(Specification<User> spec, Pageable pageable);

	User findByUsername(String username);
}
