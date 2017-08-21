package com.aioute.controller;

import com.aioute.shiro.filter.PasswordShiroFilter;
import com.aioute.util.CloudError;
import com.aioute.util.SendJSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("loginController")
public class LoginController {

    //private Logger logger = Logger.getLogger(LoginController.class);

//	@Resource
//	private UserDao userDao;

    /**
     * 用户登录
     *
     * @param req
     * @param res
     */
    @RequestMapping("login")
    public void login(HttpServletRequest req, HttpServletResponse res) {
        try {
			String returnString = null;
            String errorClassName = (String) req.getAttribute(PasswordShiroFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
            //logger.info("login errorClassName=" + errorClassName);
			if (errorClassName == null) {
				// 成功
				Subject subject = SecurityUtils.getSubject();
				String phone = (String) subject.getPrincipal();

				if (phone == null || phone.length() == 0) {
					// 非法请求
					//logger.debug("用户请求未认证");
                    returnString = SendJSONUtil.getFailResultObject(CloudError.ReasonEnum.NOTLOGIN.getValue(), "请先登录！");
				} else {
//					UserVO user = userDao.obtainUserInfoByPhone(phone);
//					user.setLoginTime(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));
//
//					if ("N".equals(user.getUseFlag())) {
//						// 用户被禁用
//						jsonObject = SendJSONUtil.getFailResultObject(TypeEnum.PERMISSION.getValue(), "账号被禁用，无权登录");
//					} else {
//						userDao.updateUser(user);
//						user.setPassword("******");
//						jsonObject = SendJSONUtil.getNormalObject(user);
//					}
					//logger.debug("用户开始登录 ：");
				}
			} else {
				// 失败
				//logger.info("用户登录失败 " + errorClassName);
//				if (errorClassName.contains("IncorrectCredentialsException")) {
//					jsonObject = SendJSONUtil.getFailResultObject("", "密码错误！");
//				} else if (errorClassName.contains("UnknownAccountException")) {
//					jsonObject = SendJSONUtil.getFailResultObject("", "用户不存在！");
//				} else {
//					jsonObject = SendJSONUtil.getFailResultObject("", "登录失败！");
//				}
			}
            res.getWriter().write(returnString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
