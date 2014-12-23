package com.tenchael.ispring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ʵ�ִ���֤��ĵ�¼��֤�������ﻹ����ʵ�ֵ�¼��֤�������������պʹ���
 * 
 * ͨ��ָ�� filterProcessesUrl ���ԣ�ָ����Url�ᱻSpring Security���أ���¼������ֱ���ύ�����Url (�����ָ����Ĭ��UrlΪ��/j_spring_security_check)
 * 
 * Ҫ�Զ����˳���¼�ĵ�ַ������ͨ������ logout-url ���ԣ�(Ĭ��UrlΪ��/j_spring_security_logout)
 * <logout logout-url="/u/logout" logout-success-url="/logout.jsp" invalidate-session="true"/>
 * 
 * invalidate-session ���Ϊtrue����ע����ʱ������ٻỰ
 * 
 * @author Taven.Li
 *
 */
public class ValidateCodeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static Logger logger = LoggerFactory.getLogger(ValidateCodeUsernamePasswordAuthenticationFilter.class);
	
	private String userNameParameter = "userName";
	
	private String passWordParameter = "passWord";
	
	/**
	 * �Ƿ�ֻ����POST��ʽ�ύ����֤����
	 */
	private boolean postOnly = true;
	
	/**
	 * �Ƿ���Ҫ��֤��
	 */
	private boolean checkValidateCode = true;
		
	/**
	 * ��֤���Ӧ�ı���������
	 */
	private String validateCodeParameter = "verifyCode";
	
	/**
	 * ��֤�뱣����session�е�����
	 */
	private String validateCodeSessionName = "verifyCode";
	
	/**
	 * ��֤�ɹ�����ת��ҳ��
	 * ע�⣺��ַ������ / �� http ��ͷ��URL��ַ
	 */
	private String successUrl = "/main";
	/**
	 * 
	 */
	private String failureUrl = "/login";

	public void init(){
		//���ý��ղ����ı����ƣ�Ĭ���� j_username �� j_password
		//�����������ֹ�ָ����Ҳ������Spring������ע������
		this.setUsernameParameter(userNameParameter);
		this.setPasswordParameter(passWordParameter);
		
		//��֤�ɹ�����ת��ҳ��
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl(successUrl);		
		this.setAuthenticationSuccessHandler(successHandler);
		
		//��֤ʧ�ܣ���ת��ҳ��
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setDefaultFailureUrl(failureUrl);
		this.setAuthenticationFailureHandler(failureHandler);
		
	}
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
        	//�������ֱ���׳��쳣��Ҳ����ֱ����ת
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            //��
            //request.getRequestDispatcher("/errorPage").forward(request, response);
        }
        
        //ȡ�û�������ǰ�����ñ����ʽ
        //request.setCharacterEncoding("UTF-8");
        
        //�Ƿ���ҪУ����֤��
        if(checkValidateCode){
        	checkValidateCode(request);
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        
        return this.getAuthenticationManager().authenticate(authRequest);
    }
	
	protected void checkValidateCode(HttpServletRequest request) {
		String sessionValidateCode = obtainSessionValidateCode(request);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		//��֤�����벻��Ϊ�գ������ִ�Сд
		if (StringUtils.isBlank(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException(messages.getMessage("validateCode.notEquals"));
		}
	}


	public boolean isPostOnly() {
		return postOnly;
	}

	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
	
	private String obtainValidateCodeParameter(HttpServletRequest request) {
		return request.getParameter(validateCodeParameter);
	}

	protected String obtainSessionValidateCode(HttpServletRequest request) {
		Object sessionCode = request.getSession().getAttribute(validateCodeSessionName);
		return null == sessionCode ? "" : sessionCode.toString();
	}

	
	public String getUserNameParameter() {
		return userNameParameter;
	}

	public void setUserNameParameter(String userNameParameter) {
		this.userNameParameter = userNameParameter;
	}

	public String getPassWordParameter() {
		return passWordParameter;
	}

	public void setPassWordParameter(String passWordParameter) {
		this.passWordParameter = passWordParameter;
	}

	public boolean isCheckValidateCode() {
		return checkValidateCode;
	}

	public void setCheckValidateCode(boolean checkValidateCode) {
		this.checkValidateCode = checkValidateCode;
	}

	public String getValidateCodeParameter() {
		return validateCodeParameter;
	}

	public void setValidateCodeParameter(String validateCodeParameter) {
		this.validateCodeParameter = validateCodeParameter;
	}

	public String getValidateCodeSessionName() {
		return validateCodeSessionName;
	}

	public void setValidateCodeSessionName(String validateCodeSessionName) {
		this.validateCodeSessionName = validateCodeSessionName;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}
	
	
}
