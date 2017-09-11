package com.aioute.service.impl;

import com.aioute.dao.RolePermissionDao;
import com.aioute.service.RolePermissionService;
import com.sft.model.Permission;
import com.sft.model.Role;
import com.sft.model.bean.PermissionBean;
import com.sft.model.bean.RoleBean;
import com.sft.service.RoleService;
import com.sft.util.DateUtil;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private RolePermissionDao rolePermissionDao;
    @Resource
    private RoleService roleService;

    public boolean addRole(Role role) {
        if (role == null) {
            return false;
        }
        if (role.getDel_flag() != 0) {
            role.setDel_flag(1);
        }
        role.setCreate_date(DateUtil.getCurDate());

        boolean result = rolePermissionDao.addRole(role);

        if (result) {
            //  增加超级管理员的默认角色
            List<String> roleIdList = new ArrayList<String>();
            roleIdList.add(role.getId());
            roleService.updateUserRoles("1", roleIdList, "0");
            roleService.updateUserRoles("2", roleIdList, "0");
        }
        return result;
    }

    public List<RoleBean> getRoles(Map<String, String> whereMap, int page, int pageSize) {
        return rolePermissionDao.getRoles(whereMap, page, pageSize);
    }

    public int getRoleCount(Map<String, String> whereMap) {
        return rolePermissionDao.getRoleCount(whereMap);
    }

    public boolean updateRole(Role role) {
        if (role == null) {
            return false;
        }
        if (StringUtils.hasText(role.getId())) {
            return rolePermissionDao.updateRole(role);
        }
        return false;
    }

    public boolean updateRolePermission(String roleId, List<String> permissionId) {
        if (StringUtils.hasText(roleId)) {
            return rolePermissionDao.updateRolePermission(roleId, permissionId);
        }
        return false;
    }

    public boolean addPermission(Permission permission) {
        if (permission == null) {
            return false;
        }
        if (permission.getDel_flag() != 0) {
            permission.setDel_flag(1);
        }
        permission.setId(UUID.randomUUID().toString());
        permission.setCreate_date(DateUtil.getCurDate());

        return rolePermissionDao.addPermission(permission);
    }

    public boolean updatePermission(Permission permission, String by) {
        if (permission == null) {
            return false;
        }
        permission.setUpdate_by(by);
        permission.setUpdate_date(DateUtil.getCurDate());
        return rolePermissionDao.updatePermission(permission);
    }

    public List<String> getPermissions(String userId) {
        if (StringUtils.hasText(userId)) {
            return rolePermissionDao.getPermissions(userId);
        }
        return null;
    }

    public List<PermissionBean> getPermissions(Map<String, String> whereMap, int page, int pageSize) {
        return rolePermissionDao.getPermissions(whereMap, page, pageSize);
    }

    public List<PermissionBean> getPermissions() {
        return rolePermissionDao.getPermissions();
    }

    public int getPermissionCount(Map<String, String> whereMap) {
        return rolePermissionDao.getPermissionCount(whereMap);
    }

    public List<String> getRolePermissions(String roleId) {
        if (StringUtils.hasText(roleId)) {
            return rolePermissionDao.getRolePermissions(roleId);
        }
        return null;
    }

    public List<PermissionBean> getRolePermissionsList(String roleId) {
        if (StringUtils.hasText(roleId)) {
            return rolePermissionDao.getRolePermissionsList(roleId);
        }
        return null;
    }

    public List<PermissionBean> getUrlPermissions() {
        return rolePermissionDao.getUrlPermissions();
    }

}
