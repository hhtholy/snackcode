package com.hhtholy.service.impl;

import com.hhtholy.dao.ReviewDao;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import com.hhtholy.entity.Review;
import com.hhtholy.service.ReviewService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.Result;
import com.sun.org.apache.regexp.internal.RE;
import oracle.jrockit.jfr.events.RequestableEventEnvironment;
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

    /**
     * 删除 评价
     * @param id
     * @return
     */
    @Override
    public String deleteReview(Integer id) {
        String result = null;
        try {
            reviewDao.deleteById(id);
            result = "success";
        } catch (Exception e){
            result = "failure";
        }
        return result;
    }
    /**
     *   评价分页展示  对应商品
     * @param currentPage
     * @param size
     * @param navigateNum
     * @return
     */
    @Override
    public Page<Review> getReviewsByProductPage(Product product,Integer currentPage, Integer size, int navigateNum) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");  //创建分页对象
        Pageable pageable = new PageRequest(currentPage, size, sort);
        org.springframework.data.domain.Page<Review> reviews = reviewDao.findByProduct(product, pageable);

        return new Page<>(reviews,navigateNum);
    }

    /**
     * 获取评价 根据id
     * @param id
     * @return
     */
    @Override
    public Review getReview(Integer id) {
        Review review = new Review();
        review.setId(id);
        Example<Review> example = Example.of(review);
        Optional<Review> optional = reviewDao.findOne(example);
        Review result = null;
        if(optional.isPresent()){
            result = optional.get();
        }
        return result;
    }


}
