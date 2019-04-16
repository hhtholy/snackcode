package com.hhtholy.service;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.utils.Page;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-12 15:55
 * 产品相关的业务层接口
 */
public interface ProductService { //查询一个分类下的产品信息  分页
    public Page<Product> getProductPage(Category category, Integer currentPage, Integer size, Integer navigateNum);//分页查询 分页信息
    public Product addProduct(Product product);    //添加产品
    public String deleteProduct(Integer id);   //删除产品
    public Product getProduct(Integer id); //根据id 获取产品
    public Product updateProduct(Product product);    //更新产品
    public void setSingleImageForProduct(List<Product> content); //为产品设置单图
}
