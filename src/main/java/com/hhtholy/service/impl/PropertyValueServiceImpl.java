package com.hhtholy.service.impl;

import com.hhtholy.dao.PropertyValueDao;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.Property;
import com.hhtholy.entity.PropertyValue;
import com.hhtholy.service.PropertyService;
import com.hhtholy.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-15 19:33
 *  属性值接口实现
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired private PropertyValueDao propertyValueDao;
    @Autowired private PropertyService propertyService;

    /**
     *  初始化属性值  也就是把产品引用和属性引用加上 暂时不设置值
     * @param product  产品
     */
    @Override
    public void init(Product product) {
        // 查询该产品下的所属的分类
        // 查询该分类下的属性
        List<Property> propertys = propertyService.getPropertyList(product.getCategory());
        for (Property property:propertys){  //遍历每一个属性  判定有没有值  有的话
            PropertyValue propertyValue = getByPropertyAndProduct(property, product);
            if(propertyValue ==  null){   // 如果没有的话 那么 把产品引用和属性引用加上 （仅仅是这个作用）
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDao.save(propertyValue);
            }
        }
    }

    /**
     * 根据属性和产品（属于哪款产品）
     * @param property 属性
     * @param product 产品
     * @return  PropertyValue 属性值
     */
    @Override
    public PropertyValue getByPropertyAndProduct(Property property, Product product) {
        return propertyValueDao.findByProductAndProperty(product,property);
    }

    /**
     * 根据产品查询属性值
     * @param product 产品
     * @return List<PropertyValue>  属性值
     */
    @Override
    public List<PropertyValue> getPropertyByProduct(Product product) {
        return propertyValueDao.findByProductOrderByIdDesc(product);
    }

    /**
     * 更新属性值
     * @param propertyValue 属性值实体
     * @return
     */

    @Override
    public PropertyValue updatePropertyValue(PropertyValue propertyValue) {
        return propertyValueDao.save(propertyValue);
    }
}
