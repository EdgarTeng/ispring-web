package com.tenchael.ispring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Player;
import com.tenchael.ispring.repository.PlayerDao;
import com.tenchael.ispring.service.PlayerService;

@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao playerDao;

	public Page<Player> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
				Direction.DESC, "id"));
		Page<Player> players = playerDao.findAll(pageable);
		return players;
	}

	public Page<Player> findByNameLike(String name, int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
				Direction.DESC, "id"));
		String q = "%" + name + "%";
		Page<Player> persons = playerDao.findByNameLike(q, pageable);
		return persons;
	}

	public Player findById(Integer id) {
		return playerDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Player insert(Player player) {
		return playerDao.save(player);
	}

	@Transactional(readOnly = false)
	public Player update(Player player) {
		return playerDao.save(player);
	}

	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		playerDao.delete(id);

	}

}
