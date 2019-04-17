package com.hhtholy.service;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.ProductImage;
import com.hhtholy.entity.Property;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-14 16:42
 * 产品图片相关的业务层接口
 */
public interface ProductImageService {
    public ProductImage addProductImage(ProductImage productImage);   //添加图片
    public String deleteProductImage(Integer id);     //删除图片
    public ProductImage getProductImage(Integer id);  //获取图片
    public ProductImage updateProductImage(ProductImage productImage);    // 更新图片
    List<ProductImage> getProductImage(Product product, String type); //获取一个产品下的图片 包括单图和详情图
    List<ProductImage> getProductImage(Product product); //获取一个产品下的图片
}
