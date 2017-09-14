package com.aioute.dao;

import com.aioute.model.AppPermission;

import java.util.List;
import java.util.Map;

public interface AppPermissionDao {

    /**
     * 添加
     *
     * @param appPermission
     * @return
     */
    public boolean addAppPermission(AppPermission appPermission);

    /**
     * 更新
     *
     * @param appPermission
     * @return
     */
    public boolean updateAppPermission(AppPermission appPermission);

    /**
     * 获取列表
     *
     * @return
     */
    public List<AppPermission> getAppPermissions(Map<String, String> whereMap, int page, int pageSize);

    public int getPermissionCount(Map<String, String> whereMap);
}
