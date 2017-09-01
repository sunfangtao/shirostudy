package com.aioute.controller;

import com.sft.service.FilterChainDefinitionsService;
import com.sft.service.RolePermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("loginController")
public class LoginController {

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
    @RequestMapping("login2")
    public void login2(HttpServletRequest req, HttpServletResponse res) {
        int i = 0;
    }

}
