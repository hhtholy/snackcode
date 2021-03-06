package com.hhtholy.dao;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import com.hhtholy.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-19 11:09
 *
 * 评价相关的数据层接口
 */
public interface ReviewDao extends JpaRepository<Review,Integer> {
    List<Review> findByProductOrderByIdDesc(Product product); //获取一个产品下的评价
    int countByProduct(Product product);   //获取一个产品的数量
    Page<Review> findByProduct(Product product, Pageable pageable);  //查询一个产品下的所有评论 分页的方式
}
