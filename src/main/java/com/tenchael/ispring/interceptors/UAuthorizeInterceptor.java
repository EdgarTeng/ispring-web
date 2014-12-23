package com.tenchael.ispring.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * �û����ĵ�������
 * 
 * @author Taven
 *
 */
public class UAuthorizeInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory
			.getLogger(UAuthorizeInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// �������������һЩ���ش�����Ȼ��Ҳ����ʲô�������������ֻ��ע Spring Security�����Բ���ע
		// HandlerInterceptor �ӿ�

		// String path = request.getContextPath();
		// response.sendRedirect(path + "/index");

		return true;

	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
