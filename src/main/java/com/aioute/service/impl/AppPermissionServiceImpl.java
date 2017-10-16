package com.aioute.service.impl;

import com.aioute.dao.AppPermissionDao;
import com.aioute.model.AppPermission;
import com.aioute.model.bean.AppPermissionBean;
import com.aioute.service.AppPermissionService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class AppPermissionServiceImpl implements AppPermissionService {

    @Resource
    private AppPermissionDao appPermissionDao;

    public boolean addAppPermission(AppPermission appPermission) {
        return appPermissionDao.addAppPermission(appPermission);
    }

    public boolean updateAppPermission(AppPermission appPermission) {
        return appPermissionDao.updateAppPermission(appPermission);
    }

    public List<AppPermissionBean> getAppPermissions(Map<String, String> whereMap, int page, int pageSize) {
        return appPermissionDao.getAppPermissions(whereMap, page, pageSize);
    }

    public int getPermissionCount(Map<String, String> whereMap) {
        return appPermissionDao.getPermissionCount(whereMap);
    }
}
