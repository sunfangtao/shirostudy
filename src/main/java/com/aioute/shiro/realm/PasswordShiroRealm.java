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
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class PasswordShiroRealm extends AuthorizingRealm {

    private Logger logger = Logger.getLogger("PasswordShiroRealm");

    @Autowired
    private SessionManager sessionManager;
//    @Resource
//    private UserDao userDao;
    @SuppressWarnings("unused")
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // TODO Auto-generated method stub

        // 登录成功授权操作
        // Principal principal = (Principal)
        // getAvailablePrincipal(principalCollection);

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        logger.info("");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        if (sessionManager instanceof DefaultWebSessionManager) {
            DefaultWebSessionManager defaultWebSessionManager = (DefaultWebSessionManager) sessionManager;

            SessionDAO sessionDAO = defaultWebSessionManager.getSessionDAO();
            Collection<Session> sessions = sessionDAO.getActiveSessions();

            for (Session session : sessions) {
                // 清除该用户以前登录时保存的session
                if (token.getPrincipal().equals(
                        String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                    sessionDAO.delete(session);
                    logger.info("已在线的用户：" + token.getPrincipal() + "被踢出");
                }
            }
        }

        if (username != null && !"".equals(username.trim())) {
//            UserVO user = userDao.obtainUserInfoByPhone(username);
//            if (user != null) {
//                String password = user.getPassword();
//                if(password!=null) {
//                    return new SimpleAuthenticationInfo(username, password.substring(32), ByteSource.Util.bytes(user
//                            .getPassword().substring(0, 32)), getName());
//                }else {
//                    return null;
//                }
//            }
        }
        return null;
    }
}
