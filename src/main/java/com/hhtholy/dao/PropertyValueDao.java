package com.hhtholy.dao;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import com.hhtholy.entity.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-15 19:35
 * 属性值相关的数据层接口
 */
public interface PropertyValueDao extends JpaRepository<PropertyValue,Integer> {
    public PropertyValue findByProductAndProperty(Product product, Property property);//根据产品和属性去查询属性值
    public List<PropertyValue> findByProductOrderByIdDesc(Product product); // 根据产品去查询属性值
}
