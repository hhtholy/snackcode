package com.hhtholy.controller.fore;

import com.hhtholy.entity.Order_;
import com.hhtholy.service.OrderService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hht
 * @create 2019-05-02 15:51
 */
@RestController
@Api(tags = "前台订单模块",description = "前台订单相关的api")
public class ForeOrderController {

    @Autowired private OrderService orderService;

    @GetMapping("foreDeleteOrder")
    public Object deleteOrder(int oid){
        Order_ order = orderService.getOrder(oid);
        order.setStatus(Constant.ORDER_DELETE.getWord());
        orderService.updateOrder(order);
        return Result.success();
    }
}
