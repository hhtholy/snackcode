package com.hhtholy.dao;

import com.hhtholy.entity.Order_;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-19 16:17
 * 订单项相关的数据层接口
 */
public interface OrderItemDao extends JpaRepository<OrderItem,Integer> {
    public List<OrderItem> findByOrderOrderById(Order_ order);//根据订单查订单项
    public List<OrderItem> findByProductOrderById(Product product); //根据产品去查订单项
    public List<OrderItem> findByUserAndIncartOrderById(User user,int incart); //根据用户去查订单项

}
