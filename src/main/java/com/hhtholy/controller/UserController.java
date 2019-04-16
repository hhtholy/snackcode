package com.hhtholy.controller;

import com.hhtholy.entity.User;
import com.hhtholy.service.UserService;
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
 * @create 2019-04-16 15:14
 * 用户相关的前端控制器
 */
@RestController
@Api(tags = "用户模块",description = "用户相关的api")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户列表(分页)",notes = "获取所有的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("/users")
    public Page<User> getUsers(@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                               @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        return  userService.getUserPage(currentPage,Integer.valueOf(ReadProperties.getPropertyValue("pagesize","application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums","application.properties")));
    }
}
