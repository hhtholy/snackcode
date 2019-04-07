package com.hhtholy.service;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Property;
import com.hhtholy.utils.Page;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-06 20:43
 */
public interface PropertyService {
    public Page<Property> getPropertyPage(Category category, Integer currentPage, Integer size, int navigatePages);    //查询 属性值  一个分类下的属性值  分页
    public List<Property> getPropertyList(Category category); //查询 属性值  一个分类下的属性值 不分页
    public void addProperty(Property property);  //添加属性
    public String deleteProperty(Integer id); //删除属性
    public Property getProperty(Integer id);  //根据id 获取属性
    public Property updateProperty(Property property);    // 更新属性
}
