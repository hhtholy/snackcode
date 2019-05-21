package com.hhtholy.service;

import com.hhtholy.entity.Order_;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.User;
import com.hhtholy.utils.Page;

import java.util.Date;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-16 15:24
 */
public interface OrderService {
    //订单列表分页展示
    public Page<Order_> getOrdersPage(Integer currentpage, Integer size, Integer navigateNum);


    //计算订单的总金额 和该订单包含产品的总数量
    public void addTotalPriceAndTotalNum(List<Order_> list);
    public void addTotalPriceAndTotalNum(Order_ order);
    List<Object> addOrder(Order_ order, List<OrderItem> orderItems);  //添加订单 订单和订单项关联起来
    public Order_ updateOrder(Order_ order_); //更新实体

    public Order_ getOrderByOrderCode(String orderCode);

    public Order_ getOrder(Integer id);

    public List<Order_> getOrdersByUserAndStatus(User user,String status); //去查用户的订单

    public List<Order_> getOrdersByUser(User user);

    public List<Order_> getOrders();

    public List<Order_> getRecentServenDaysOrder();
    public List<Order_> getRecentFifteenDaysOrder();
    public List<Order_> getRecentThirtyDaysOrder();
}
