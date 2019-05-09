package com.hhtholy.service.impl;

import com.hhtholy.dao.UserDao;
import com.hhtholy.entity.User;
import com.hhtholy.service.UserService;
import com.hhtholy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author hht
 * @create 2019-04-16 15:11
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserDao userDao;

    /**
     * 返回用户的信息
     * @param currentPage 当前页
     * @param size  每页显示的条数
     * @param navigateNum  导航长度
     * @return  分页信息
     */
    @Override
    public Page<User> getUserPage(Integer currentPage, Integer size, int navigateNum) {
        //设置分页信息
        Pageable pageable = new PageRequest(currentPage,size, Sort.Direction.DESC,"id");
        org.springframework.data.domain.Page<User> results = userDao.findAll(pageable);
        return new Page<>(results,navigateNum);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */

    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.save(user);
    }

    /**
     * 判断用户是否已经存在
     * @param user
     * @return
     */
    @Override
    public boolean isExist(User user) {
        return userDao.findByName(user.getName()) != null;
    }

    /**
     * 根据名称去获取用户
     * @param name
     * @return
     */
    @Override
    public User getUserByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public int getNumOfUsers() {
        return userDao.getNumOfUsers();
    }
}
