package com.hhtholy.dao;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-12 16:02
 * 产品相关的数据层接口
 */
public interface ProductDao extends JpaRepository<Product,Integer> {
    Page<Product> findByCategory(Category category, Pageable pageable);  //查询一个分类下的所有产品 分页的方式
    List<Product> findByCategory(Category category);   //不分页的方式
}
