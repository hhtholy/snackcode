package com.hhtholy.service;

import com.hhtholy.entity.User;
import com.hhtholy.utils.Page;
import com.sun.org.apache.regexp.internal.RE;

/**
 * @author hht
 * @create 2019-04-16 15:10
 */
public interface UserService {
    //展示用户列表  分页的方式
    public Page<User> getUserPage(Integer currentPage, Integer size, int navigateNum);
    public User addUser(User user);
    public User updateUser(User user);
    public boolean isExist(User user);
}
