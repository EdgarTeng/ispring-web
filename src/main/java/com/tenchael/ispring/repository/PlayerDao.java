package com.tenchael.ispring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tenchael.ispring.domain.Player;

public interface PlayerDao extends JpaRepository<Player, Integer> {
	@Query("select p from Player p where p.name like ?1")
	Page<Player> findByNameLike(String name, Pageable pageable);

	public Page<Player> findAll(Specification<Player> spec, Pageable pageable);
}
