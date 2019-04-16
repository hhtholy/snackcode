package com.hhtholy.service;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import com.hhtholy.entity.PropertyValue;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-15 19:30
 * 属性值 相关的业务层接口
 */
public interface PropertyValueService {
    public void  init(Product product); //初始化 属性值
    public PropertyValue getByPropertyAndProduct(Property property, Product product); //获取属性值 根据属性和产品（属于哪款产品）
    public List<PropertyValue> getPropertyByProduct(Product product);    //获取属性值列表
    public PropertyValue updatePropertyValue(PropertyValue propertyValue);  //更新属性值
}
