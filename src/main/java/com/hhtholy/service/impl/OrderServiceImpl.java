package com.hhtholy.service.impl;

import com.hhtholy.dao.OrderDao;
import com.hhtholy.entity.Order;
import com.hhtholy.service.OrderService;
import com.hhtholy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author hht
 * @create 2019-04-16 15:25
 * 订单相关的业务层实现
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderDao orderDao;
    @Override
    public Page<Order> getOrdersPage(Integer currentpage, Integer size, Integer navigateNum) {
        //设置分页信息
        Pageable pageable = new PageRequest(currentpage,size, Sort.Direction.DESC,"id");
        org.springframework.data.domain.Page<Order> results = orderDao.findAll(pageable);
        return new Page<>(results,navigateNum);
    }
}
