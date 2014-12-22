package com.tenchael.ispring.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.common.DEncryptionUtils;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.domain.User;
import com.tenchael.ispring.repository.UserDao;
import com.tenchael.ispring.service.RoleService;
import com.tenchael.ispring.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	public List<User> findAll() {
		return userDao.findAll();
	}

	public Page<User> findAll(Pageable page) {
		return userDao.findAll(page);
	}

	public User findById(Long id) {
		return userDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public User save(User entity) {
		entity.setPassword(DEncryptionUtils.standPwdEncoder(entity
				.getPassword()));
		User user = userDao.save(entity);
		Role role = new Role(user, 2);
		role = roleService.save(role);
		user.setRole(role);
		return user;
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		userDao.delete(id);
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			com.tenchael.ispring.domain.User domainUser = userDao
					.findByUsername(username);
			if (domainUser == null) {
				throw new UsernameNotFoundException("user do not exit");
			}

			boolean enabled = true;
			if (0 == domainUser.getEnable()) {
				enabled = false;
			}

			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			return new org.springframework.security.core.userdetails.User(
					domainUser.getUsername(), domainUser.getPassword()
							.toLowerCase(), enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked,
					getAuthorities(domainUser.getRole().getRole()));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}

		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
