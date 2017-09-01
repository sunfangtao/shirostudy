package com.aioute.controller;

import com.sft.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("userController")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequestMapping("addUser")
    public void addUser(HttpServletRequest req, HttpServletResponse res) {

    }

}
