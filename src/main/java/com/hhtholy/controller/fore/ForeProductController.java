package com.hhtholy.controller.fore;

import com.hhtholy.entity.*;
import com.hhtholy.service.*;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.Result;
import com.hhtholy.utils.aliPay.Pay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-20 17:34
 */
@RestController
@Api(tags = "前台产品模块",description = "前台产品相关的api")
public class ForeProductController {
     @Autowired private ProductService productService;
     @Autowired private PropertyValueService propertyValueService;
     @Autowired private ReviewService reviewService;
     @Autowired private OrderItemService orderItemService;
    @Autowired private OrderService orderService;


    @ApiOperation(value = "产品展示",notes = "对应产品的展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="path",dataType = "int")
    })
    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") Integer pid){
        Product product = productService.getProduct(pid);   //根据pid  获取产品
        productService.setSingleImageUrlFoJson(product); //单图
        List<PropertyValue> propertys = propertyValueService.getPropertyByProduct(product);  //查询出 产品的属性值
        List<Review> reviews = reviewService.getReviewsByProduct(product);  //查询出 产品的评价
        productService.setReviewsAndSaleCountForProduct(product); //设置产品的 评价数量和销量
        HashMap<String, Object> map = new HashMap<>();//把数据放在map中
        map.put("product",product);  //产品
        map.put("pvalues",propertys);  //产品的属性值
        map.put("reviews",reviews); //产品的评价
        return Result.success(map);
    }


    @ApiOperation(value = "购物车",notes = "添加产品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="num",value="购买产品的数量",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="session",value="用户session",required=true,allowMultiple = true,dataType = "javax.servlet.http.HttpSession")
    })
    @GetMapping("/foreaddCart")
    public Object addCart(Integer pid, Integer num, HttpSession session){
        User user = (User) session.getAttribute("user");        //获取 用户的数据
        Integer orderItemId = productService.buyitNow(pid, num, user); //返回的是订单项的id
        return Result.success();
    }

    @ApiOperation(value = "立即购买",notes = "点击立即购买商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="num",value="购买产品的数量",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="session",value="用户session",required=true,allowMultiple = true,dataType = "javax.servlet.http.HttpSession")
    })
    @GetMapping("/buyitNow")
    public Object buyitNow(Integer pid,Integer num,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer orderItemId = productService.buyitNow(pid, num, user);
        return orderItemId;
    }


    @ApiOperation(value = "根据关键字查询产品",notes = "根据关键字查询产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword",value="产品关键字",required=true,paramType="query",dataType = "string"),
    })
    @GetMapping("/searchProduct")
    public  Object search(@RequestParam("keyword") String keyword){
        Page<Product> searchResult = productService.searchProductByKey(keyword, 0,20); //这里仅仅是查询全20条数据
        productService.setSingleImageUrlFoJson(searchResult.getContent()); //设置单图（第一个）不存入数据库
        productService.setReviewsAndSaleCountForProduct(searchResult.getContent());     //设置 产品的 销量和评价数量
        return searchResult;
    }

    @ApiOperation(value = "结算页数据填充",notes = "返回订单项对应产品和金额等数据到结算页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oiids",value="订单项id(数组)",required=true,paramType="query",dataType = "string"),
    })
    @GetMapping("forebuy") //点击立即购买成功后  返回订单项id  然后请求到这（携带订单项id）
    public Object buy(String[] oiids,HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0; //订单项的总金额
        for (String oiid : oiids) {  //遍历订单项id
            OrderItem orderItem = orderItemService.getOrderItem(Integer.parseInt(oiid));//根据订单项id查询出订单项
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber(); //每一项的价格
            orderItems.add(orderItem);
            productService.setSingleImageUrlFoJson(orderItem.getProduct()); //为产品设置单图
        }
        session.setAttribute("orderItems",orderItems); //订单项 放入session中
        HashMap<Object, Object> map = new HashMap<>();
        map.put("orderItems",orderItems);
        map.put("total",total);
        return Result.success(map);
    }
    @ApiOperation(value = "购物车信息展示",notes = "展示购物车中的产品和金额等数据")
    @GetMapping("/forecart")
    public Object showCart(HttpSession session){
        User user =(User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.getOrderItemByUser(user);//根据用户获取订单项
        for (OrderItem orderItem : orderItems) {  //为订单项对应产品 设置单图
            productService.setSingleImageUrlFoJson(orderItem.getProduct());
        }
        return orderItems;
    }


    @ApiOperation(value = "提交订单",notes = "填写好订单信息，将订单项信息和订单绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name="order",value="订单项id(数组)",required=true,paramType="query",dataType = "string"),
    })
    @PostMapping("/foreCreateOrder")
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public Object createOrder(@RequestBody Order_ order, HttpSession session){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("未登录");
        String code = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + RandomUtils.nextInt(1000);        //生成随机的订单码
        order.setOrderCode(code);
        order.setUser(user);
        order.setCreateDate(new Date());
        order.setStatus(Constant.ORDER_WAITPAY.getWord()); //订单状态 待付款
        List<OrderItem> orderItems = (List<OrderItem>)session.getAttribute("orderItems"); //拿到session中的订单项
        //创建订单
        List<Object> totals = orderService.addOrder(order, orderItems);
        Float  price = (Float) totals.get(0); // 价格等信息放在 集合中
        Integer nums = (Integer) totals.get(1);
        order.setTotalPrice(price);
        order.setTotalNum(nums);
        orderService.updateOrder(order);
        //返回数据 一个是订单id  一个是订单总额
        HashMap<String, Object> map = new HashMap<>();
        Pay pay = new Pay(); //支付宝支付会用到的数据
        pay.setOut_trade_no(code); //商户订单号
        pay.setSubject("商品购买"); //订单名称
        pay.setBody("商品购买"); //订单描述

        map.put("total",price); //订单总额
        map.put("oid",order.getId()); //订单id
        map.put("pay",pay); //支付实体
        return Result.success(map);
    }


    /**
     * 查看我的订单页
     * @param session
     * @return
     */
    @GetMapping("/foreBought")
    public Object bought(HttpSession session,@RequestParam("type") String type) {
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("未登录");
        List<Order_> orderList = null;
        if(type.equals(Constant.ORDER_WAITPAY.getWord())){ //订单待支付
            orderList= orderService.getOrdersByUserAndStatus(user,Constant.ORDER_WAITPAY.getWord());
        }else if(type.equals(Constant.ORDER_WAITDELIVERY.getWord())){ //订单已经支付待发货
            orderList= orderService.getOrdersByUserAndStatus(user,Constant.ORDER_WAITDELIVERY.getWord());
        }else if(type.equals(Constant.ORDER_WAITCONFIRM.getWord())){ //订单已经发货待收货
            orderList= orderService.getOrdersByUserAndStatus(user,Constant.ORDER_WAITCONFIRM.getWord());
        }else if(type.equals(Constant.ORDER_WAITREVIEW.getWord())){ //订单已经收货 待评价
            orderList= orderService.getOrdersByUserAndStatus(user,Constant.ORDER_WAITREVIEW.getWord());
        }else{
            orderList= orderService.getOrdersByUser(user);  //根据用户查询订单
        }
        for (Order_ order : orderList) { // 遍历sql
            List<OrderItem> orderItems = order.getOrderItems(); //获取订单下的订单项
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(null);
                productService.setSingleImageUrlFoJson(orderItem.getProduct()); //设置产品单图
            }
            order.setOrderItemsForJson(orderItems);
        }
        return Result.success(orderList);
    }
}
