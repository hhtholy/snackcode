package com.hhtholy.service.impl;

import com.hhtholy.dao.OrderDao;
import com.hhtholy.entity.Order_;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.User;
import com.hhtholy.service.OrderItemService;
import com.hhtholy.service.OrderService;
import com.hhtholy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author hht
 * @create 2019-04-16 15:25
 * 订单相关的业务层实现
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderDao orderDao;
    @Autowired private OrderItemService orderItemService;

    /**
     * 获取订单的分页信息
     * @param currentpage
     * @param size
     * @param navigateNum
     * @return
     */
    @Override
    public Page<Order_> getOrdersPage(Integer currentpage, Integer size, Integer navigateNum) {
        //设置分页信息
        Pageable pageable = new PageRequest(currentpage,size, Sort.Direction.DESC,"id");
        org.springframework.data.domain.Page<Order_> results = orderDao.findAll(pageable);
        return new Page<>(results,navigateNum);
    }

    /**
     * 为订单添加 总金额 和 数量
     * @param list
     */
    @Override
    public void addTotalPriceAndTotalNum(List<Order_> list) {
        for (Order_ order : list) {
            addTotalPriceAndTotalNum(order);
        }
    }

    /**
     *  给订单计算总金额  商品总数量
     * @param order  订单
     */
    @Override
    public void addTotalPriceAndTotalNum(Order_ order) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);//获取一个订单下的所有订单项
        Product result = null;
        float totalPrice = 0; //总共的价格
        int total = 0;  //  产品的总数量
        for (OrderItem orderItem : orderItems) {    //遍历每一个订单项
           result = orderItem.getProduct(); // 订单项对应的产品
            totalPrice += result.getPromotePrice() * orderItem.getNumber(); //总价格
            total += orderItem.getNumber();   //总数量
        }
        order.setTotalNum(total);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
    }

    /**
     *   添加订单
     * @param order 订单
     * @param orderItems 订单项
     * @return  订单的总金额
     */
    @Override
    public  List<Object> addOrder(Order_ order, List<OrderItem> orderItems) {

        ArrayList<Object> totalNumsAndPrice = new ArrayList<>();
        Float totalPrice = 0f;
        Integer totalNum = 0;
        orderDao.save(order);
        for (OrderItem item : orderItems){     //更新 订单项
            item.setOrder(order); //订单项和订单关联起来
            orderItemService.updateOrderItem(item);
            totalPrice += item.getProduct().getPromotePrice() * item.getNumber();
            totalNum += item.getNumber();
        }
        totalNumsAndPrice.add(totalPrice);
        totalNumsAndPrice.add(totalNum);
        return totalNumsAndPrice;
    }

    /**
     * 更新订单
     * @param order_ 订单实体
     * @return
     */
    @Override
    public Order_ updateOrder(Order_ order_) {
        Order_ result = null;
        try {
            result = orderDao.save(order_);
        }catch (Exception e){
            result = null;
        }
        return result;
    }

    /**
     * 根据订单号去查询 订单
     * @param orderCode
     * @return
     */
    @Override
    public Order_ getOrderByOrderCode(String orderCode) {
        return orderDao.findByOrderCode(orderCode);
    }

    /**
     * 根据订单id 获取订单
     * @param id
     * @return
     */
    @Override
    public Order_ getOrder(Integer id) {
        Order_ order = new Order_();
        order.setId(id);
        Example<Order_> example = Example.of(order);
        Optional<Order_> optional = orderDao.findOne(example);
        Order_ result = null;
        if(optional.isPresent()){
            result = optional.get();
        }
        return result;
    }

    /**
     *  查询用户的全部订单
     * @param user
     * @return
     */

    @Override
    public List<Order_> getOrdersByUserAndStatus(User user,String status) {
        return orderDao.findByUserAndStatus(user,status);
    }

    /**
     *  根据    用户去查询订单
     * @param user
     * @return
     */
    @Override
    public List<Order_> getOrdersByUser(User user) {
        return orderDao.findByUser(user);
    }

}
