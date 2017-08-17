package com.aioute.shiro.realm;

import com.aioute.constant.Constants;
import com.aioute.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// 认证数据库存储
@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {

    public Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

    @Resource
    private UpmService upmService;

    @Resource
    private CacheManager shiroCacheManager;

    public static final String HASH_ALGORITHM = "MD5";
    public static final int HASH_INTERATIONS = 1;
    private static final int SALT_SIZE = 8;

    public ShiroDbRealm() {
        // 认证
        super.setAuthenticationCacheName(Constants.SSO_CACHE);
        super.setAuthenticationCachingEnabled(false);
        // 授权
        super.setAuthorizationCacheName(Constants.AUTH_CACHE);
        super.setName(Constants.AUTH_REALM);
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principalCollection);
            SecurityUtils.getSubject().logout();
            return null;
        }

        UserModel shiroUser = (UserModel) principalCollection.getPrimaryPrincipal();
        String userId = shiroUser.getId();
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        // 添加角色及权限信息
        SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
        try {
            sazi.addRoles(upmService.getRolesAsString(userId));
            sazi.addStringPermissions(upmService.getPermissionsAsString(userId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return sazi;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        /*
         * String pwd = new String(upToken.getPassword()); if
         * (StringUtils.isNotBlank(pwd)) { pwd = DigestUtils.md5Hex(pwd); }
         */
        // 调用业务方法
        UserModel user = null;
        String userName = upToken.getUsername();
        try {
            user = upmService.findLoginUser(userName, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AuthenticationException(e);
        }

        if (user != null) {
            // 要放在作用域中的东西，请在这里进行操作
            // SecurityUtils.getSubject().getSession().setAttribute("c_user",
            // user);
            // byte[] salt = EncodeUtils.decodeHex(user.getSalt());

            Session session = SecurityUtils.getSubject().getSession(false);
            AuthenticationInfo authinfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            Cache<Object, Object> cache = shiroCacheManager.getCache(Constants.SSO_CACHE);
            cache.put(Constants.SSO_CACHE + "-" + userName, session.getId());
            return authinfo;
        }
        // 认证没有通过
        return null;
    }

}