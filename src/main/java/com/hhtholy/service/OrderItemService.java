package com.hhtholy.service;

import com.hhtholy.entity.Order_;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.User;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-19 16:14
 * 订单项相关的业务层接口
 */
public interface OrderItemService {
    public OrderItem addOrderItem(OrderItem orderItem);    //添加产品
    public String deleteOrderItem(Integer id);   //删除产品
    public OrderItem getOrderItem(Integer id); //根据id 获取产品
    public OrderItem updateOrderItem(OrderItem orderItem);    //更新产品

    public List<OrderItem> getOrderItemsByOrder(Order_ order); //根据订单获取订单项

    public List<OrderItem> getOrderItemByProduct(Product product); //根据产品获取订单项
    public List<OrderItem> getOrderItemByUser(User user); //根据用户获取订单项

    
}
