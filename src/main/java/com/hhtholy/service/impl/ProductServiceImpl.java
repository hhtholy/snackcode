package com.hhtholy.service.impl;

import com.hhtholy.dao.ProductDao;
import com.hhtholy.entity.*;
import com.hhtholy.service.OrderItemService;
import com.hhtholy.service.ProductImageService;
import com.hhtholy.service.ProductService;
import com.hhtholy.service.ReviewService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Page;
import oracle.jrockit.jfr.jdkevents.throwabletransform.ConstructorTracerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hht
 * @create 2019-04-12 15:59
 * 产品业务相关的实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductDao productDao;
    @Autowired private ProductImageService productImageService;
    @Autowired private OrderItemService orderItemService;
    @Autowired private ReviewService reviewService;
    /**
     *   查询产品数据 分页
     * @param category 分类
     * @param currentPage 当前页
     * @param size  每页显示的大小
     * @param navigateNum 导航页的长度
     * @return  Page<Product>  产品的分页数据
     */
    @Override
    public Page<Product> getProductPage(Category category, Integer currentPage, Integer size, Integer navigateNum) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");  //创建分页对象
        Pageable pageable = new PageRequest(currentPage, size, sort);
        org.springframework.data.domain.Page<Product> page= productDao.findByCategory(category, pageable);
        return new Page<>(page,navigateNum);
    }

    /**
     * 添加产品
     * @param product 产品（实体）
     * @return  添加后的产品
     */
    @Override
    public Product addProduct(Product product) {
        return productDao.save(product);
    }

    /**
     * 根据产品id 删除产品
     * @param id 产品id
     * @return  删除标志
     */

    @Override
    public String deleteProduct(Integer id) {
        String result = null;
        try {
            productDao.deleteById(id);
            result = "success";
        } catch (Exception e){
            result = "failure";
        }
        return result;
    }

    /**
     * 根据id去获取产品
     * @param id 产品id
     * @return 对应的产品
     */
    @Override
    public Product getProduct(Integer id) {
        Product product = new Product();
        product.setId(id);
        Example<Product> example = Example.of(product);
        Optional<Product> optional = productDao.findOne(example);
        Product result = null;
        if(optional.isPresent()){
            result = optional.get();
        }
        return result;
    }

    /**
     * 更新产品信息
     * @param product 产品（实体）
     * @return 更新后的产品
     */
    @Override
    public Product updateProduct(Product product) {
        Product result = null;
        try {
            result = productDao.save(product);
        }catch (Exception e){
            result = null;
        }
        return result;
    }

    /**
     *  为产品设置单图
     * @param content 当前页 下的 产品列表
     */
    @Override
    public void setSingleImageForProduct(List<Product> content) {
            for (Product p: content){
                setSingleImageLogic(p);
            }
    }

    public void setSingleImageLogic(Product product) {
        //根据产品的去获取 单图 获取的是一个列表
        List<ProductImage> productImages = productImageService.getProductImage(product, Constant.SINGLEIMAGE.getWord());
        if(productImages != null && productImages.size() > 0){
            product.setImageUrlSingle(productImages.get(0).getImageurl());
        }else{
            product.setImageUrlSingle(null); //如果没有单图的话 置空
        }
        updateProduct(product); //更新数据
    }

    /**
     * 为产品设置详情图
     * @param content 当前页 下的 产品列表
     */
    @Override
    public void setDetailImageForProduct(List<Product> content) {
        for (Product p: content){
            setDetailImageLogic(p);
        }
    }

    public void setDetailImageLogic(Product product) {
        int count = 0;
        StringBuffer str = new StringBuffer();
        //根据产品的去获取 详情图 获取的是一个列表
        List<ProductImage> productImages = productImageService.getProductImage(product, Constant.DETAILIMAGE.getWord());
        if(productImages != null && productImages.size() > 0){
            for (ProductImage productImageDetail : productImages) { //遍历每一个详情图
                if(count == (productImages.size() - 1)){
                    str.append(productImageDetail.getImageurl());
                }else {
                    str.append(productImageDetail.getImageurl()).append(",");
                }
                count ++;
            }
            product.setImageUrlsDetail(str.toString());
        }else{
            product.setImageUrlsDetail(null); //如果没有详情图的话 置空
        }
        updateProduct(product); //更新数据
    }

    /**
     * 获取一个分类下的所有产品
     * @param category
     * @return
     */
    @Override
    public List<Product> getProductList(Category category) {
        return productDao.findByCategory(category);
    }

    /**
     * 获取产品的销量
     * @param product 产品
     * @return
     */
    @Override
    public Integer getSaleCountForProduct(Product product) {
        List<OrderItem> results = orderItemService.getOrderItemByProduct(product);   //获取 订单项中 拥有该产品的所有订单项
        int count = 0;
        for (OrderItem item:results){  //遍历每一个订单项
            if(item.getOrder() != null && item.getOrder().getPayDate() != null){   //首先确定是创建了订单的  同时是已经付款的
                count += item.getNumber();
            }
        }
        return count;
    }

    /**
     * 为产品设置 评价数量（review） 和 销量(saleCount)
     * @param product
     */
    @Override
    public void setReviewsAndSaleCountForProduct(Product product) {
        Integer saleCount = getSaleCountForProduct(product);
        //根据产品获取 评价数量
        int reviewCount = reviewService.getReviewCount(product);
        product.setReviewCount(reviewCount);
        product.setSaleCount(saleCount);
    }

    /**
     * 为产品设置 评价数量和销量
     * @param products
     */
    @Override
    public void setReviewsAndSaleCountForProduct(List<Product> products) {
        for (Product product : products) {
            setReviewsAndSaleCountForProduct(product);
        }
    }


}
