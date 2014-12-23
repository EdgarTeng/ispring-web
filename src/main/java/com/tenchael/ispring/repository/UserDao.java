package com.tenchael.ispring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
	public Page<User> findAll(Specification<User> spec, Pageable pageable);

	public List<User> findAll(Iterable<Long> ids);

	public void deleteInBatch(Iterable<User> entities);

	User findByUserName(String username);

}
