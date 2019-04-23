package com.hhtholy.controller.fore;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.PropertyValue;
import com.hhtholy.entity.Review;
import com.hhtholy.entity.User;
import com.hhtholy.service.ProductService;
import com.hhtholy.service.PropertyValueService;
import com.hhtholy.service.ReviewService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-20 17:34
 */
@RestController
@Api(tags = "前台产品模块",description = "前台产品相关的api")
public class ForeProductController {
     @Autowired private ProductService productService;
     @Autowired private PropertyValueService propertyValueService;
     @Autowired private ReviewService reviewService;


    @ApiOperation(value = "产品展示",notes = "对应产品的展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="path",dataType = "int")
    })
    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") Integer pid){
        Product product = productService.getProduct(pid);   //根据pid  获取产品
        productService.setSingleImageUrlFoJson(product); //单图
        List<PropertyValue> propertys = propertyValueService.getPropertyByProduct(product);  //查询出 产品的属性值
        List<Review> reviews = reviewService.getReviewsByProduct(product);  //查询出 产品的评价
        productService.setReviewsAndSaleCountForProduct(product); //设置产品的 评价数量和销量
        HashMap<String, Object> map = new HashMap<>();//把数据放在map中
        map.put("product",product);  //产品
        map.put("pvalues",propertys);  //产品的属性值
        map.put("reviews",reviews); //产品的评价
        return Result.success(map);
    }


    @ApiOperation(value = "购物车",notes = "添加产品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="num",value="购买产品的数量",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="session",value="用户session",required=true,allowMultiple = true,dataType = "javax.servlet.http.HttpSession")
    })
    @GetMapping("/foreaddCart")
    public Object addCart(Integer pid, Integer num, HttpSession session){
        User user = (User) session.getAttribute("user");        //获取 用户的数据
        Integer orderItemId = productService.buyitNow(pid, num, user); //返回的是订单项的id
        return Result.success();
    }

    @ApiOperation(value = "立即购买",notes = "点击立即购买商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="num",value="购买产品的数量",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="session",value="用户session",required=true,allowMultiple = true,dataType = "javax.servlet.http.HttpSession")
    })
    @GetMapping("/buyitNow")
    public Object buyitNow(Integer pid,Integer num,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer orderItemId = productService.buyitNow(pid, num, user);
        return orderItemId;
    }


    @ApiOperation(value = "根据关键字查询产品",notes = "根据关键字查询产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword",value="产品关键字",required=true,paramType="query",dataType = "string"),
    })
    @GetMapping("/searchProduct")
    public  Object search(@RequestParam("keyword") String keyword){
        Page<Product> searchResult = productService.searchProductByKey(keyword, 0, 20);     //查询结果
        List<Product> content = searchResult.getContent();
        //设置产品的图片  单图显示
       // productService.setFirstImagesForProduct(content);

        //设置 产品的 销量和评价数量
      //  productService.setReviewsAndSaleCount(content);


        return searchResult;
    }



}
