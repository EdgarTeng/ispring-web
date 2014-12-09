package com.tenchael.ispring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.Player;

public interface PlayerDao extends JpaRepository<Player, Integer> {
	Page<Player> findByNameLike(String name, Pageable pageable);
}
