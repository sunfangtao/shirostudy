package com.aioute.controller;

import com.aioute.controller.base.BaseController;
import com.sft.model.Permission;
import com.sft.model.Role;
import com.sft.service.RolePermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("loginController")
public class LoginController extends BaseController {

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequestMapping("login")
    public void login(HttpServletRequest req, HttpServletResponse res) {
        Role role = new Role();
        role.setName("超级管理员");
        role.setRemarks("没有备注");
        role.setCreate_by("111");
        boolean i = rolePermissionService.addRole(role);
        role.setName("普通管理员");
        role.setRemarks("没有备注");
        role.setCreate_by("111");
        rolePermissionService.addRole(role);
        role.setName("用户");
        role.setRemarks("没有备注");
        role.setCreate_by("111");
        rolePermissionService.addRole(role);

        Permission permission = new Permission();
        permission.setName("更新");
        permission.setPermission("share:update");
        permission.setServer_id("1");
        permission.setCreate_by("111");
        boolean j = rolePermissionService.addPermission(permission);
        permission = new Permission();
        permission.setName("添加");
        permission.setPermission("share:add");
        permission.setServer_id("1");
        permission.setCreate_by("111");
        rolePermissionService.addPermission(permission);
        permission = new Permission();
        permission.setName("删除");
        permission.setPermission("share:delete");
        permission.setServer_id("1");
        permission.setCreate_by("111");
        rolePermissionService.addPermission(permission);


    }

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequiresPermissions("sss")
    @RequestMapping("login2")
    public void login2(HttpServletRequest req, HttpServletResponse res) {
        int i = 0;
    }
}
