package com.hhtholy.dao;

import com.hhtholy.entity.Order_;
import com.hhtholy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-16 15:25
 */
public interface OrderDao extends JpaRepository<Order_,Integer> {
      public Order_ findByOrderCode(String code); //根据订单号 查询订单

      public List<Order_> findByUserAndStatus(User user, String status);//根据用户查询订单状态

      public List<Order_>  findByUser(User user); //根据用户查询订单
}
