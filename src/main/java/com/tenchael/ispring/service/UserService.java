package com.tenchael.ispring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.model.UserInfo;

public interface UserService extends BasicService<User, Long> {

	public User getByUserName(String userName);

	public Page<User> searchByUserName(String userName, Pageable pageable);

	public int delUsers(Long[] userIds);

	public boolean saveUserRole(UserInfo userInfo);

}
