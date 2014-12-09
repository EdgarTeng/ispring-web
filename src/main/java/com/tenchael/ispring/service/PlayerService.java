package com.tenchael.ispring.service;

import org.springframework.data.domain.Page;

import com.tenchael.ispring.domain.Player;

public interface PlayerService {

	Page<Player> findAll(int page, int size);

	Page<Player> findByNameLike(String name, int page, int size);

	Player findById(Integer id);

	Player insert(Player player);

	Player update(Player player);

	void deleteById(Integer id);

}
