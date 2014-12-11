package com.tenchael.ispring.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
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

	public List<Player> findAll() {
		return playerDao.findAll();
	}

	public Page<Player> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
				Direction.DESC, "id"));
		Page<Player> players = playerDao.findAll(pageable);
		return players;
	}

	public Page<Player> findAll(Pageable page) {
		return playerDao.findAll(page);
	}

	public Page<Player> findByNameLike(String name, Pageable pageable) {
		String nameLike = "%" + name + "%";
		Page<Player> players = playerDao.findByNameLike(nameLike, pageable);
		return players;
	}

	public Player findById(Integer id) {
		return playerDao.findOne(id);
	}

	public Page<Player> search(final String condition, Pageable page) {
		Specification<Player> spec = new Specification<Player>() {
			public Predicate toPredicate(Root<Player> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predictes = new ArrayList<Predicate>();
				predictes.add(cb.like(root.<String> get("name"), "%"
						+ condition.trim() + "%"));
				predictes.add(cb.like(root.<String> get("phone"),
						condition.trim() + "%"));
				if (predictes.size() > 0) {
					return cb.or(predictes.toArray(new Predicate[predictes
							.size()]));
				}
				return cb.conjunction();
			}
		};
		return playerDao.findAll(spec, page);
	}

	@Transactional(readOnly = false)
	public Player save(Player entity) {
		return playerDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		playerDao.delete(id);
	}

}
