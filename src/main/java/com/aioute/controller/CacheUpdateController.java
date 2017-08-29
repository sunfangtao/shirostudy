package com.aioute.controller;

import com.sft.service.FilterChainDefinitionsService;
import com.sft.util.SendJSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("cacheUpdate")
public class CacheUpdateController {

    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;
    @Resource
    private EhCacheManager shiroCacheManager;

    /**
     * shiro认证授权缓存
     *
     * @param req
     * @param res
     */
    @RequestMapping("shiroCache")
    public void shiroCache(HttpServletRequest req, HttpServletResponse res) {
        try {
            String account = req.getParameter("account");
            if (account != null && account.length() > 0) {
                clearAuthorizationInfo(account);
                String returnJson = SendJSONUtil.getNormalString(null);
                res.getWriter().write(returnJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 资源权限缓存
     *
     * @param req
     * @param res
     */
    @RequestMapping("resourceCache")
    public void resourceCache(HttpServletRequest req, HttpServletResponse res) {
        try {
            filterChainDefinitionsService.reloadFilterChains();
            String returnJson = SendJSONUtil.getNormalString(null);
            res.getWriter().write(returnJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除当前用户的授权信息
     */
    private void clearAuthorizationInfo(String userName) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            Cache<Object, Object> cache = shiroCacheManager.getCache("authorizationCache");
            cache.remove(userName);
        }
    }

}
