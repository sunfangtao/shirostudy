package com.aioute.controller;

import com.sft.model.UserModel;
import com.sft.service.UserService;
import com.sft.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private DefaultPasswordEncoder passwordEncoder;

    /**
     * 增加贝格用户(不包含子用户)
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("addUser")
    public String addUser(HttpServletRequest req, HttpServletResponse res) {
        String login_name = req.getParameter("login_name");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String remarks = req.getParameter("remarks");

        if (!StringUtils.hasText(login_name)) {
            return SendAppJSONUtil.getRequireParamsMissingObject("请输入账号!");
        }
        if (!StringUtils.hasText(password)) {
            return SendAppJSONUtil.getRequireParamsMissingObject("请输入密码!");
        }
        if (!StringUtils.hasText(name)) {
            return SendAppJSONUtil.getRequireParamsMissingObject("请输入名称!");
        }
        UserModel userModel = new UserModel();
        userModel.setLogin_name(login_name);
        userModel.setParent_id_set("1");
        userModel.setParent_id("1");
        userModel.setId(UUID.randomUUID().toString());
        userModel.setName(name);
        userModel.setPhone(phone);
        userModel.setPassword(passwordEncoder.encode(password));
        userModel.setCreate_by("1");
        userModel.setCreate_date(DateUtil.getCurDate());
        userModel.setRemarks(remarks);

        if (userService.addUser(userModel)) {
            return SendAppJSONUtil.getNormalString("操作成功!");
        } else {
            return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "操作失败!");
        }
    }

    /**
     * 获取用户
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("getUser")
    public String getUser(HttpServletRequest req, HttpServletResponse res) {
        int page = PagingUtil.getPage(req);
        int pageSize = PagingUtil.getPageSize(req);
        String loginUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");

        Map<String, String> whereMap = new HashMap<String, String>();
        whereMap.put("name", req.getParameter("name"));
        whereMap.put("phone", req.getParameter("phone"));
        List<UserModel> userModelList = userService.getSubUserById(whereMap, loginUserId, page, pageSize);
        int count = userService.getSubUserCount(whereMap, loginUserId);
        return SendPlatJSONUtil.getPageJsonString(0, "", count, userModelList);
    }

    /**
     * 获取用户
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("updateUser")
    public String updateUser(HttpServletRequest req, HttpServletResponse res) {
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String remarks = req.getParameter("remarks");
        String userId = req.getParameter("userId");
        String loginUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");

        UserModel userModel = new UserModel();
        userModel.setLogin_name(userService.getUserById(userId).getLogin_name());
        if (StringUtils.hasText(password))
            userModel.setPassword(password);
        userModel.setName(name);
        userModel.setPhone(phone);
        userModel.setRemarks(remarks);

        if (userService.updateUser(userModel, loginUserId)) {
            return SendAppJSONUtil.getNormalString("更新成功！");
        } else {
            return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "更新失败！");
        }
    }
}
