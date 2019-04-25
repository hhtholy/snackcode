package com.hhtholy.controller.fore;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

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
    @GetMapping("/registerSuccess")
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

    /**
     * 用户注销 退出
     * @return
     */
    @GetMapping("/logout")
    public String loginOut(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())  //用户已经登录的话
            subject.logout();
        return "redirect:home";
    }

    /**
     * 跳转到产品的详情信息信息展示界面
     */
    @GetMapping("/toforeProduct")
    public String toProductShow(){
        return "fore/productPage/product";
    }

    /**
     * 跳转到 分类下对应产品的展示
     * @return
     */
    @GetMapping("/toforeCategory")
    public String categoryShow(){
        return "fore/proudctPage4aCategory/product4Category";
    }


    /**
     * 跳转到 搜索结果 界面
     * @return
     */
    @GetMapping("/toForeSearch")
    public String search(){
        return "fore/searchResultPage/resultSearch";
    }

    /**
     * 点击立即购买 后生成    结算页
     */
    @GetMapping("/toOrderSetAccount")
    public String buy(){
        return "fore/orderSetAccountPage/orderSetAccount";
    }

    /**
     * 跳转到购物车页
     * @return
     */
    @GetMapping("/toCart")
    public  String cart(){
        return "fore/cartPage/cart";
    }

}
