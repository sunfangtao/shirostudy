package com.aioute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("loginController")
public class LoginController {

    /**
     * 用户登录
     */
    @RequestMapping("login")
    public String login2() {
        return "redirect:/index.jsp";
    }

}
