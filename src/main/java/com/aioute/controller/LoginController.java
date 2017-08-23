package com.aioute.controller;

import com.aioute.controller.base.BaseController;
import com.aioute.util.HttpClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("loginController")
public class LoginController extends BaseController {

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequiresPermissions("sssw")
    @RequestMapping("login")
    public void login(HttpServletRequest req, HttpServletResponse res, RedirectAttributes model) {
        try {
            new HttpClient(req, res).sendByPost("http://10.10.29.180:8080/CustomRail/rail/queryRail");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @ModelAttribute
    @RequiresPermissions("sss")
    @RequestMapping("login2")
    public void login2(@ModelAttribute("userId") String userId, HttpServletRequest req, HttpServletResponse res) {
        int i = 0;
    }
}
