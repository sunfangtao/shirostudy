package com.aioute.controller;

import com.sft.model.Permission;
import com.sft.model.Role;
import com.sft.model.UserModel;
import com.sft.model.bean.PermissionBean;
import com.sft.model.bean.RoleBean;
import com.sft.service.RolePermissionService;
import com.sft.service.UserService;
import com.sft.util.CloudError;
import com.sft.util.PagingUtil;
import com.sft.util.SendAppJSONUtil;
import com.sft.util.SendPlatJSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("rolePermission")
public class RolePermissionController {

    @Resource
    private UserService userService;
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 新建角色 done
     *
     * @param name
     * @param remarks
     */
    @ResponseBody
    @RequestMapping("addRole")
    public String addRole(String name, String remarks, String del_flag) {
        String returnStr = "";
        Role role = new Role();

        if (StringUtils.hasText(remarks)) {
            role.setRemarks(remarks);
        }
        if (StringUtils.hasText(del_flag) && !del_flag.equals("0")) {
            role.setDel_flag(1);
        }
        if (StringUtils.hasText(name)) {
            role.setName(name);
        } else {
            // 必须有角色名称
            return SendAppJSONUtil.getRequireParamsMissingObject("必须填写角色名称!");
        }
        role.setCreate_by((String) SecurityUtils.getSubject().getSession().getAttribute("userId"));
        boolean result = rolePermissionService.addRole(role);
        if (result) {
            // 操作成功
            returnStr = SendAppJSONUtil.getNormalString("创建成功!");
        } else {
            // 角色名称不能重复
            returnStr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "角色名称不能重复!");
        }
        return returnStr;
    }

    /**
     * 获取所有角色 done
     */
    @ResponseBody
    @RequestMapping("roles")
    public String roles(HttpServletRequest req) {
        int page = PagingUtil.getPage(req);
        int pageSize = PagingUtil.getPageSize(req);
        Map<String, String> whereMap = new HashMap<String, String>();
        whereMap.put("name", req.getParameter("name"));
        whereMap.put("del_flag", req.getParameter("del_flag"));
        List<RoleBean> roleList = rolePermissionService.getRoles(whereMap, page, pageSize);
        int count = rolePermissionService.getRoleCount(whereMap);
        return SendPlatJSONUtil.getPageJsonString(0, "", count, roleList);
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
                return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.NODATA.getValue(), "您创建的用户中没有此用户!");
            }
        }

        List<RoleBean> roleList = rolePermissionService.getRoles(userId);

        return SendAppJSONUtil.getNormalString(roleList);
    }

    /**
     * 更新角色 done
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
                return SendAppJSONUtil.getNormalString("更新成功!");
            } else {
                // 角色名称不能重复
                String sr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "角色名称不能重复!");
                return sr;
            }
        } else {
            // 必须有角色编号
            return SendAppJSONUtil.getRequireParamsMissingObject("缺少角色编号!");
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
                        return SendAppJSONUtil.getNormalString("更新成功!");
                    } else {
                        return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "更新失败!");
                    }
                } else {
                    return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.NODATA.getValue(), "您创建的用户中没有此用户!");
                }
            } else {
                return SendAppJSONUtil.getRequireParamsMissingObject("缺少用户信息!");
            }
        } else {
            // 必须有角色编号
            return SendAppJSONUtil.getRequireParamsMissingObject("请选择修改的角色!");
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
                    return SendAppJSONUtil.getNormalString("更新成功!");
                } else {
                    return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "更新失败!");
                }
            } else {
                return SendAppJSONUtil.getRequireParamsMissingObject("缺少角色信息!");
            }
        } else {
            // 必须有权限列表
            return SendAppJSONUtil.getRequireParamsMissingObject("请选择修改的权限!");
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
        String permissionStr = req.getParameter("permission");
        String url = req.getParameter("url");
        String type = req.getParameter("type");
        String del_flag = req.getParameter("del_flag");

        if (StringUtils.hasText(moduleId)) {
            if (StringUtils.hasText(name)) {
                if (StringUtils.hasText(permissionStr)) {
                    permission.setModule_id(moduleId);
                    permission.setName(name);
                    permission.setRemarks(remarks);
                    permission.setPermission(permissionStr);
                    permission.setUrl(url);
                    permission.setType(type);
                    if ("1".equals(del_flag)) {
                        permission.setDel_flag(1);
                    }
                    permission.setCreate_by((String) SecurityUtils.getSubject().getSession().getAttribute("userId"));

                    if (rolePermissionService.addPermission(permission)) {
                        return SendAppJSONUtil.getNormalString("添加成功!");
                    } else {
                        return SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "添加失败!");
                    }
                } else {
                    return SendAppJSONUtil.getRequireParamsMissingObject("请填写权限标识!");
                }
            } else {
                return SendAppJSONUtil.getRequireParamsMissingObject("请填写权限别名!");
            }
        } else {
            // 必须有模块分类
            return SendAppJSONUtil.getRequireParamsMissingObject("请选择所属模块!");
        }
    }

    /**
     * 更新角色 done
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("updatePermission")
    public String updatePermission(HttpServletRequest req, HttpServletResponse res) {
        Permission permission = new Permission();
        String permissionId = req.getParameter("permissionId");
        if (StringUtils.hasText(permissionId)) {
            permission.setId(permissionId);
            String moduleId = req.getParameter("moduleId");
            String name = req.getParameter("name");
            String remarks = req.getParameter("remarks");
            String del_flag = req.getParameter("del_flag");
            String type = req.getParameter("type");
            String url = req.getParameter("url");
            String permissionStr = req.getParameter("permission");
            if (StringUtils.hasText(moduleId)) {
                permission.setModule_id(moduleId);
            }
            if (StringUtils.hasText(name)) {
                permission.setName(name);
            }
            if (StringUtils.hasText(remarks)) {
                permission.setRemarks(remarks);
            }
            if (StringUtils.hasText(del_flag) && !del_flag.equals("0")) {
                permission.setDel_flag(1);
            }
            if (StringUtils.hasText(type)) {
                permission.setType(type);
            }
            if (StringUtils.hasText(url)) {
                permission.setUrl(url);
            }
            if (StringUtils.hasText(permissionStr)) {
                permission.setPermission(permissionStr);
            }

            String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            if (rolePermissionService.updatePermission(permission, userId)) {
                return SendAppJSONUtil.getNormalString("更新成功!");
            } else {
                // 角色名称不能重复
                String sr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "角色名称不能重复!");
                return sr;
            }
        } else {
            // 必须有权限编号
            return SendAppJSONUtil.getRequireParamsMissingObject("缺少权限编号!");
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
        String isHas = req.getParameter("isHas");

        if (StringUtils.hasText(roleId)) {
            List<PermissionBean> permissionList = rolePermissionService.getRolePermissionsList(roleId);
            if (isHas != null) {
                // 获取当前用户拥有的所有系统权限
                List<PermissionBean> allPermissionList = rolePermissionService.getPermissions();
                Map<String, List<PermissionBean>> permissionBeanMap = new TreeMap<String, List<PermissionBean>>();
                int length = allPermissionList.size();
                for (int i = 0; i < length; i++) {
                    PermissionBean permission = allPermissionList.get(i);
                    if (permissionList.contains(permission)) {
                        permission.setHas(true);
                    }
                    List<PermissionBean> list = permissionBeanMap.get(permission.getModule());
                    if (list == null) {
                        list = new ArrayList<PermissionBean>();
                        permissionBeanMap.put(permission.getModule(), list);
                    }
                    list.add(permission);
                }
                String s = SendAppJSONUtil.getNormalString(permissionBeanMap);
                return s;
            }
            return SendAppJSONUtil.getNormalString(permissionList);
        } else {
            // 必须有角色信息
            return SendAppJSONUtil.getRequireParamsMissingObject("请选择角色!");
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
        List<PermissionBean> permissionList = rolePermissionService.getUrlPermissions();
        return SendAppJSONUtil.getNormalString(permissionList);
    }

    /**
     * 获取系统所有权限
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("getAllPermissions")
    public String getAllPermissions(HttpServletRequest req, HttpServletResponse res) {
        int page = PagingUtil.getPage(req);
        int pageSize = PagingUtil.getPageSize(req);
        Map<String, String> whereMap = new HashMap<String, String>();
        whereMap.put("name", req.getParameter("name"));
        whereMap.put("url", req.getParameter("url"));
        whereMap.put("type", req.getParameter("type"));
        whereMap.put("permission", req.getParameter("permission"));
        whereMap.put("del_flag", req.getParameter("del_flag"));
        List<PermissionBean> permissionList = rolePermissionService.getPermissions(whereMap, page, pageSize);
        int count = rolePermissionService.getPermissionCount(whereMap);
        return SendPlatJSONUtil.getPageJsonString(0, "", count, permissionList);
    }

}
