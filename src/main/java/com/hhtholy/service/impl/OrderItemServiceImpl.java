package com.hhtholy.service.impl;

import com.hhtholy.dao.OrderItemDao;
import com.hhtholy.entity.*;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hht
 * @create 2019-04-19 16:14
 * 订单项相关的实现接口
 *
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired private OrderItemDao orderItemDao;

    /**
     * 添加订单项
     * @param orderItem 订单项实体
     * @return
     */
    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        return orderItemDao.save(orderItem);
    }

    /**
     * 删除订单项
     * @param id 订单项id
     * @return
     */
    @Override
    public String deleteOrderItem(Integer id) {
        String result = null;
        try {
            orderItemDao.deleteById(id);
            result = "success";
        } catch (Exception e){
            result = "failure";
        }
        return result;
    }

    /**
     * 根据id 获取订单项
     * @param id  订单项id
     * @return
     */

    @Override
    public OrderItem getOrderItem(Integer id) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        Example<OrderItem> example = Example.of(orderItem);
        Optional<OrderItem> optional = orderItemDao.findOne(example);
        OrderItem result = null;
        if(optional.isPresent()){
            result = optional.get();
        }
        return result;
    }

    /**
     * 更新订单项
     * @param orderItem
     * @return
     */
    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        OrderItem result = null;
        try {
            result = orderItemDao.save(orderItem);
        }catch (Exception e){
            result = null;
        }
        return result;
    }

    /**
     *  根据订单获取订单项
     * @param order
     * @return
     */
    @Override
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemDao.findByOrderOrderById(order);
    }

    /**
     *  根据产品获取订单项
     * @param product
     * @return
     */
    @Override
    public List<OrderItem> getOrderItemByProduct(Product product) {
        return orderItemDao.findByProductOrderById(product);
    }

    /**
     * 根据用户去查询订单
     * @param user  用户
     * @return
     */

    @Override
    public List<OrderItem> getOrderItemByUser(User user) {
        return orderItemDao.findByUserOrderById(user);
    }
}
