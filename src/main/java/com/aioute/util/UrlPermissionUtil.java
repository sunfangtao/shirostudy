package com.aioute.util;

import com.aioute.service.ModuleService;
import com.sft.model.bean.ModuleBean;
import com.sft.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class UrlPermissionUtil {

    @Resource
    private ModuleService moduleService;
    @Resource
    private RoleService roleService;

    /**
     * 更新资源权限
     */
    public void updateUrlPermission() {
        new Thread(new Runnable() {
            public void run() {
                List<ModuleBean> moduleList = moduleService.getAllModule(null, 0, 0);
                if (moduleList != null) {
                    int length = moduleList.size();
                    for (int i = 0; i < length; i++) {
                        String url = moduleList.get(i).getAddress() + "uController/resourceCache";
                        sendGet(url);
                    }
                }
            }
        }).start();
    }

    /**
     * 更新用户权限
     */
    public void updateUserPermission(final String account) {
        new Thread(new Runnable() {
            public void run() {
                List<ModuleBean> moduleList = moduleService.getAllModule(null, 0, 0);
                if (moduleList != null) {
                    int length = moduleList.size();
                    for (int i = 0; i < length; i++) {
                        String url = moduleList.get(i).getAddress() + "uController/shiroCache";
                        if (account != null) {
                            url += ("?account=" + account);
                        }
                        sendGet(url);
                    }
                }
            }
        }).start();
    }

    private String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
