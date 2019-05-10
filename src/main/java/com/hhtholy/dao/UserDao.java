package com.hhtholy.dao;

import com.hhtholy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author hht
 * @create 2019-04-16 15:11
 */
public interface UserDao extends JpaRepository<User,Integer> {

    public User findByName(String name); //根据用户名称进行查询
    @Query(value = "select count(*) from user",nativeQuery = true)
    public int  getNumOfUsers();

}
