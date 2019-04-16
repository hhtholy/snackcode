package com.hhtholy.dao;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-14 16:38
 */
public interface ProductImageDao extends JpaRepository<ProductImage,Integer>{
    List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product,String type);
}
