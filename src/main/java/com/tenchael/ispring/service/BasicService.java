package com.tenchael.ispring.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasicService<Entity extends Serializable, ID extends Serializable> {
	public List<Entity> findAll();

	public Page<Entity> findAll(Pageable page);

	public Entity findById(ID id);

	public Entity save(Entity entity);

	public void delete(ID id);

}
