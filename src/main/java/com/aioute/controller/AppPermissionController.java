package com.aioute.controller;

import com.aioute.model.AppPermission;
import com.aioute.service.AppPermissionService;
import com.aioute.util.UrlPermissionUtil;
import com.sft.util.CloudError;
import com.sft.util.PagingUtil;
import com.sft.util.SendAppJSONUtil;
import com.sft.util.SendPlatJSONUtil;
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
@RequestMapping("appPermission")
public class AppPermissionController {

    @Resource
    private AppPermissionService appPermissionService;
    @Resource
    private UrlPermissionUtil urlPermissionUtil;

    /**
     * 新建
     *
     * @param type
     * @param remarks
     * @param url
     * @param is_user
     * @return
     */
    @ResponseBody
    @RequestMapping("addAppPermission")
    public String addAppPermission(String type, String remarks, String url, String is_user, String moduleId) {
        String returnStr = "";
        AppPermission appPermission = new AppPermission();

        if (StringUtils.hasText(remarks)) {
            appPermission.setRemarks(remarks);
        }
        if (StringUtils.hasText(is_user) && is_user.equals("1")) {
            appPermission.setIs_user(1);
        }
        if (StringUtils.hasText(moduleId)) {
            appPermission.setModule_id(moduleId);
        } else {
            return SendAppJSONUtil.getRequireParamsMissingObject("必须填写模块!");
        }
        if (StringUtils.hasText(url)) {
            appPermission.setUrl(url);
        } else {
            return SendAppJSONUtil.getRequireParamsMissingObject("必须填写映射地址!");
        }
        if (StringUtils.hasText(type)) {
            appPermission.setType(type);
        } else {
            return SendAppJSONUtil.getRequireParamsMissingObject("必须填写访问标识!");
        }

        appPermission.setId(UUID.randomUUID().toString());
        boolean result = appPermissionService.addAppPermission(appPermission);
        if (result) {
            // 操作成功
            urlPermissionUtil.updateAppPermission();
            returnStr = SendAppJSONUtil.getNormalString("创建成功!");
        } else {
            // 角色名称不能重复
            returnStr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "创建失败!");
        }
        return returnStr;
    }

    /**
     * 更新 done
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("updateAppPermission")
    public String updateAppPermission(HttpServletRequest req, HttpServletResponse res) {
        AppPermission appPermission = new AppPermission();
        String id = req.getParameter("id");
        if (StringUtils.hasText(id)) {
            appPermission.setId(id);
            String type = req.getParameter("type");
            String remarks = req.getParameter("remarks");
            String url = req.getParameter("url");
            String is_user = req.getParameter("is_user");
            String module_Id = req.getParameter("moduleId");
            if (StringUtils.hasText(type)) {
                appPermission.setType(type);
            }
            if (StringUtils.hasText(remarks)) {
                appPermission.setRemarks(remarks);
            }
            if (StringUtils.hasText(url)) {
                appPermission.setUrl(url);
            }
            if (StringUtils.hasText(is_user) && !is_user.equals("0")) {
                appPermission.setIs_user(1);
            }
            if (StringUtils.hasText(module_Id)) {
                appPermission.setModule_id(module_Id);
            }
            if (appPermissionService.updateAppPermission(appPermission)) {
                urlPermissionUtil.updateAppPermission();
                return SendAppJSONUtil.getNormalString("更新成功!");
            } else {
                // 角色名称不能重复
                String sr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.SQLEXCEPTION.getValue(), "更新失败!");
                return sr;
            }
        } else {
            return SendAppJSONUtil.getRequireParamsMissingObject("缺少编号!");
        }
    }

    /**
     * 获取所有权限
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("getAllAppPermissions")
    public String getAllPermissions(HttpServletRequest req, HttpServletResponse res) {
        int page = PagingUtil.getPage(req);
        int pageSize = PagingUtil.getPageSize(req);
        Map<String, String> whereMap = new HashMap<String, String>();
        whereMap.put("url", req.getParameter("url"));
        whereMap.put("type", req.getParameter("type"));
        List<AppPermission> permissionList = appPermissionService.getAppPermissions(whereMap, page, pageSize);
        int count = appPermissionService.getPermissionCount(whereMap);
        return SendPlatJSONUtil.getPageJsonString(0, "", count, permissionList);
    }

}
