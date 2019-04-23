package com.hhtholy.service.impl;

import com.hhtholy.dao.CategoryDao;
import com.hhtholy.entity.Category;
import com.hhtholy.entity.Product;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.ProductService;
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
 * @create 2019-04-04 17:03
 *
 * Category操作相关的业务实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
      @Autowired
      private CategoryDao categoryDao;  //注入CategoryDao
      @Autowired private ProductService productService;

    /**
     * 查询分类数据  同时分页
     * @param currentPage  当前页码
     * @param size   每页展示的条数
     * @param navigatePages   分页条导航长度
     * @return Page<Category> 自定义的分页对象
     */
    @Override
    public Page<Category> getCategoryPage(Integer currentPage, Integer size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(currentPage, size, sort);
        org.springframework.data.domain.Page<Category> page = categoryDao.findAll(pageable);
        return  new Page<>(page,navigatePages);
    }

    /**
     * 添加分类
     * @param category 分类（实体）
     */
    @Override
    public void addCategory(Category category) {
         categoryDao.save(category);
    }

    /**
     * 删除分类
     * @param id  分类的id
     * @return  删除成功 或者失败的标志
     */
    @Override
    public String deleteCategory(Integer id) {
        try {
            categoryDao.deleteById(id);
        }catch (Exception e){
            return "failure";
        }
        return "success";
    }

    /**
     * 更新分类
     * @param category 分类（实体）
     * @return  更新完成后的分类
     */
    @Override
    public Category updateCategory(Category category) {
        Category result = null;
        try {
            result = categoryDao.save(category);
        }catch (Exception e){
            result = null;
        }
        return  result;
    }

    /**
     * 根据id去获取分类
     * @param id  分类的id
     * @return  对应id对应的分类
     */
    @Override
    public Category getCategory(Integer id) {
        Category category = new Category(); //SpringBoot2.0+的版本 findOne方法有变化
        category.setId(id);
        Example<Category> example = Example.of(category);
        Optional<Category> categoryOptional = categoryDao.findOne(example);//设置查询条件
        Category result = null;
        if(categoryOptional.isPresent()){  //结果存在的情况下
            result = categoryOptional.get();
        }
        return result;
    }

    /**
     * 为分类加 产品值（界面转换json）
     * @param category
     */
    @Override
    public void setProductsForJsonOfCategory(Category category) {
        List<Product> productList = productService.getProductList(category);
        for (Product p:productList){
            p.setCategory(null);       //消除引用  但是不会影响其他业务使用
        }
        productService.setSingleImageUrlFoJson(productList); // 设置单图（不持久化数据库中）
        category.setProductsForJson(productList);
    }


}
