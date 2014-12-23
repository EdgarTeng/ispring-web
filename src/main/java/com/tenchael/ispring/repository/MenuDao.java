package com.tenchael.ispring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer> {
	
	public List<Menu> findAll(Specification<Menu> spec);

	public Page<Menu> findAll(Specification<Menu> spec, Pageable pageable);
}
