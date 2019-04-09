package com.hhtholy.controller;

import com.aliyun.oss.OSSClient;
import com.hhtholy.entity.Category;
import com.hhtholy.service.CategoryService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
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

import static com.hhtholy.config.aliyunConfig.OSSClientConstants.BACKET_NAME;
import static com.hhtholy.config.aliyunConfig.OSSClientConstants.FOLDER;

/**
 * @author hht
 * @create 2019-04-04 16:55
 * 和分类相关的前端控制器
 */
@Api(tags = "分类模块",description = "针对分类相关的Api")
@RestController
public class CategoryController {
    @Autowired  private CategoryService categoryService;  //注入CategoryService


    @ApiOperation(value = "获取分类列表(分页)",notes = "获取所有的分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("/categories")
    public Page<Category> getCategoryPage(@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                          @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        return (Page<Category>) categoryService.getCategoryPage(currentPage,Integer.valueOf(ReadProperties.getPropertyValue("pagesize","application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums","application.properties")));
    }

    @ApiOperation(value = "添加分类",notes = "添加分类以及分类图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="category",value="传递的bean对象",required=true,paramType="form",dataType = "com.hhtholy.entity.Category"),
            @ApiImplicitParam(name="file",value="上传的文件",required=true,paramType="form",dataType = "org.springframework.web.multipart.MultipartFile"),
            @ApiImplicitParam(name="request",value="request对象",required=false)
    })
    @PostMapping("/categories")
    public Category addCategory(Category category, @RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        categoryService.addCategory(category);       //经过这一部分操作后  id就有值了
        uploadFile(category,file,request);     //文件上传操作
        return category;
    }

    /**
     *  文件上传到阿里云oss
     * @param category 分类（实体）
     * @param file  文件对象
     * @param request
     * @throws IOException
     */
   public void uploadFile(Category category, MultipartFile file, HttpServletRequest request) throws IOException {
       File f = null;
       try{
           f = File.createTempFile("prefix",category.getId() + ".jpg");
           file.transferTo(f);
           category.setImageurl(f.getName());
           categoryService.updateCategory(category); //更新分类
           OSSClient ossClient= Ossutil.getOSSClient();
           Ossutil.uploadObject2OSS(ossClient, f,BACKET_NAME, FOLDER+"category/");
       }catch (Exception e) {
       }
    }
    @ApiOperation(value = "删除分类",notes = "根据分类id删除分类同时删除oss上对应图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="分类id",required=true,paramType="path",dataType = "int"),
    })
    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable("id") Integer id){
        Category category = categoryService.getCategory(id);//删除前先获取key
        String imageurl = category.getImageurl();
        OSSClient ossClient= Ossutil.getOSSClient();
        Ossutil.deleteFile(ossClient,BACKET_NAME,FOLDER+"category/",imageurl);
        String deleteResult = categoryService.deleteCategory(id);
        return deleteResult;
    }





}
