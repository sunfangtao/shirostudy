package com.aioute.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyCasFilter extends CasFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        System.out.print("createToken");
        AuthenticationToken authenticationToken = super.createToken(request, response);
        //System.out.print(authenticationToken.getPrincipal().toString());
        return authenticationToken;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.print("onAccessDenied");
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.print("isAccessAllowed");
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        System.out.print("onLoginSuccess");
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request, ServletResponse response) {
        System.out.print("onLoginFailure");
        return super.onLoginFailure(token, ae, request, response);
    }

    @Override
    public void setFailureUrl(String failureUrl) {
        System.out.print("setFailureUrl");
        super.setFailureUrl(failureUrl);
    }
}
