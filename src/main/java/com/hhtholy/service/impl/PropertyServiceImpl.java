package com.hhtholy.service.impl;

import com.hhtholy.dao.PropertyDao;
import com.hhtholy.entity.Category;
import com.hhtholy.entity.Property;
import com.hhtholy.service.PropertyService;
import com.hhtholy.utils.Page;
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
 * @create 2019-04-06 20:43
 * Property操作相关的业务实现类
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired private PropertyDao propertyDao; //注入 propertydao

    /**
     * 根据分类查询全部属性数据  同时分页
     * @param category 分类
     * @param currentPage  当前页码
     * @param size   每页展示的条数
     * @param navigatePages   分页条导航长度
     * @return Page<Property> 自定义的分页对象
     */
    @Override
    public Page<Property> getPropertyPage(Category category, Integer currentPage, Integer size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");  //创建分页对象
        Pageable pageable = new PageRequest(currentPage, size, sort);
        org.springframework.data.domain.Page<Property> page= propertyDao.findByCategory(category, pageable);
        return new Page<>(page,navigatePages);
    }

    /**
     *  根据分类查询全部属性数据  不分页
     * @param category 分类
     * @return   List<Property 一个分类下的全部属性数据
     */
    @Override
    public List<Property> getPropertyList(Category category) {
        return propertyDao.findByCategory(category);
    }

    /**
     * 添加属性
     * @param property 属性（实体）
     */
    @Override
    public void addProperty(Property property) {
          propertyDao.save(property);
    }

    /**
     * 根据id去删除属性
     * @param id 属性id
     * @return  删除成功或者失败的标志
     */
    @Override
    public String deleteProperty(Integer id) {
        String result = null;
        try {
            propertyDao.deleteById(id);
            result = "success";
        } catch (Exception e){
            result = "failure";
        }
        return result;
    }

    /**
     * 根据id获取属性
     * @param id 属性的id
     * @return  对应id的属性
     */
    @Override
    public Property getProperty(Integer id) {
        Property property = new Property();
        property.setId(id);
        Example<Property> example = Example.of(property);
        Optional<Property> result = propertyDao.findOne(example);
        Property resultGet = null;
        if(result.isPresent()){
            resultGet = result.get();
        }
        return resultGet;
    }

    /**
     * 更新属性
     * @param property 属性（实体）
     * @return  更新后的属性
     */
    @Override
    public Property updateProperty(Property property) {
        Property result = null;
        try {
            result = propertyDao.save(property);
        }catch (Exception e){
            result = null;
        }
        return result;
    }
}
