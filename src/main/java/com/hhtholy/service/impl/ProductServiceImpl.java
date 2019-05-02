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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
    @Autowired private ProductService productService;
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
        int count = 0;
        StringBuffer str = new StringBuffer();
        if(productImages != null && productImages.size() > 0){
            for (ProductImage productImageSingle : productImages) { //遍历每一个单图
                if(count == (productImages.size() - 1)){
                    str.append(productImageSingle.getImageurl());
                }else {
                    str.append(productImageSingle.getImageurl()).append(",");
                }
                count ++;
            }
            product.setImageUrlSingle(str.toString());
        }else{
            product.setImageUrlSingle(null); //如果没有详情图的话 置空
        }
        updateProduct(product); //更新数据
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
     * 为产品设置详情图
     * @param content 当前页 下的 产品列表
     */
    @Override
    public void setDetailImageForProduct(List<Product> content) {
        for (Product p: content){
            setDetailImageLogic(p);
        }
    }

    /**
     * 为产品设置一个单图
     * @param product
     */
    @Override
    public void setSingleImageUrlFoJson(Product product) {
        List<ProductImage> productImages = productImageService.getProductImage(product, Constant.SINGLEIMAGE.getWord());
        if(productImages != null && productImages.size() > 0){
            product.setSingleImageUrlForJson(productImages.get(0).getImageurl());
        }else {
            product.setSingleImageUrlForJson(null);
        }
    }

    @Override
    public void setSingleImageUrlFoJson(List<Product> content) {
        for (Product product : content) {
            setSingleImageUrlFoJson(product);
        }
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
     * @param product 产品
     */
    @Override
    public void setReviewsAndSaleCountForProduct(Product product) {
        Integer saleCount = getSaleCountForProduct(product);
        int reviewCount = reviewService.getReviewCount(product);        //根据产品获取 评价数量
        product.setReviewCount(reviewCount);
        product.setSaleCount(saleCount);
        updateProduct(product);  //更新数据
    }

    /**
     * 为产品设置 评价数量和销量
     * @param products 产品列表
     */
    @Override
    public void setReviewsAndSaleCountForProduct(List<Product> products) {
        for (Product product : products) {
            setReviewsAndSaleCountForProduct(product);
        }
    }

    /**
     * 立即购买
     * @param pid 产品id
     * @param buyNum  购买的数量
     * @param user  当前用户
     * @return  返回订单项id
     */
    @Override
    public int buyitNow(Integer pid, Integer buyNum, User user) {
        int resultOrderItemId = 0;
        boolean flag = false;
        Product product = getProduct(pid); //根据产品id获取产品
        List<OrderItem> orderItemByUser = orderItemService.getOrderItemByUser(user); //获取用户所在的订单项
        if(orderItemByUser != null && orderItemByUser.size() > 0){
            for (OrderItem orderItem : orderItemByUser) {
                  if(orderItem.getProduct().getId().equals(pid)){ //如果说已经点击过立即购买或者添加到购物车了 那么订单项的数量加1即可
                      Integer number = orderItem.getNumber();
                      number+=buyNum;
                      orderItem.setNumber(number);
                      orderItemService.updateOrderItem(orderItem); //更新订单项数据（也就是数量）
                      flag = true;
                      resultOrderItemId = orderItem.getId();
                  }
            }
        } ///if
        if(!flag){  //如果是之前也没有添加过的话  创建新的订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(buyNum);
            orderItem.setUser(user); //关联用户
            orderItem.setProduct(product); //关联产品
            orderItemService.addOrderItem(orderItem); //存取进数据库
            resultOrderItemId = orderItem.getId();
        }
        return resultOrderItemId;
    }

    /**
     * 根据产品关键字去查询产品
     * @param keyword  关键字
     * @param currentPage 当前页
     * @param size  每页显示的条数
     * @return
     */
    @Override
    public Page<Product> searchProductByKey(final String keyword, int currentPage, int size) {
            Pageable pageable = new  PageRequest(currentPage, size, Sort.Direction.ASC, "id");    //分页条件
            org.springframework.data.domain.Page<Product> resultGet = productDao.findAll(new Specification<Product>() {
             @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> arrayList = new ArrayList<Predicate>();
                if (keyword != null) {
                    arrayList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + keyword + "%"));
                }
                Predicate[] p = new Predicate[arrayList.size()];
                return criteriaBuilder.and(arrayList.toArray(p));
            }
        }, pageable);
        return new Page<>(resultGet,5);
    }

}
