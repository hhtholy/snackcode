package com.hhtholy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author hht
 * @create 2019-04-04 15:39
 *
 * 页面跳转前端相关控制器
 */
@ApiIgnore
@Controller
public class PageController {

    /**
     * 跳转到主页  主页是分类管理
     * @return
     */
       @GetMapping(value = "/admin")
      public String toAdmin(){
          return "redirect:admin_category_list";
       }

    /**
     * 跳转到分类管理 页面
     * @return
     */
    @GetMapping("/admin_category_list")
      public String categoryList(){
        return "admin/listCategory";
      }

    /**
     * 跳转到属性管理 页面
     * @return
     */
    @GetMapping("/admin_property_list")
    public String propertyList(){
        return "admin/listProperty";
    }



    /**
     * 跳转到 产品管理界面
     * @return
     */
    @GetMapping("/admin_product_list")
    public String productList(){
        return "admin/listProduct";
    }

    /**
     * 跳转到   图片管理界面 （针对产品）
     * @return
     */
    @GetMapping("/admin_productImage_list")
    public String productImageList(){
        return "admin/listProductImage";
    }


    /**
     * 跳转到属性值的设定界面
     * @return
     */
    @GetMapping("/admin_propertyValue_edit")
    public String propertyValueSet(){
        return "admin/editPropertyValue";
    }


    /**
     * 跳转到 用户展示界面
     * @return
     */
    @GetMapping("/admin_user_list")
    public String listUser(){
        return "admin/listUser";
    }

    /**
     * 跳转到订单展示界面
     * @return
     */
    @GetMapping("/admin_order_list")
    public String listOrders(){

        return "admin/listOrder";
    }


}
