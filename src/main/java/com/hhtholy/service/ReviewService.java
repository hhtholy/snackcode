package com.hhtholy.service;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.Review;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-19 11:10
 */
public interface ReviewService {
    List<Review> getReviewsByProduct(Product product);  //获取一个产品的评价
    int getReviewCount(Product product); //获取一个产品下的评价数量
}
