package com.hhtholy.service;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.User;
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
    public String deleteProduct(Integer id);   //删除产品 改状态
    public String putAwayNoProduct(Integer id); //下架商品
    public String putAwayYesProduct(Integer id); //上架商品
    public Product getProduct(Integer id); //根据id 获取产品

    public Product getProductIsOnLine(Integer id); //上架商品
    public Product updateProduct(Product product);    //更新产品
    public void setSingleImageForProduct(List<Product> content); //为产品设置单图
    public void setDetailImageForProduct(List<Product> content); //为产品设置详情图
    public void setSingleImageUrlFoJson(Product product); //一个单图 不存在数据库中
    public void setSingleImageUrlFoJson(List<Product> content);
    public List<Product> getProductList(Category category);  //不能包括下架 、 已经删除的商品
    public Integer getSaleCountForProduct(Product product);//获取产品的销量
    public void setReviewsAndSaleCountForProduct(Product product);     //为产品设置销量 和 评价数量
    public void setReviewsAndSaleCountForProduct(List<Product> products);
    public int buyitNow(Integer pid, Integer buyNum, User user);  //立即购买产品
    public Page<Product> searchProductByKey(String keyword,int currentPage, int size);   //根据关键词去查询出 产品结果

    List<Product> getProductByName(String name);//根据名称获取产品
    public void fillCategoryData(List<Category> cs); // 分类产品标题 按行显示

    public int getMaxId();
    public int getMinId();
}
