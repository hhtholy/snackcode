package com.hhtholy.controller.fore;

import com.hhtholy.entity.User;
import com.hhtholy.service.UserService;
import com.hhtholy.utils.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @author hht
 * @create 2019-04-18 16:13
 */
@RestController
@Api(tags = "前台用户模块",description = "前台用户相关的api")
public class ForeUserController {

    @Autowired private UserService userService;

    @PostMapping("/register")
    public Object register(@RequestBody User user){
        if(userService.isExist(user)){     //检测 用户是不是已经注册过
            return Result.fail("用户名已经被使用，请使用其他的用户名");
        }
        String password = user.getPassword();

        ///需要进行  加盐的操作  使用shiro的方式进行登录
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //加密次数
        int times = 2;
        //加密策略
        String algorithmName = "md5";
        //密码加密
        String encodePassword = new SimpleHash(algorithmName, password, salt, times).toString();
        //设置用户信息
        user.setSalt(salt);
        user.setPassword(encodePassword);
        userService.addUser(user);
        return Result.success();
    }

}
