package com.hhtholy.controller.admin;

import com.hhtholy.entity.*;
import com.hhtholy.service.OrderService;
import com.hhtholy.service.ProductService;
import com.hhtholy.service.ReviewService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import com.hhtholy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hht
 * @create 2019-05-04 17:38
 */
@RestController
@Api(tags = "后台订单模块",description = "后台订单相关的api")
public class OrderController {

 @Autowired private OrderService orderService;

 @Autowired private ProductService productService;

 @Autowired private ReviewService reviewService;

    @ApiOperation(value = "获取订单列表(分页)",notes = "获取所有的订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("/orders")
    public Page<Order_> getUsers(@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                 @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        Page<Order_> ordersPage = orderService.getOrdersPage(currentPage, Integer.valueOf(ReadProperties.getPropertyValue("pagesize", "application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums", "application.properties")));
        List<Order_> content = ordersPage.getContent();

        for (Order_ order : content) { // 遍历订单列表
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单下的订单项
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(null);
                productService.setSingleImageUrlFoJson(orderItem.getProduct()); //设置产品单图
            }
            order.setOrderItemsForJson(orderItems);
        }
        return ordersPage;
    }


    /***********发货和确认收货统一放在 后台的controller中**********/

    @ApiOperation(value = "发货",notes = "点击发货更新订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oid",value="订单id",required=true,paramType="path",dataType = "int"),
    })
    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order_ order = orderService.getOrder(oid);
        order.setDeliveryDate(new Date());
        order.setStatus(Constant.ORDER_WAITCONFIRM.getWord()); //订单的状态改为待收货
        orderService.updateOrder(order);
        return Result.success();
    }

    @ApiOperation(value = "收货过程中加载订单项信息",notes = "收货逻辑支持")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oid",value="订单id",required=true,paramType="path",dataType = "int"),
    })
    @GetMapping("foreConfirmPay")
    public Object confirmPay(int oid) { //填充订单的订单项信心
            Order_ order = orderService.getOrder(oid);//根据id获取订单
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单下的订单项
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(null);
                productService.setSingleImageUrlFoJson(orderItem.getProduct()); //设置产品单图
            }
            order.setOrderItemsForJson(orderItems);
            return order;
        }

    @ApiOperation(value = "确认收货",notes = "确认收货 订单信息为待评价状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oid",value="订单id",required=true,paramType="path",dataType = "int"),
    })
    @GetMapping("foreOrderConfirmed")
    public Object orderConfirmed(int oid) {
        Order_ order = orderService.getOrder(oid);
        //更新订单的状态
        order.setStatus(Constant.ORDER_WAITREVIEW.getWord()); //待评价状态
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            Integer number = orderItem.getNumber(); //购买该项的产品数量
            Product product = orderItem.getProduct();
            Integer saleCount = product.getSaleCount();
            product.setSaleCount(saleCount + number);
            Integer stock = product.getStock();
            product.setStock(stock - number);
            productService.updateProduct(product); //修改销量和库存
        }
        orderService.updateOrder(order); //更新
        return Result.success();
    }

    @ApiOperation(value = "评价",notes = "评价逻辑支持")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oid",value="订单id",required=true,paramType="path",dataType = "int"),
    })
    @GetMapping("foreReview")
    public Object review(int oid) {
        Order_ order = orderService.getOrder(oid);
        List<OrderItem> orderItems = order.getOrderItems();
        Product p = orderItems.get(0).getProduct(); //这里只评价第一个商品
        List<Review> reviews = reviewService.getReviewsByProduct(p); //获取产品的评价
        productService.setReviewsAndSaleCountForProduct(p);
        productService.setSingleImageUrlFoJson(p);
        Map<String,Object> map = new HashMap<>();
        map.put("p", p);
        map.put("o", order);
        map.put("reviews", reviews);
        return Result.success(map);
    }


    @ApiOperation(value = "提交评价",notes = "提交评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oid",value="订单id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="content",value="评价内容",required=true,paramType="query",dataType = "string"),
    })
    @PostMapping("foreDoreview")
    public Object doReview(HttpSession session, int oid, int pid, String content) {
        Order_ order = orderService.getOrder(oid);
        //更新订单的状态
        order.setStatus(Constant.ORDER_FINISH.getWord()); //订单完成
        orderService.updateOrder(order); //更新
        Product p = productService.getProduct(pid);
        content = HtmlUtils.htmlEscape(content); //转义非法字符
        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date());
        review.setUser(user);
        reviewService.addReview(review);
        return Result.success();
    }
}
