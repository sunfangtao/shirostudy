/**
 * @Project:crm
 * @Title:PasswordShiroRealm.java
 * @Author:Riozenc
 * @Datetime:2016年10月16日 下午8:04:07
 */
package com.aioute.shiro.realm;

import com.aioute.shiro.password.PasswordHelper;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PasswordShiroRealm extends CasRealm {

    private Logger logger = Logger.getLogger("PasswordShiroRealm");

    @Autowired
    private SessionManager sessionManager;
    //    @Resource
//    private UserDao userDao;
    @SuppressWarnings("unused")
    @Autowired
    private PasswordHelper passwordHelper;

    protected final Map<String, SimpleAuthorizationInfo> roles = new ConcurrentHashMap<String, SimpleAuthorizationInfo>();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 登录成功授权操作
        String account = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = null;
//        if (authorizationInfo == null) {
//            authorizationInfo = new SimpleAuthorizationInfo();
//            List<String> permissions = roleService.getPermissions(account);
//            authorizationInfo.addStringPermissions(permissions);
//            authorizationInfo.addRoles(roleService.getRoles(account));
//            roles.put(account, authorizationInfo);
//        }

        return authorizationInfo;
    }

    // 认证成功回调
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        AuthenticationInfo authc = super.doGetAuthenticationInfo(authenticationToken);

        logger.info((String) authc.getPrincipals().getPrimaryPrincipal());
//        String account = (String) authc.getPrincipals().getPrimaryPrincipal();
//
//        User user = userService.getUserByAccount(account);
//
//        SecurityUtils.getSubject().getSession().setAttribute("user", user);

        return authc;
    }
}
