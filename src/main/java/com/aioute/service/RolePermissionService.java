package com.aioute.service;

import java.util.List;

public interface RolePermissionService {

    /**
     * 获取用户的角色
     *
     * @param account
     * @return
     */
    public List<String> getRoles(String account);

    /**
     * 获取用户的权限
     *
     * @param account
     * @return
     */
    public List<String> getPermissions(String account);

    /**
     * 获取角色的权限
     *
     * @param role
     * @return
     */
    public List<String> getRolePermissions(String role);

}
