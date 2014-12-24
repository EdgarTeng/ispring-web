package com.tenchael.ispring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.service.UserService;

/**
 * 实现 UserDetailsService 接口，主要是在 loadUserByUsername 方法中验证一个用户
 * 
 * 这里需要从数据库中读取验证表单提交过来的用户
 * 
 * @author Taven.Li
 *
 */
@Service
public class WebUserDetailsService implements UserDetailsService {

	private static Logger logger = LoggerFactory
			.getLogger(WebUserDetailsService.class);

	protected MessageSourceAccessor messages = SpringSecurityMessageSource
			.getAccessor();

	@Autowired
	private UserService userService;

	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// 该方法负责实现验证并授权

		com.tenchael.ispring.domain.User userEntity = userService
				.getByUserName(username);

		if (null == userEntity) {
			throw new UsernameNotFoundException(messages.getMessage(
					"User.notFound", new Object[] { username },
					"Username {0} not found"));
		}

		long userId = userEntity.getId();
		String password = userEntity.getPassWord();
		boolean userEnabled = userEntity.getStatus() == 1;

		// 读取当前用户有哪些角色权限
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<Role> userRoles = userEntity.getRoles();
		for (Role userRole : userRoles) {
			// 这里的 role 参数为自己定义的，要和 SecurityMetadataSource 中的 SecurityConfig
			// 参数对应
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
					"ROLE_" + userRole.getRoleName());

			authorities.add(authority);
		}

		// 我这里是把超级用户名写死的，您也可以把它实现可配置化
		// 如果是超级用户，则添加超级用户的授权
		/*
		 * if (username.equals("admin")) { // ROLE_SUPER 这个权限名字也是自己定义的
		 * authorities.add(new SimpleGrantedAuthority("ROLE_SUPER")); }
		 */

		// 创建 UserDetails 对象
		WebUserDetails webUserDetails = new WebUserDetails(userId, username,
				password, userEnabled, authorities);

		return webUserDetails;

	}

}
