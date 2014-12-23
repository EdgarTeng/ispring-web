package com.tenchael.ispring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Service;

import com.tenchael.ispring.domain.Menu;
import com.tenchael.ispring.domain.Role;
import com.tenchael.ispring.service.MenuService;
import com.tenchael.ispring.service.RoleService;
import com.tenchael.ispring.service.UserService;

/**
 * [���Ĵ����߼�]
 * 
 * ��ԴԴ���ݶ��壬������ĳһ��Դ���Ա���Щ��ɫ���� ������Դ��Ȩ�޵Ķ�Ӧ��ϵ
 * 
 * Ҳ����ֱ��ʹ��Spring�ṩ���� DefaultFilterInvocationSecurityMetadataSource
 * 
 * @author Taven.Li
 *
 */
@Service
public class WebSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private static Logger logger = LoggerFactory
			.getLogger(WebSecurityMetadataSource.class);

	private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	/**
	 * ��ʼ����Դ����
	 * 
	 * spring ���ø÷����ķ�ʽ��2�� ��ʽ1�������ϼ�ע�⣺
	 * 
	 * @PostConstruct
	 * 
	 *                ��ʽ2�������ļ��� init-method ����ָ���� <beans:bean
	 *                id="webSecurityMetadataSource" init-method="initResource"
	 *                class="com.tavenli.security.WebSecurityMetadataSource"/>
	 */
	@PostConstruct
	public void initResource() {

		resourceMap.clear();

		// ȡ�õ�ǰϵͳ���п��ý�ɫ
		List<Role> roles = this.roleService.getAvailableRoles();
		for (Role role : roles) {
			this.loadRole(role);

		}

		// Ϊ��������Ա���������ԴȨ��
		this.initSuperUserResource();

	}

	private void loadRole(Role role) {

		// ����� role ����Ϊ�Լ�����ģ�Ҫ�� UserDetailsService �е� SimpleGrantedAuthority
		// ������Ӧ
		// role ����Ҳ����ֱ��ʹ�ý�ɫ��
		ConfigAttribute ca = new SecurityConfig("ROLE_" + role.getId());
		// ȡ��ɫ����Щ��Դ��Ȩ��
		Set<Menu> menus = role.getMenus();
		for (Menu menu : menus) {
			String menuUrl = menu.getMenuUrl();
			if (StringUtils.isBlank(menuUrl)) {
				// ���ǲ˵���ַ������
				continue;
			}

			// �����URL��Դ��������ɫ����Դ��ϵ
			if (resourceMap.containsKey(menuUrl)) {

				resourceMap.get(menuUrl).add(ca);

			} else {

				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
				atts.add(ca);
				resourceMap.put(menuUrl, atts);

			}

		}

	}

	private void initSuperUserResource() {

		// ��ӳ�������Ա��ɫ
		// ROLE_SUPER ���Ȩ������Ҳ���Լ������
		ConfigAttribute superCA = new SecurityConfig("ROLE_SUPER");
		// ��������Ա�����в˵�Ȩ��
		List<Menu> menus = this.menuService.findAll();
		for (Menu menu : menus) {
			String menuUrl = menu.getMenuUrl();
			if (StringUtils.isBlank(menuUrl)) {
				// ���ǲ˵���ַ������
				continue;
			}

			if (resourceMap.containsKey(menuUrl)) {

				resourceMap.get(menuUrl).add(superCA);

			} else {

				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
				atts.add(superCA);
				resourceMap.put(menuUrl, atts);

			}

		}

	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		Iterator<String> ite = resourceMap.keySet().iterator();

		while (ite.hasNext()) {
			String resourceURL = ite.next();
			// AntPathRequestMatcher : ������Ant��Ŀ����һ�ּ��׶���·��ƥ����ԡ�
			// RegexRequestMatcher : ��� AntPathRequestMatcher �޷���������
			// ������ѡ��ʹ�ø�ǿ���RegexRequestMatcher����֧��ʹ��������ʽ��URL��ַ����ƥ��
			RequestMatcher requestMatcher = new AntPathRequestMatcher(
					resourceURL);
			if (requestMatcher.matches(request)) {
				return resourceMap.get(resourceURL);
			}
		}
		return null;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public void reloadResource() {
		//
		this.initResource();
	}

}
