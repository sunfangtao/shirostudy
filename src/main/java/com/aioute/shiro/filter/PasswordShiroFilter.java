/**
 * Title:PasswordShiroFilter.java
 * Author:czy
 * Datetime:2016年10月24日 下午3:21:01
 */
package com.aioute.shiro.filter;

import com.aioute.shiro.token.UsernamePasswordToken;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class PasswordShiroFilter extends FormAuthenticationFilter {

	private Logger logger = Logger.getLogger(PasswordShiroFilter.class);

	private static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	public boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);

		String host = "";
		if (request instanceof HttpServletRequest) {
			HttpServletRequest r = (HttpServletRequest) request;
			String remoteAddr = r.getHeader("X-Real-IP");
			if (!isBlank(remoteAddr)) {
				remoteAddr = r.getHeader("X-Forwarded-For");
			} else if (!isBlank(remoteAddr)) {
				remoteAddr = r.getHeader("Proxy-Client-IP");
			} else if (!isBlank(remoteAddr)) {
				remoteAddr = r.getHeader("WL-Proxy-Client-IP");
			}
			host = remoteAddr != null ? remoteAddr : request.getRemoteAddr();
		}

		String captcha = WebUtils.getCleanParam(request, DEFAULT_CAPTCHA_PARAM);
		boolean mobile = WebUtils.isTrue(request, DEFAULT_MOBILE_PARAM);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		logger.info("onLoginFailure" + e.getMessage());
		return super.onLoginFailure(token, e, request, response);
	}

}
