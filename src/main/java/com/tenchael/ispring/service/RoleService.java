package com.tenchael.ispring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;

public interface RoleService extends BasicService<Role, Short> {
	Role getByUser(User user);
	
	List<Role> findByStatus(Short status);

	List<Role> getAvailableRoles();

	Page<Role> findByRoleName(String roleName, Pageable pageable);

}
