package com.hhtholy.controller.admin;

import com.aliyun.oss.OSSClient;
import com.hhtholy.entity.ProductImage;
import com.hhtholy.entity.Review;
import com.hhtholy.entity.User;
import com.hhtholy.service.ProductService;
import com.hhtholy.service.ReviewService;
import com.hhtholy.utils.Constant;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import com.hhtholy.utils.aliyunoss.Ossutil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.hhtholy.config.aliyunConfig.OSSClientConstants.BACKET_NAME;
import static com.hhtholy.config.aliyunConfig.OSSClientConstants.FOLDER;

/**
 * @author hht
 * @create 2019-05-21 19:01
 *
 * 评论相关的前端控制器
 */
@RestController
public class ReviewController {
    @Autowired private ReviewService reviewService;
    @Autowired private ProductService productService;


    /**
     * 获取对应商品的评价分页信息
     * @param pid
     * @param currentPage
     * @param size
     * @return
     * @throws IOException
     */
    @GetMapping("/products/{pid}/reviews")
    public Page<Review> getUsers(@PathVariable("pid") Integer pid,@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                 @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {

        Page<Review> reviewsByProductPage = reviewService.getReviewsByProductPage(productService.getProduct(pid), currentPage, size, 10);
        return reviewsByProductPage;
    }


    /**
     * 删除评价
     * @param ids
     * @return
     */
    @DeleteMapping("/reviews/{id}")
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public String deleteProduct(@PathVariable("id") String[] ids){
        String result = "success";
        try {
            for (String id : ids) {
                Integer intId = Integer.parseInt(id);
                deleteLogic(intId);
            }
        }catch (Exception e){
            result = "failure";
        }
        return result;
    }

    /**
     * 删除评论的逻辑
     * @param id
     * @return
     */
    public String deleteLogic(Integer id){
       Review review = reviewService.getReview(id);
        review.setProduct(null);
        review.setUser(null);
        //执行删除逻辑之前 有 引用置空
        String deleteResult = reviewService.deleteReview(id);
        return deleteResult;
    }
}
