package com.tenchael.ispring.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tenchael.ispring.domain.User;

public interface UserService extends BasicService<User, Long>,
		UserDetailsService {

}
