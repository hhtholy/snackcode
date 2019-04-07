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
     * 跳转到分类管理 界面
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


}
