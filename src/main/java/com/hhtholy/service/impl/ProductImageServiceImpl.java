package com.hhtholy.service.impl;

import com.hhtholy.dao.ProductImageDao;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.ProductImage;
import com.hhtholy.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hht
 * @create 2019-04-14 16:42
 * 产品图片相关的数据库交互层
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired private ProductImageDao productImageDao;

    /**
     *  添加产品图片
     * @param productImage
     * @return
     */
    @Override
    public ProductImage addProductImage(ProductImage productImage) {
        return productImageDao.save(productImage);
    }

    /**
     *  删除产品图片
     * @param id 产品图片id
     * @return
     */
    @Override
    public String deleteProductImage(Integer id) {
        String result = "success";
       try {
           productImageDao.deleteById(id);
       }catch (Exception e){
           result = "failure";
       }
        return result;
    }

    /**
     *  根据产品图片id获取产品图片
     * @param id 产品图片id
     * @return
     */
    @Override
    public ProductImage getProductImage(Integer id) {
        ProductImage productImage = new ProductImage();
        productImage.setId(id);
        Example<ProductImage> example = Example.of(productImage);
        Optional<ProductImage> result = productImageDao.findOne(example);
        ProductImage resultGet = null;
        if(result.isPresent()){
            resultGet = result.get();
        }
        return resultGet;
    }

    /**
     * 更新产品图片
     * @param productImage 产品图片实体
     * @return
     */
    @Override
    public ProductImage updateProductImage(ProductImage productImage) {
        ProductImage result = null;
        try {
            result = productImageDao.save(productImage);
        }catch (Exception e){
            result = null;
        }
        return result;
    }

    /**
     *  根据产品和类型去查询对应的产品图片
     * @param product  产品
     * @param type   类型
     * @return  返回产品图片列表
     */
    @Override
    public List<ProductImage> getProductImage(Product product, String type) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product,type);
    }

    /**
     * 根据产品去查询对应的产品图片
     * @param product 产品
     * @return  对应的产品列表
     */
    @Override
    public List<ProductImage> getProductImage(Product product) {
        return productImageDao.findByProductOrderByIdDesc(product);
    }
}
