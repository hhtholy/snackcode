package com.hhtholy.controller.admin;

import com.hhtholy.entity.Order_;
import com.hhtholy.service.OrderService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author hht
 * @create 2019-05-04 17:38
 */
@RestController
@Api(tags = "后台订单模块",description = "后台订单相关的api")
public class OrderController {

 @Autowired private OrderService orderService;

    @ApiOperation(value = "获取订单列表(分页)",notes = "获取所有的订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("/orders")
    public Page<Order_> getUsers(@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                 @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        return  orderService.getOrdersPage(currentPage,Integer.valueOf(ReadProperties.getPropertyValue("pagesize","application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums","application.properties")));
    }

}
