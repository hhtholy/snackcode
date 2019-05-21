package com.hhtholy.dao;

import com.hhtholy.entity.Order_;
import com.hhtholy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-16 15:25
 */
public interface OrderDao extends JpaRepository<Order_,Integer> {
      public Order_ findByOrderCode(String code); //根据订单号 查询订单

      public List<Order_> findByUserAndStatusOrderById(User user, String status);//根据用户查询订单状态

      public List<Order_>  findByUserOrderByIdDesc(User user); //根据用户查询订单

      @Query(value = "SELECT * FROM order_ e where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(payDate)",nativeQuery = true)
      public List<Order_> getRecentServenDaysOrders(); //获取近七天的数据

      @Query(value = "SELECT * FROM order_ e where DATE_SUB(CURDATE(), INTERVAL 15 DAY) <= date(payDate)",nativeQuery = true)
      public List<Order_> getRecentFifteenDaysOrders(); //获取近15天的数据

      /**
       *   CURDATE()表示当前的系统时间（日期），另有CURTIME（）表示当前的系统时间（时分秒）
       *     date（表中的时间字段）
       */
      @Query(value = "SELECT * FROM order_ e where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(payDate)",nativeQuery = true)
      public List<Order_> getRecentThirtyDaysOrders(); //获取近15天的数据
}
