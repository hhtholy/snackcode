package com.hhtholy.controller.fore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author hht
 * @create 2019-04-18 11:25
 *
 * 前台相关的页面跳转前端控制器
 */
@ApiIgnore
@Controller
public class ForePageController {

    /**
     * 跳转到主界面
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "redirect:home";
    }
    /**
     * 跳转主界面
     * @return
     */
    @GetMapping("/home")
    public String toHome(){
        return "fore/home";
    }

    /**
     * 跳转到 注册界面
     * @return
     */
    @GetMapping("/toRegister")
    public String toRegisterPage(){
        return "fore/registerPage/register";
    }


    /**
     * 跳转到注册成功界面
     * @return
     */
    public String toRegisterSuccessPage(){
        return "fore/registerPage/registerSuccess";
    }

    /**
     * 跳转到登录界面
     * @return
     */
    @GetMapping("/toLogin")
    public String toLoginPage(){
        return "fore/loginPage/login";
    }

}
