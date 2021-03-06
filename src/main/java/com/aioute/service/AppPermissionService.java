package com.aioute.service;

import com.aioute.model.AppPermission;
import com.aioute.model.bean.AppPermissionBean;

import java.util.List;
import java.util.Map;

public interface AppPermissionService {

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
    public List<AppPermissionBean> getAppPermissions(Map<String, String> whereMap, int page, int pageSize);

    /**
     * 数目
     *
     * @param whereMap
     * @return
     */
    public int getPermissionCount(Map<String, String> whereMap);
}
