package com.hhtholy.service;

import com.hhtholy.entity.Order_;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.utils.Page;

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
    Order_ updateOrder(Order_ order_); //更新实体
}
