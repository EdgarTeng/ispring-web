package com.tenchael.ispring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.Player;

public interface PlayerService extends BasicService<Player, Integer> {

	Page<Player> findAll(int page, int size);

	Page<Player> findByNameLike(String name, Pageable pageable);

	Page<Player> search(String condition, Pageable page);

}
