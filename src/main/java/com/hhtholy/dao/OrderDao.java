package com.hhtholy.dao;

import com.hhtholy.entity.Order_;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hht
 * @create 2019-04-16 15:25
 */
public interface OrderDao extends JpaRepository<Order_,Integer> {
      public Order_ findByOrderCode(String code); //根据订单号 查询订单
}
