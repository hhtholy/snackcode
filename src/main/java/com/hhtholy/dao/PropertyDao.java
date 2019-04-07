package com.hhtholy.dao;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-06 20:33
 */
public interface PropertyDao extends JpaRepository<Property,Integer> {
    Page<Property> findByCategory(Category category, Pageable pageable);  //查询一个分类下的所有属性 分页的方式
    List<Property> findByCategory(Category category);   //不分页的方式
}
