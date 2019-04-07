package com.hhtholy.controller;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Property;
import com.hhtholy.service.CategoryService;
import com.hhtholy.service.PropertyService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author hht
 * @create 2019-04-07 16:56
 *
 * 属性相关的前端控制器
 */
@Api(tags = "属性模块",description = "针对属性相关的Api")
@RestController
public class PropertyController {
    @Autowired  private PropertyService propertyService;
    @Autowired  private CategoryService categoryService;

    /**
     * 分页查询一个分类下的属性
     * @param currentPage 当前页码
     * @param size 每页显示的条数
     * @return Page<Property>
     */
    @ApiOperation(value = "获取属性列表",notes = "获取一个分类下所有的属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cid",value="分类id",required=true,paramType="path",dataType = "int"),
            @ApiImplicitParam(name="currentPage",value="当前页码(0开始）",required=true,paramType="query",dataType = "int"),
            @ApiImplicitParam(name="size",value="每页显示的条数",required=true,paramType="query",dataType = "int")
    })
    @GetMapping("/categories/{cid}/properties")
    public Page<Property> getProperties(@PathVariable("cid") Integer cid, @RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                        @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        Category category = categoryService.getCategory(cid);//根据cid 分类id 查询出分类
        return  propertyService.getPropertyPage(category, currentPage, Integer.valueOf(ReadProperties.getPropertyValue("pagesize", "application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums", "application.properties")));
    }



}
