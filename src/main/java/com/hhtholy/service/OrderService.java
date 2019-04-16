package com.hhtholy.service;

import com.hhtholy.entity.Order;
import com.hhtholy.utils.Page;

/**
 * @author hht
 * @create 2019-04-16 15:24
 */
public interface OrderService {
    //订单列表分页展示
    public Page<Order> getOrdersPage(Integer currentpage, Integer size, Integer navigateNum);
}
