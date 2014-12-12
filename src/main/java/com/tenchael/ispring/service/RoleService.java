package com.tenchael.ispring.service;

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;

public interface RoleService {
	Role getByUser(User user);

}
