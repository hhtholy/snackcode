package com.hhtholy.controller.admin;

import com.aliyun.oss.OSSClient;
import com.hhtholy.entity.Product;
import com.hhtholy.entity.ProductImage;
import com.hhtholy.service.ProductImageService;
import com.hhtholy.service.ProductService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.aliyunoss.Ossutil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hhtholy.config.aliyunConfig.OSSClientConstants.BACKET_NAME;
import static com.hhtholy.config.aliyunConfig.OSSClientConstants.FOLDER;

/**
 * @author hht
 * @create 2019-04-14 16:39
 * 产品图片相关的前端控制器
 */
@Api(tags = "产品图片模块",description = "针对产品图片相关的Api")
@RestController
public class ProductImageController {
    @Autowired private ProductImageService productImageService;
    @Autowired private ProductService productService;


    @ApiOperation(value = "获取产品图片(单图和详情图)",notes = "根据产品id获取对应的产品图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="产品id",required=true,paramType="path",dataType = "int"),
            @ApiImplicitParam(name="productImage",value="传递图片类型",required=true,paramType="query",dataType = "int"),
    })
    @GetMapping("/products/{pid}/productimages")
    public List<ProductImage> getProductImage(ProductImage productImage, @PathVariable("pid") Integer pid){
        Product product = productService.getProduct(pid);   //获取到产品  因为需要根据 产品去查询 该产品下面的图
        if(product != null && productImage.getType() != null){
            return productImageService.getProductImage(product,productImage.getType());
        }
        return new ArrayList<>();
    }

    @ApiOperation(value = "添加产品图片(单图和详情图)",notes = "添加一个产品的单图和详情图")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="productImage",value="产品图片实体",required=true,paramType="form",dataType = "com.hhtholy.entity.ProductImage"),
            @ApiImplicitParam(name="file",value="上传的文件",required=true,paramType="form",dataType = "org.springframework.web.multipart.MultipartFile"),
    })
    @PostMapping("/productimages")
    public ProductImage addProductImage(@RequestParam("pid") Integer pid, ProductImage productImage, MultipartFile file, HttpServletRequest request) throws IOException {
        Product product = productService.getProduct(pid);     //根据 id 去查询出产品
        productImage.setProduct(product);
        productImageService.addProductImage(productImage);
        uploadFile(productImage,file,productImage.getType()); //上传文件
        return productImage;
    }

    /**
     *  文件上传到阿里云oss
     * @param productImage  产品图片（实体）
     * @param file  文件对象
     * @throws IOException
     */
    public void uploadFile(ProductImage productImage, MultipartFile file,String type) throws IOException {
        File f = null;
        try {
            f = File.createTempFile("prefix", productImage.getId() + ".jpg");
            file.transferTo(f);
            productImage.setImageurl(f.getName());
            productImageService.updateProductImage(productImage);
            if(type.equals(Constant.SINGLEIMAGE.getWord())){  //上传图片
                OSSClient ossClient = Ossutil.getOSSClient();
                Ossutil.uploadObject2OSS(ossClient, f, BACKET_NAME, FOLDER + "products/single/");
            }
            if(type.equals(Constant.DETAILIMAGE.getWord())){
                OSSClient ossClient = Ossutil.getOSSClient();
                Ossutil.uploadObject2OSS(ossClient, f, BACKET_NAME, FOLDER + "products/detail/");
            }
          } catch (Exception e) {

        }
    }
    @ApiOperation(value = "删除产品图片(单图和详情图)",notes = "删除一个产品的单图和详情图")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="产品id",required=true,paramType="path",dataType = "int"),
    })
    @DeleteMapping("/productimages/{id}")
    public String deleteProductImage(@PathVariable("id") Integer id,HttpServletRequest request){
        ProductImage productImage = productImageService.getProductImage(id); //先获取下 图片的 type 以便于判断删除
        String imageurl = productImage.getImageurl();
        String type = productImage.getType();
        if(type.equals(Constant.SINGLEIMAGE.getWord())){  //上传图片
            OSSClient ossClient= Ossutil.getOSSClient();
            Ossutil.deleteFile(ossClient,BACKET_NAME,FOLDER+"products/single/",imageurl);
        }
        if(type.equals(Constant.DETAILIMAGE.getWord())){
            OSSClient ossClient= Ossutil.getOSSClient();
            Ossutil.deleteFile(ossClient,BACKET_NAME,FOLDER+"products/detail/",imageurl);
        }
        //删除
        String result = productImageService.deleteProductImage(id);
        return result;
    }
}
