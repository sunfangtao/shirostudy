package com.aioute.service;

import com.aioute.model.UserModel;

public interface UserService {
    /**
     * 获取用户信息
     * @param account
     * @return
     */
    public UserModel getUserByAccount(String account);
}
