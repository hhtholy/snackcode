package com.hhtholy.service;

import com.hhtholy.entity.User;
import com.hhtholy.utils.Page;

/**
 * @author hht
 * @create 2019-04-16 15:10
 */
public interface UserService {
    //展示用户列表  分页的方式
    public Page<User> getUserPage(Integer currentPage, Integer size, int navigateNum);
}
