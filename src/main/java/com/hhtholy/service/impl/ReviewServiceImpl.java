package com.hhtholy.service.impl;

import com.hhtholy.dao.ReviewDao;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.Review;
import com.hhtholy.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-19 11:11
 * 评价相关的业务层
 *
 */
@Service
public class ReviewServiceImpl implements ReviewService {
      @Autowired private ReviewDao reviewDao;
    /**
     * 获取一个产品下的评价
     * @param product  产品实体
     * @return  List<Review>  产品列表
     */
    @Override
    public List<Review> getReviewsByProduct(Product product) {
        return reviewDao.findByProductOrderByIdDesc(product);
    }


    /**
     * 获取一个产品下的评价数量
     * @param product 产品实体
     * @return int 数量
     */
    @Override
    public int getReviewCount(Product product) {
        return reviewDao.countByProduct(product);
    }


    /**
     * 添加评价
     * @param review
     */
    @Override
    public void addReview(Review review) {
         reviewDao.save(review);
    }


}
