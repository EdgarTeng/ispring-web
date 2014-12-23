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
 * ʵ�� UserDetailsService �ӿڣ���Ҫ���� loadUserByUsername ��������֤һ���û�
 * 
 * ������Ҫ�����ݿ��ж�ȡ��֤���ύ�������û�
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
		// �÷�������ʵ����֤����Ȩ

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

		// ��ȡ��ǰ�û�����Щ��ɫȨ��
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<Role> userRoles = userEntity.getRoles();
		for (Role userRole : userRoles) {
			// ����� role ����Ϊ�Լ�����ģ�Ҫ�� SecurityMetadataSource �е� SecurityConfig
			// ������Ӧ
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
					"ROLE_" + userRole.getId());

			authorities.add(authority);
		}

		// �������ǰѳ����û���д���ģ���Ҳ���԰���ʵ�ֿ����û�
		// ����ǳ����û�������ӳ����û�����Ȩ
		if (username.equals("admin")) {
			// ROLE_SUPER ���Ȩ������Ҳ���Լ������
			authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
		}

		// ���� UserDetails ����
		WebUserDetails webUserDetails = new WebUserDetails(userId, username,
				password, userEnabled, authorities);

		return webUserDetails;

	}

}
