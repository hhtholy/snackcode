package com.hhtholy.service;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.Review;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.Result;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-19 11:10
 */
public interface ReviewService {
    List<Review> getReviewsByProduct(Product product);  //获取一个产品的评价
    int getReviewCount(Product product); //获取一个产品下的评价数量

    void addReview(Review review); //添加评价
    public String deleteReview(Integer id); //删除评价

    Page<Review> getReviewsByProductPage(Product product,Integer currentPage, Integer size, int navigateNum); //评价分页展示

    Review getReview(Integer id);
}
