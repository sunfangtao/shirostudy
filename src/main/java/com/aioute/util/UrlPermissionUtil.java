package com.aioute.util;

import com.aioute.service.ModuleService;
import com.sft.model.bean.ModuleBean;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UrlPermissionUtil {

    @Resource
    private ModuleService moduleService;
    /**
     * 更新资源权限
     */
    public void updateUrlPermission() {
        getUrlAndSendGet("uController/resourceCache", null);
    }

    /**
     * 更新用户权限
     */
    public void updateUserPermission(final String account) {
        getUrlAndSendGet("uController/shiroCache", account);
    }

    /**
     * 更新APP访问权限
     */
    public void updateAppPermission() {
        getUrlAndSendGet("trans/updatePermission", null);
    }

    private void getUrlAndSendGet(final String address, final String account) {
        new Thread(new Runnable() {
            public void run() {
                List<ModuleBean> moduleList = moduleService.getAllModule(null, 0, 0);
                if (moduleList != null) {
                    int length = moduleList.size();
                    for (int i = 0; i < length; i++) {
                        String url = moduleList.get(i).getAddress() + address;
                        sendGet(url, account);
                    }
                }
            }
        }).start();
    }

    private String sendGet(String url, String account) {
        try {
            // 创建一个httpclient对象
            CloseableHttpClient client = HttpClients.custom().build();

            // 创建URIBuilder
            URIBuilder uri = new URIBuilder(url);

            // 设置参数
            if (StringUtils.hasText(account)) {
                uri.addParameter("account", account);
            }

            // 创建httpGet对象
            HttpGet hg = new HttpGet(uri.build());

            // 设置请求的报文头部的编码
            hg.setHeader(
                    new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));

            // 设置期望服务端返回的编码
            hg.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

            // 请求服务
            CloseableHttpResponse response = client.execute(hg);

            // 获取响应码
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                // 获取返回实例entity
                HttpEntity entity = response.getEntity();
                // 通过EntityUtils的一个工具方法获取返回内容
                String resStr = EntityUtils.toString(entity, "utf-8");
                // 输出
                System.out.println("请求成功,请求返回内容为: " + resStr);
                return resStr;
            } else {
                // 输出
                System.out.println("请求失败,错误码为: " + statusCode);
            }
            // 关闭response和client
            response.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
