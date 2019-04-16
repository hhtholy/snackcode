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
    @Override
    public Page<User> getUserPage(Integer currentPage, Integer size, int navigateNum) {
        //设置分页信息
        Pageable pageable = new PageRequest(currentPage,size, Sort.Direction.DESC,"id");
        org.springframework.data.domain.Page<User> results = userDao.findAll(pageable);
        return new Page<>(results,navigateNum);
    }
}
