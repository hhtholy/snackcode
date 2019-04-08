package com.hhtholy.controller;

import com.hhtholy.entity.Category;
import com.hhtholy.service.CategoryService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import com.sun.imageio.plugins.common.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author hht
 * @create 2019-04-04 16:55
 *
 * 和分类相关的前端控制器
 */
@Api(tags = "分类模块",description = "针对分类相关的Api")
@RestController
public class CategoryController {
    @Autowired  private CategoryService categoryService;  //注入CategoryService

    @ApiOperation(value = "获取分类列表",notes = "获取所有的分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("/categories")
    public Page<Category> getCategoryPage(@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                          @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        return (Page<Category>) categoryService.getCategoryPage(currentPage,Integer.valueOf(ReadProperties.getPropertyValue("pagesize","application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums","application.properties")));
    }

    /**
     * 添加分类  这里有上传文件的操作
     * @param category 分类（实体）
     * @param file  文件对象
     * @param request  HttpServletRequest请求对象
     * @return  添加后的分类对象（为了符合REST风格 返回json数据）
     * @throws IOException
     */
    @PostMapping("/categories")
    public Category addCategory(Category category, @RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        categoryService.addCategory(category);       //经过这一部分操作后  id就有值了
      //  uploadFile(category,file,request);     //文件上传操作
        return category;
    }

    /**
     *
     * @param category
     * @param file
     * @param request
     * @throws IOException
     */
   /* public  void uploadFile(Category category, MultipartFile file, HttpServletRequest request) throws IOException {
        // getServletContext().getRealPath("/");获得绝对路径。
        String realPath = request.getServletContext().getRealPath("img/category");
        File imageFolder = new File(realPath);
        File fileImage = new File(imageFolder, category.getId() + ".jpg");
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        file.transferTo(fileImage); // 文件复制到服务器上
        //转换
        BufferedImage bufferedImage = ImageUtil.change2jpg(fileImage);
        // 转换为jpg格式图片
        ImageIO.write(bufferedImage,"jpg",fileImage);
    }*/

}
