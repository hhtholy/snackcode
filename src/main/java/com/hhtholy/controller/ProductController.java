package com.hhtholy.controller;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.ProductService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author hht
 * @create 2019-04-12 15:53
 * 产品相关的前端控制器
 */
@Api(tags = "产品模块",description = "产品相关的api")
@RestController
public class ProductController {
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;


    @ApiOperation(value = "获取产品列表",notes = "获取一个分类下所有的产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cid",value="分类id",required=true,paramType="path",dataType = "int"),
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("categories/{cid}/products")
    public Page<Product> getProductPage(@PathVariable("cid") Integer cid, @RequestParam(value = "currentPage",defaultValue = "0") Integer currentPage,
                                     @RequestParam(defaultValue = "2") Integer size) throws IOException {
        Category category = categoryService.getCategory(cid);//根据cid 分类id 查询出分类
        Page<Product> productPage = productService.getProductPage(category, currentPage, Integer.valueOf(ReadProperties.getPropertyValue("pagesize", "application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums", "application.properties")));

        productService.setSingleImageForProduct(productPage.getContent()); //设置产品单图
        return productPage;
    }

    @ApiOperation(value = "添加产品",notes = "添加产品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="product",value="产品对象",required=true,paramType="body",dataType = "com.hhtholy.entity.Product"),
    })
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return product;
    }

    @ApiOperation(value = "获取产品",notes = "根据产品id获取产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="产品id",required=true,paramType="path",dataType = "int"),
    })
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") Integer id){
        return productService.getProduct(id);
    }

    @ApiOperation(value = "更新产品",notes = "更新一个分类下的产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="产品id",required=true,paramType="path",dataType = "int"),
            @ApiImplicitParam(name="product",value="产品实体",required=true,paramType="body",dataType = "com.hhtholy.entity.Product"),
    })
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Integer id,@RequestBody Product product){
        return  productService.updateProduct(product);
    }

    @ApiOperation(value = "删除产品(包括批量)",notes = "根据产品id删除产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="产品id",required=true,paramType="path", dataType = "string"),
    })
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") String[] ids){
        String result = "success";
        try {
            for (String id : ids) {
                Integer intId = Integer.parseInt(id);
                deleteLogic(intId);
            }
        }catch (Exception e){
            result = "failure";
        }
        return result;
    }

    /**
     * 删除产品的逻辑
     * @param id
     * @return
     */
    public String deleteLogic(Integer id){
        String deleteResult = productService.deleteProduct(id);
        return deleteResult;
    }


}
