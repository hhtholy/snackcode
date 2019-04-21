package com.hhtholy.service;

import com.hhtholy.entity.Order;
import com.hhtholy.utils.Page;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-16 15:24
 */
public interface OrderService {
    //订单列表分页展示
    public Page<Order> getOrdersPage(Integer currentpage, Integer size, Integer navigateNum);



    //计算订单的总金额 和该订单包含产品的总数量
    public void addTotalPriceAndTotalNum(List<Order> list);
    public void addTotalPriceAndTotalNum(Order order);

}
