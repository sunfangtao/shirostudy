/**
 * @Project:crm
 * @Title:PasswordShiroRealm.java
 * @Author:Riozenc
 * @Datetime:2016年10月16日 下午8:04:07
 */
package com.aioute.shiro.realm;

import com.aioute.model.UserModel;
import com.aioute.service.RolePermissionService;
import com.aioute.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PasswordShiroRealm extends CasRealm {

    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private UserService userService;

    protected final Map<String, SimpleAuthorizationInfo> authorizationMap = new ConcurrentHashMap<String, SimpleAuthorizationInfo>();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 登录成功授权操作
        String account = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = null;
        if (authorizationInfo == null) {
            authorizationInfo = new SimpleAuthorizationInfo();
            List<String> permissions = rolePermissionService.getPermissions(account);
            if (permissions != null) {
                authorizationInfo.addStringPermissions(permissions);
            }
            List<String> roles = rolePermissionService.getRoles(account);
            if (roles != null) {
                authorizationInfo.addRoles(rolePermissionService.getRoles(account));
            }
            authorizationMap.put(account, authorizationInfo);
        }
        authorizationInfo.addStringPermission("sss");
        return authorizationInfo;
    }

    // 认证成功回调
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        AuthenticationInfo authc = super.doGetAuthenticationInfo(authenticationToken);

        String account = (String) authc.getPrincipals().getPrimaryPrincipal();
        UserModel user = userService.getUserByAccount(account);
        SecurityUtils.getSubject().getSession().setAttribute("user", user);

        return authc;
    }
}
