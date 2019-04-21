package com.hhtholy.controller.fore;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.PropertyValue;
import com.hhtholy.entity.Review;
import com.hhtholy.service.ProductService;
import com.hhtholy.service.PropertyValueService;
import com.hhtholy.service.ReviewService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


}
