package com.tenchael.ispring.service;

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;

public interface RoleService extends BasicService<Role, Long> {
	Role getByUser(User user);

}
