package com.hhtholy.controller.admin;

import com.hhtholy.entity.User;
import com.hhtholy.service.UserService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import com.hhtholy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author hht
 * @create 2019-04-16 15:14
 * 用户相关的前端控制器
 */
@RestController
@Api(tags = "后台用户模块",description = "后台用户相关的api")
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


    /**
     * 管理员登录
     */
    @PostMapping("/backUserLogin")
     public Object adminLogin(@RequestBody User user, HttpSession session){

        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject(); //获取当前用户
        try{
            subject.login(token);
            boolean administrator = subject.hasRole("administrator");
            if(!administrator){
                return Result.fail("对不起~~ 你没有此权限~~");
            }
            User result= userService.getUserByName(user.getName());
            session.setAttribute("userAdmin",result);     //登录成功后 把信息放在session中
            return Result.success();
        }catch (AuthenticationException e){
            String message = "账号或者密码错误";
            return Result.fail(message);
        }
     }
}
