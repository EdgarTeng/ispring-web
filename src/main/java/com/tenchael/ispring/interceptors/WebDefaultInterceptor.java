package com.tenchael.ispring.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * ȫվ������
 * 
 * ������д���� preHandle ��postHandle ��afterCompletion ������ʵ�ֶ�ȫ��ҳ�������һЩ�߼�����
 * 
 * @author Taven
 *
 */
public class WebDefaultInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory
			.getLogger(WebDefaultInterceptor.class);

}
