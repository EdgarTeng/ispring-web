package com.tenchael.ispring.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ����ʵ�� UserDetails �ӿڣ���������֤�ɹ���ᱻ�����ڵ�ǰ�ػ���principal������
 * 
 * ��ö���ķ�ʽ�� WebUserDetails webUserDetails =
 * (WebUserDetails)SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal();
 * 
 * ����JSP�У� <sec:authentication property="principal.username"/>
 * 
 * �����Ҫ�����û����������ԣ������ڸ�����������Ӧ���Լ���
 * 
 * @author Taven.Li
 *
 */
public class WebUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8242940190960961504L;

	private String username;
	private String password;
	private boolean userEnabled;
	private Collection<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;

	// �������ӵ�����
	private long userId;

	public WebUserDetails(long userId, String username, String password,
			boolean userEnabled, Collection<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.userEnabled = userEnabled;
		this.authorities = authorities;

		// �����ȳ�ʼ��Ϊtrue�������Ҫ��ȿ��ƣ�������
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;

		//
		this.userId = userId;

	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public boolean isEnabled() {
		return this.userEnabled;
	}

	public long getUserId() {
		return userId;
	}

}
