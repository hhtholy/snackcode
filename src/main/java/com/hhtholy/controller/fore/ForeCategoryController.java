package com.hhtholy.controller.fore;

import com.hhtholy.entity.*;
import com.hhtholy.service.*;
import com.hhtholy.service.impl.RecommendService;
import com.hhtholy.utils.comparator.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author hht
 * @create 2019-04-22 19:18
 */

@Api(tags = "前台分类模块",description = "前台分类相关的api")
@RestController
@Slf4j
public class ForeCategoryController {
    @Autowired private CategoryService categoryService;
    @Autowired private ProductService productService;

    @Autowired private OrderService orderService;

    @Autowired private RecommendService recommendService;


    /**
     * 跳转到主页  这里需要处理智能推荐
     * @return
     */

    @ApiOperation(value = "获取分类",notes = "前台获取分类")
    @GetMapping("/forehome")
    public Object home(HttpSession session, HttpServletRequest request) {
        User user =(User)  session.getAttribute("user"); //用户登录数据
        List<Category> cs= categoryService.getCategoryList(); //获取所有的分类
         productService.fillCategoryData(cs); // 分类产品标题 按行显示
         for (Category c : cs) {
            categoryService.setProductsForJsonOfCategory(c); //为分类设置产品值
         }
        List<Order_> orders = orderService.getOrders(); //所有订单
        List<Product> products = recommendService.recommedProducts(user, orders);//推荐的产品
//        List<Category> categories = recommendService.recommedCategories(user, orders);

        List<Product> list = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productService.setSingleImageUrlFoJson(products.get(i)); //设置单图
            if(i >= 1){
                list.add(products.get(i));
            }
        }
        String contextPath= request.getServletContext().getContextPath();
        //request.getServletContext().setAttribute("categories_below_search", categories);
        HashMap<String, Object> map = new HashMap<>();
        map.put("active",products.get(0));
        map.put("cs",cs);
        map.put("recommend",list);

        return map;
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
