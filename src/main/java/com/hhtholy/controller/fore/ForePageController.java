package com.hhtholy.controller.fore;

import com.hhtholy.utils.Result;
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
     * 用户注销 退出  已经在自定义Filter做了处理
     * @return
     */
   /* @GetMapping("/logout")
    public String loginOut(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        boolean flag = subject.hasRole("administrator");
        if(subject.isAuthenticated())  //用户已经登录的话
            subject.logout();
        if(flag){
            return "redirect:admin"; //如果是后台管理员的话
        }else {
            return "redirect:home";
        }
    }*/

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


    /**
     * 阿里支付  跳转到支付请求发起的界面  携带金额等数据过去
     * @param oid
     * @param
     * @return
     */
    @GetMapping("/alipay")
    public String alipay(String oid,String total_fee,String out_trade_no,String subject,String body){

        return "pay/payRequest";
    }

    /**
     *   跳转到支付成功界面
     * @param oid  订单id
     * @return
     */
    @GetMapping("/paySuccess")
    public String paySuccess(String oid){
        return "pay/paySuccess";
    }


    /**
     * 跳转到我的订单页 全部订单
     * @param oid
     * @return
     */
    @GetMapping("/bought")
    public String toMyOrders(String oid){
        return "fore/MyOrderPage/myOrderPage";
    }

    /**
     *  跳转到我的订单页  全部订单
     * @param oid
     * @return
     */
    @GetMapping("/all")
    public String toAllMyOrders(String oid){
        return "fore/MyOrderPage/myOrderPage";
    }


    /**
     * 未付款
     * @param oid
     * @return
     */
    @GetMapping("/waitPay")
    public String toMywaitPayOrders(String oid){
        return "fore/MyOrderPage/waitPayOrderPage";
    }

    /**
     * 待发货
     * @param oid
     * @return
     */
    @GetMapping("/waitDelivery")
    public String toMywaitDeliveryOrders(String oid){
        return "fore/MyOrderPage/waitDeliveryOrderPage";
    }

    /**
     * 待收货
     * @param oid
     * @return
     */
    @GetMapping("/waitConfirm")
    public String toMywaitConfirmOrders(String oid){
        return "fore/MyOrderPage/waitConfirmOrderPage";
    }


    /**
     * 待评价
     * @param oid
     * @return
     */
    @GetMapping("/waitReview")
    public String toMywaitReviewOrders(String oid){
        return "fore/MyOrderPage/waitReviewOrderPage";
    }


    /**
     * 确认收货
     * @return
     */
    @GetMapping(value="/confirmPay")
    public String confirmPay(){
        return "fore/confirmPayPage/confirmPay";
    }


    /**
     * 跳转到评价界面
     * @return
     */
    @GetMapping(value="/review")
    public String review(){
        return "fore/reviewPage/review";
    }


    @GetMapping(value="/recommend")
    public String recommend(){
        return "fore/recommendPage/recommend";
    }


}
