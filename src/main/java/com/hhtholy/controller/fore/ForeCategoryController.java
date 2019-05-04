package com.hhtholy.controller.fore;

import com.hhtholy.entity.Category;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.ProductService;
import com.hhtholy.utils.comparator.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-22 19:18
 */

@Api(tags = "前台分类模块",description = "前台分类相关的api")
@RestController
public class ForeCategoryController {
    @Autowired private CategoryService categoryService;
    @Autowired private ProductService productService;



    @ApiOperation(value = "获取分类",notes = "前台获取分类")
    @GetMapping("/forehome")
    public Object home() {
         List<Category> cs= categoryService.getCategoryList(); //获取所有的分类
         productService.fillCategoryData(cs); // 分类产品标题 按行显示
         for (Category c : cs) {
            categoryService.setProductsForJsonOfCategory(c); //为分类设置产品值
         }
        return cs;
    }


    @GetMapping("/forecategory/{cid}")
    public Object category(@PathVariable("cid") Integer cid, String sortType){
        Category category = categoryService.getCategory(cid);  //首先根据id 查询出分类信息
        productService.setReviewsAndSaleCountForProduct(category.getProducts());//为产品 设置上 评价数量和销量
        categoryService.setProductsForJsonOfCategory(category); //为分类设置产品值
        if(sortType != null){
            switch (sortType){
                case "review":
                    Collections.sort(category.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(category.getProducts(),new ProductDateComparator());
                    break;
                case "saleCount" :
                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(),new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(),new ProductAllComparator());
                    break;
            }
        }
        return category;
    }
}
