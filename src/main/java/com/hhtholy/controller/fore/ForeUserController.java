package com.hhtholy.controller.fore;

import com.aliyuncs.exceptions.ClientException;
import com.hhtholy.entity.User;
import com.hhtholy.entity.UserVo;
import com.hhtholy.service.UserService;
import com.hhtholy.utils.Result;
import com.hhtholy.utils.aliSendMs.SendMs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author hht
 * @create 2019-04-18 16:13
 */
@RestController
@Api(tags = "前台用户模块",description = "前台用户相关的api")
public class ForeUserController {

    @Autowired private UserService userService;

    @ApiOperation(value = "注册用户",notes = "注册用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="user",value="用户信息",required=true,paramType="body",allowMultiple = true,dataType = "com.hhtholy.entity.User")
    })
    @PostMapping("/register")
    public Object register(@RequestBody User user){
        if(userService.isExist(user)){     //检测 用户是不是已经注册过
            return Result.fail("用户名已经被使用，请使用其他的用户名");
        }
        String password = user.getPassword();
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();  //需要进行  加盐的操作  使用shiro的方式进行登录
        int times = 2;        //加密次数
        String algorithmName = "md5";        //加密策略
        String encodePassword = new SimpleHash(algorithmName, password, salt, times).toString();        //密码加密
        user.setSalt(salt);        //设置用户信息
        user.setPassword(encodePassword);
        userService.addUser(user);
        return Result.success();
    }
    @ApiOperation(value = "用户登录",notes = "用户进行身份登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="user",value="用户信息",required=true,paramType="body",allowMultiple = true,dataType = "com.hhtholy.entity.User"),
            @ApiImplicitParam(name="session",value="session信息",required=true,paramType="",allowMultiple = true,dataType = "javax.servlet.http.HttpSession")
    })
    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpSession session){

        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject(); //获取当前用户
        try{
            subject.login(token);
            User result= userService.getUserByName(user.getName());
            session.setAttribute("user",result);     //登录成功后 把信息放在session中
            return Result.success();
        }catch (AuthenticationException e){
            String message = "账号或者密码错误";
            return Result.fail(message);
        }
    }

    /**
     * 检测用户有没有登录
     * @return
     */
    @GetMapping("/foreCheckLogin")
    public Object checkUserIsLongin(){
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() == false?Result.fail("用户没有登录"):Result.success();
    }



    @GetMapping("/sendMs")
    public Object sendMs(String phone) throws ClientException {
         int min = 10000;
         int max = 99999;
         int code = new Random().nextInt(max - min + 1) + min;
         String codeRe  = SendMs.sendMs(phone, String.valueOf(code)); //发送短信
          return Result.success(codeRe);
    }
    @PostMapping("/userfindpass")
    public Object findPass(@RequestBody UserVo userVo) throws ClientException {
        String name = userVo.getName();
        User userByName = userService.getUserByName(name);
        if(userByName == null){
            return Result.fail("不存在该用户~~  请核对~~");
        }
        String password = userVo.getPassword();
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();  //需要进行  加盐的操作  使用shiro的方式进行登录
        int times = 2;        //加密次数
        String algorithmName = "md5";        //加密策略
        String encodePassword = new SimpleHash(algorithmName, password, salt, times).toString();        //密码加密
        userByName.setPassword(encodePassword);
        userByName.setSalt(salt);        //设置用户信息
        userService.updateUser(userByName);
        return  Result.success();

    }

}
