package com.aioute.controller.trans;

import com.sft.service.PermissionService;
import com.sft.util.HttpClient;
import com.sft.util.SendAppJSONUtil;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("trans")
public class TransController {

    @Resource
    private PermissionService permissionService;

    /**
     * plat接口转发
     *
     * @param req
     * @param res
     */
    @RequestMapping("plat")
    public void transPlat(HttpServletRequest req, HttpServletResponse res) {
        String type = req.getParameter("type");
        String url = permissionService.getUrlByType(type);
        if (StringUtils.hasText(url)) {
            new HttpClient(req, res).send(permissionService.getUrlByType(type));
        } else {
            // 转发失败
            try {
                String returnJson = SendAppJSONUtil.getRequireParamsMissingObject("没有type");
                res.getWriter().write(returnJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}