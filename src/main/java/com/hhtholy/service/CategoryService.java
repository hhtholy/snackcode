package com.hhtholy.service;

import com.hhtholy.entity.Category;
import com.hhtholy.utils.Page;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-04 16:56
 * 和分类相关操作的接口
 */
public interface CategoryService {
    public Page getCategoryPage(Integer currentPage, Integer size, int navigatePages);    //分类分页
    public List<Category> getCategoryList();    //分类全部 不分页
    public void addCategory(Category category);    //添加分类
    public String deleteCategory(Integer id);  //删除分类
    public Category updateCategory(Category category);     //更新分类
    public Category getCategory(Integer id);    //根据id去查询一个分类实体
    public void setProductsForJsonOfCategory(Category category); //为分类 加上产品值（页面需要json转换）

    public List<Category> getCategoryByName(String name);
}
