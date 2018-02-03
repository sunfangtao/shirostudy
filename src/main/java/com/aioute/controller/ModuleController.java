package com.aioute.controller;

import com.aioute.service.ModuleService;
import com.sft.model.Module;
import com.sft.model.bean.ModuleBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController {

    @Resource
    private ModuleService moduleService;

    /**
     * 新增模块
     */
    @ResponseBody
    @RequestMapping("addModule")
    public String addModule(String name, String remarks, String del_flag, String address) {
        String returnStr = "";
        Module module = new Module();

        if (StringUtils.hasText(remarks)) {
            module.setRemarks(remarks);
        }
        if (StringUtils.hasText(del_flag) && !del_flag.equals("0")) {
            module.setDel_flag(1);
        }
        if (StringUtils.hasText(address)) {
            module.setAddress(address);
        }
        if (StringUtils.hasText(name)) {
            module.setName(name);
        } else {
            // 必须有模块名称
            return SendAppJSONUtil.getRequireParamsMissingObject("必须填写模块名称!");
        }
        String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
        boolean result = moduleService.addModule(module, userId);
        if (result) {
            // 操作成功
            returnStr = SendAppJSONUtil.getNormalString("创建成功!");
        } else {
            // 模块名称不能重复
            returnStr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "模块名称不能重复!");
        }
        return returnStr;
    }

    /**
     * 更新模块
     */
    @ResponseBody
    @RequestMapping("updateModule")
    public String updateModule(String moduleId, String name, String remarks, String del_flag, String address, String is_redict, HttpServletRequest req, HttpServletResponse res) {
        Module module = new Module();
        if (StringUtils.hasText(moduleId)) {
            module.setId(moduleId);
            if (StringUtils.hasText(name)) {
                module.setName(name);
            }
            if (StringUtils.hasText(remarks)) {
                module.setRemarks(remarks);
            }
            if (StringUtils.hasText(address)) {
                module.setAddress(address);
            }
            if (StringUtils.hasText(del_flag) && !del_flag.equals("0")) {
                module.setDel_flag(1);
            }
            if (StringUtils.hasText(is_redict) && !is_redict.equals("0")) {
                module.setIsDirect("1");
            } else {
                module.setIsDirect("0");
            }
            if (moduleService.updateModule(module)) {
                return SendAppJSONUtil.getNormalString("更新成功!");
            } else {
                // 模块名称不能重复
                String sr = SendAppJSONUtil.getFailResultObject(CloudError.ReasonEnum.REPEAT.getValue(), "模块名称不能重复!");
                return sr;
            }
        } else {
            // 必须有模块编号
            return SendAppJSONUtil.getRequireParamsMissingObject("缺少模块编号!");
        }
    }

    /**
     * 获取系统所有模块
     *
     * @param req
     * @param res
     */
    @ResponseBody
    @RequestMapping("getAllModule")
    public String getAllModule(HttpServletRequest req, HttpServletResponse res) {
        int page = PagingUtil.getPage(req);
        int pageSize = PagingUtil.getPageSize(req);

        Map<String, String> whereMap = new HashMap<String, String>();
        if (req.getParameter("isAll") != null) {
            page = 0;
            pageSize = 0;
        } else {
            whereMap.put("name", req.getParameter("name"));
            whereMap.put("del_flag", req.getParameter("del_flag"));
        }

        List<ModuleBean> moduleList = moduleService.getAllModule(whereMap, page, pageSize);
        int count = moduleService.getModuleCount(whereMap);
        return SendPlatJSONUtil.getPageJsonString(0, "", count, moduleList);
    }

}
