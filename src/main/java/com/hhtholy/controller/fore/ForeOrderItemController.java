package com.hhtholy.controller.fore;

import com.hhtholy.entity.OrderItem;
import com.hhtholy.entity.User;
import com.hhtholy.service.OrderItemService;
import com.hhtholy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-28 20:17
 */
@RestController
public class ForeOrderItemController {
    @Autowired private OrderItemService orderItemService;

    /**
     * 更新订单项中产品的数量
     * @param session
     * @param pid 产品id
     * @param num  新的数量
     * @return
     */
    @GetMapping("foreChangeOrderItemProductNums")
    public Object changeOrderItem(HttpSession session, Integer pid, int num) {
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("未登录");
        List<OrderItem> ois = orderItemService.getOrderItemByUserAndIncart(user,1);//获取该用户的所有订单项
       try {
           for (OrderItem oi : ois) {
               if(oi.getProduct().getId().equals(pid)){ //查询出 当前产品的订单项
                   oi.setNumber(num);
                   orderItemService.updateOrderItem(oi); //更新订单项产品的数量
                   break;
               }
           }
       }catch (Exception e){
             return  Result.fail("出现错误~~~");
       }

        return Result.success();
    }


    /**
     * 删除 订单项
     * @param session
     * @param oiid  订单项的id
     * @return
     */
    @GetMapping("foreDeleteOrderItem")
    public Object deleteOrderItem(HttpSession session,int oiid){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("未登录");
        try {
            orderItemService.deleteOrderItem(oiid);
            return Result.success();
        }catch (Exception e){
            return Result.fail("出现异常");
        }
    }




}
