package com.tenchael.ispring.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

/**
 * �Լ�ʵ�ֵĹ����û������࣬Ҳ����ֱ��ʹ�� FilterSecurityInterceptor
 * 
 * AbstractSecurityInterceptor�����������ࣺ
 * FilterSecurityInterceptor��������FilterInvocation��ʵ�ֶ�URL��Դ�����ء�
 * MethodSecurityInterceptor��������MethodInvocation��ʵ�ֶԷ������õ����ء�
 * AspectJSecurityInterceptor��������JoinPoint����Ҫ�����ڶ����淽��(AOP)���õ����ء�
 * 
 * ������ֱ��ʹ��ע���Action�����������أ������ڷ����ϼӣ�
 * 
 * @PreAuthorize("hasRole('ROLE_SUPER')")
 * 
 * @author Taven.Li
 *
 */
@Service
public class WebSecurityFilter extends AbstractSecurityInterceptor implements
		Filter {

	private static Logger logger = LoggerFactory
			.getLogger(WebSecurityFilter.class);

	@Autowired
	private WebSecurityMetadataSource securityMetadataSource;
	@Autowired
	@Qualifier("webAuthenticationManager")
	private AuthenticationManager webAuthenticationManager;
	@Autowired
	private WebAccessDecisionManager accessDecisionManager;

	@PostConstruct
	public void init() {
		super.setAuthenticationManager(webAuthenticationManager);
		super.setAccessDecisionManager(accessDecisionManager);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		FilterInvocation fi = new FilterInvocation(request, response, chain);

		logger.debug("requestURL:" + fi.getRequestUrl());

		// ��ִ��doFilter֮ǰ������Ȩ�޵ļ��
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void destroy() {

	}

	public Class<?> getSecureObjectClass() {

		return FilterInvocation.class;
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public WebSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(
			WebSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

}
