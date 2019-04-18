package com.hhtholy.dao;

import com.hhtholy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hht
 * @create 2019-04-16 15:11
 */
public interface UserDao extends JpaRepository<User,Integer> {

    public User findByName(String name); //根据用户名称进行查询
}
