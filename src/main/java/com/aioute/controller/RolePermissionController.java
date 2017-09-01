package com.aioute.controller;

import com.sft.model.Permission;
import com.sft.model.Role;
import com.sft.model.UserModel;
import com.sft.service.RolePermissionService;
import com.sft.service.UserService;
import com.sft.util.CloudError;
import com.sft.util.SendJSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("rolePermission")
public class RolePermissionController {

    @Resource
    private UserService userService;
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 新建角色
     *
     * @param name
     * @param remarks
     */
    @ResponseBody
    @RequestMapping("addRole")
    public String addRole(String name, String remarks) {
        String returnStr = "";
        Role role = new Role();

        if (StringUtils.hasText(remarks)) {
            role.setRemarks(remarks);
        }
        if (StringUtils.hasText(name)) {
            role.setName(name);
        } else {
            // 必须有角色名称
            return SendJSONUtil.getRequireParamsMissingObject("必须填写角色名称!");
        }
        role.setCreate_by((String) SecurityUtils.getSubject().getSession().getAttribute("userId"));
        boolean result = rolePermissionService.addRole(role);
        if (result) {
            // 操作成功
            returnStr = SendJSONUtil.getNormalString("创建成功!");
        } else {
            // 角色名称不能重复
            returnStr = SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "角色名称不能重复!");
        }
        return returnStr;
    }

    /**
     * 获取用户的角色
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("userRoles")
    public String userRoles(HttpServletRequest req, HttpServletResponse res) {
        String userId = req.getParameter("userId");
        String loginUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
        if (!StringUtils.hasText(userId)) {
            // 没有userId,就默认使用当前登录用户
            userId = loginUserId;
        } else {
            // 必须是当前用户的子用户
            List<UserModel> subUserList = userService.getSubUserById(loginUserId);
            UserModel userModel = new UserModel();
            userModel.setId(userId);
            if (!subUserList.contains(userModel)) {
                return SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.NODATA.getValue(), "您创建的用户中没有此用户!");
            }
        }

        List<Role> roleList = rolePermissionService.getRoles(userId);

        return SendJSONUtil.getNormalString(roleList);
    }

    /**
     * 更新角色
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("updateRole")
    public String updateRole(HttpServletRequest req, HttpServletResponse res) {
        Role role = new Role();
        String roleId = req.getParameter("roleId");
        if (StringUtils.hasText(roleId)) {
            role.setId(roleId);
            String name = req.getParameter("name");
            String remarks = req.getParameter("remarks");
            String del_flag = req.getParameter("del_flag");
            if (StringUtils.hasText(name)) {
                role.setName(name);
            }
            if (StringUtils.hasText(remarks)) {
                role.setRemarks(remarks);
            }
            if (StringUtils.hasText(del_flag) && !del_flag.equals("0")) {
                role.setDel_flag(1);
            }
            if (rolePermissionService.updateRole(role)) {
                return SendJSONUtil.getNormalString("更新成功!");
            } else {
                // 角色名称不能重复
                return SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "角色名称不能重复!");
            }
        } else {
            // 必须有角色编号
            return SendJSONUtil.getRequireParamsMissingObject("缺少角色编号!");
        }
    }

    /**
     * 更新用户的角色
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("updateUserRoles")
    public String updateUserRoles(HttpServletRequest req, HttpServletResponse res) {
        String roleIds = req.getParameter("roleIds");
        String userId = req.getParameter("userId");
        String loginUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
        if (StringUtils.hasText(roleIds)) {
            if (StringUtils.hasText(userId)) {
                List<UserModel> subUserList = userService.getSubUserById(loginUserId);
                UserModel userModel = new UserModel();
                userModel.setId(userId);
                if (subUserList.contains(userModel)) {
                    List<String> roleIdList = Arrays.asList(roleIds.split("@"));
                    if (rolePermissionService.updateUserRoles(userId, roleIdList, loginUserId)) {
                        return SendJSONUtil.getNormalString("更新成功!");
                    } else {
                        return SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "更新失败!");
                    }
                } else {
                    return SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.NODATA.getValue(), "您创建的用户中没有此用户!");
                }
            } else {
                return SendJSONUtil.getRequireParamsMissingObject("缺少用户信息!");
            }
        } else {
            // 必须有角色编号
            return SendJSONUtil.getRequireParamsMissingObject("请选择修改的角色!");
        }
    }

    /**
     * 更新角色权限
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("updateRolePermissions")
    public String updateRolePermissions(HttpServletRequest req, HttpServletResponse res) {
        String permissionIds = req.getParameter("permissionIds");
        String roleId = req.getParameter("roleId");
        if (StringUtils.hasText(permissionIds)) {
            if (StringUtils.hasText(roleId)) {
                List<String> permissionStrList = Arrays.asList(permissionIds.split("@"));
                if (rolePermissionService.updateRolePermission(roleId, permissionStrList)) {
                    return SendJSONUtil.getNormalString("更新成功!");
                } else {
                    return SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "更新失败!");
                }
            } else {
                return SendJSONUtil.getRequireParamsMissingObject("缺少角色信息!");
            }
        } else {
            // 必须有权限列表
            return SendJSONUtil.getRequireParamsMissingObject("请选择修改的权限!");
        }
    }

    /**
     * 新建权限
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("addPermission")
    public String addPermission(HttpServletRequest req, HttpServletResponse res) {
        Permission permission = new Permission();

        String moduleId = req.getParameter("moduleId");
        String name = req.getParameter("name");
        String remarks = req.getParameter("remarks");

        if (StringUtils.hasText(moduleId)) {
            if (StringUtils.hasText(name)) {
                permission.setModule_id(moduleId);
                permission.setName(name);
                permission.setRemarks(remarks);

                if (rolePermissionService.addPermission(permission)) {
                    return SendJSONUtil.getNormalString("添加成功!");
                } else {
                    return SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "添加失败!");
                }
            } else {
                return SendJSONUtil.getRequireParamsMissingObject("请填写权限别名!");
            }
        } else {
            // 必须有模块分类
            return SendJSONUtil.getRequireParamsMissingObject("请选择所属模块!");
        }
    }

    /**
     * 获取角色的权限
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("getRolePermissions")
    public String getRolePermissions(HttpServletRequest req, HttpServletResponse res) {
        String roleId = req.getParameter("roleId");

        if (StringUtils.hasText(roleId)) {
            List<Permission> permissionList = rolePermissionService.getRolePermissionsList(roleId);
            return SendJSONUtil.getNormalString(permissionList);
        } else {
            // 必须有角色信息
            return SendJSONUtil.getRequireParamsMissingObject("请选择角色!");
        }
    }

    /**
     * 获取资源权限
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("getResourcePermissions")
    public String getResourcePermissions(HttpServletRequest req, HttpServletResponse res) {
        List<Permission> permissionList = rolePermissionService.getUrlPermissions();
        return SendJSONUtil.getNormalString(permissionList);
    }

}
