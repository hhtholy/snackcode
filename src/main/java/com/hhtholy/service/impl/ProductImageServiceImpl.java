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

    @Override
    public List<ProductImage> getProductImage(Product product, String type) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product,type);
    }
}
