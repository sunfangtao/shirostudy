package com.aioute.dao;

import com.sft.model.Permission;
import com.sft.model.Role;
import com.sft.model.bean.PermissionBean;
import com.sft.model.bean.RoleBean;

import java.util.List;
import java.util.Map;

public interface RolePermissionDao {

    /**
     * 新建角色
     *
     * @param role
     * @return
     */
    public boolean addRole(Role role);

    public Role getRole(String roleId);

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    public boolean updateRole(Role role);

    /**
     * @param roleId
     * @param userId
     * @return
     */
    public boolean addUserRole(String roleId, String userId, String create_by, String create_date);

    /**
     * 获取所有角色
     *
     * @return
     */
    public List<RoleBean> getRoles(Map<String, String> whereMap, int page, int pageSize);

    /**
     * 获取角色数目
     *
     * @return
     */
    public int getRoleCount(Map<String, String> whereMap);

    /**
     * 更新角色权限
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    public boolean updateRolePermission(String roleId, List<String> permissionId);

    /**
     * 新建权限
     *
     * @param permission
     * @return
     */
    public boolean addPermission(Permission permission);

    /**
     * 更新权限
     *
     * @param permission
     * @return
     */
    public boolean updatePermission(Permission permission);

    /**
     * 获取用户的权限
     *
     * @param userId
     * @return
     */
    public List<String> getPermissions(String userId);

    /**
     * 获取所有权限
     *
     * @return
     */
    public List<PermissionBean> getPermissions(Map<String, String> whereMap, int page, int pageSize);

    /**
     * 获取所有权限
     *
     * @return
     */
    public List<PermissionBean> getPermissions();

    /**
     * 获取权限数目
     *
     * @return
     */
    public int getPermissionCount(Map<String, String> whereMap);

    /**
     * 获取角色的权限
     *
     * @param roleId
     * @return
     */
    public List<String> getRolePermissions(String roleId);

    /**
     * 获取角色的权限
     *
     * @param roleId
     * @return
     */
    public List<PermissionBean> getRolePermissionsList(String roleId);

    /**
     * 获取资源权限
     *
     * @return
     */
    public List<PermissionBean> getUrlPermissions();
}