package com.aioute.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("loginController")
public class LoginController {

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequiresPermissions("sss")
    @RequestMapping("login")
    public void login(HttpServletRequest req, HttpServletResponse res) {


        int i = 0;
    }
}
