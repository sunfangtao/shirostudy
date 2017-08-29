package com.aioute.controller;

import com.aioute.controller.base.BaseController;
import com.sft.service.FilterChainDefinitionsService;
import com.sft.service.RolePermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("loginController")
public class LoginController extends BaseController {

    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequestMapping("login")
    public void login(HttpServletRequest req, HttpServletResponse res) {
        filterChainDefinitionsService.reloadFilterChains();
    }

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequiresPermissions("ss")
    @RequestMapping("login2")
    public void login2(HttpServletRequest req, HttpServletResponse res) {
        int i = 0;
    }

    /**
     * 用户未授权
     *
     * @param req
     * @param res
     */
    @RequestMapping("unauthorized")
    public void unauthorized(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "-998");
        map.put("message", "无权限");
        writeJson(map, res);
    }
}
