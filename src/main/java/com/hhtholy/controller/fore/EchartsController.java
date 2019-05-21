package com.hhtholy.controller.fore;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.EchartForPie;
import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.Order_;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.OrderService;
import com.hhtholy.service.ProductService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static javax.print.attribute.standard.MediaSizeName.A;

/**
 * @author hht
 * @create 2019-05-14 19:01
 */
@RestController
public class EchartsController {
    @Autowired
    private ProductService productService;
    @Autowired private OrderService orderService;
    @Autowired private CategoryService categoryService;

    /**
     *  绘制图表
     * @return
     */

    @PostMapping("/getEchart")
    public Object getDatas(){

        List<Integer> arrayList1 = new ArrayList<>(); //零食销量
        List<Float> arrayList2 = new ArrayList<>(); //零食销售额

        //获取近7天的零食销量   //近七天的销售额  近半月  近一个月

        List<Order_> ordersRecent7d = orderService.getRecentServenDaysOrder(); //获取近七天的订单 信息
        int countForRecent7d = 0;
        float totalRecent7d = 0;
        for (Order_ order : ordersRecent7d) {
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单对应的订单项
            for (OrderItem orderItem : orderItems) {
                if(orderItem.getOrder() != null && orderItem.getOrder().getPayDate() != null){   //首先确定是创建了订单的  同时是已经付款的
                    countForRecent7d += orderItem.getNumber();
                    float total =  orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
                    totalRecent7d += total;
                }
            }
        } //for orders
        arrayList1.add(countForRecent7d); //销量
        arrayList2.add(totalRecent7d); //销售额

//////////近15天
        List<Order_> ordersRecent15d = orderService.getRecentServenDaysOrder(); //获取近15天的订单 信息
        int countForRecent15d = 0;
        float totalRecent15d = 0;
        for (Order_ order : ordersRecent15d) {
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单对应的订单项
            for (OrderItem orderItem : orderItems) {
                if(orderItem.getOrder() != null && orderItem.getOrder().getPayDate() != null){   //首先确定是创建了订单的  同时是已经付款的
                    countForRecent15d += orderItem.getNumber();
                    float total =  orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
                    totalRecent15d += total;
                }
            }
        } //for orders
        arrayList1.add(countForRecent15d); //销量
        arrayList2.add(totalRecent15d); //销售额

///////近30天
        List<Order_> ordersRecent30d = orderService.getRecentServenDaysOrder(); //获取近15天的订单 信息
        int countForRecent30d = 0;
        float totalRecent30d = 0;
        for (Order_ order : ordersRecent30d) {
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单对应的订单项
            for (OrderItem orderItem : orderItems) {
                if(orderItem.getOrder() != null && orderItem.getOrder().getPayDate() != null){   //首先确定是创建了订单的  同时是已经付款的
                    countForRecent30d += orderItem.getNumber();
                    float total =  orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
                    totalRecent30d += total;
                }
            }
        } //for orders
        arrayList1.add(countForRecent30d); //销量
        arrayList2.add(totalRecent30d); //销售额


        HashMap<String, List> map = new HashMap<>();
        map.put("count",arrayList1);
        map.put("total",arrayList2);
        return map;
    }

    @PostMapping("/getEchartForPie")
    public Object getDatasForPie(){
        List<EchartForPie> pieData = new ArrayList<>();

///////近30天
        List<Order_> ordersRecent30d = orderService.getRecentServenDaysOrder(); //获取近15天的订单 信息

        // 分类名  数量
        ArrayList<String> listNames = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();//初始化数据
        categoryService.getCategoryList().stream().map(Category::getName).collect(Collectors.toList()).forEach(x->{
            map.put(x,0); //初始化数量为0
            listNames.add(x);
        });

        for (Order_ order : ordersRecent30d) {
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单对应的订单项
            for (OrderItem orderItem : orderItems) {
                if(orderItem.getOrder() != null && orderItem.getOrder().getPayDate() != null){   //首先确定是创建了订单的  同时是已经付款的
                    Integer number = orderItem.getNumber();
                    String name = orderItem.getProduct().getCategory().getName();
                    map.put(name,map.get(name)+number);
                }
            }
        } //for orders

        List<EchartForPie> listForPie= new ArrayList<>();

        //遍历map
        Set<String> keys = map.keySet();

        for (String key : keys) {
            EchartForPie echartForPie = new EchartForPie();
            echartForPie.setName(key);
            echartForPie.setValue(String.valueOf(map.get(key)));
            listForPie.add(echartForPie);
        }
        HashMap<String, List> map1 = new HashMap<>();
        map1.put("listForPie",listForPie);
        map1.put("names",listNames);
        return  map1;

    }
}
