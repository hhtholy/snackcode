package com.hhtholy.dao;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-12 16:02
 * 产品相关的数据层接口
 */
public interface ProductDao extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {
    Page<Product> findByCategory(Category category, Pageable pageable);  //查询一个分类下的所有产品 分页的方式
    List<Product> findByCategory(Category category);   //不分页的方式
    @Query(value = "select * from product where name=?1",nativeQuery = true)
    List<Product> getProductOfName(String name); //根据产品名称去查询产品

    @Query(value = "select max(id) from product",nativeQuery = true)
    public int getMaxId();

    @Query(value = "select min(id) from product",nativeQuery = true)
    public int getMinId();
}
