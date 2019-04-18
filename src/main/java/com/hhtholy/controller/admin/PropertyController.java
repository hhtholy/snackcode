package com.hhtholy.controller.admin;

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
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "删除属性(包括批量)",notes = "根据属性id删除属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="分类id",required=true,paramType="path",allowMultiple=true, dataType = "String"),
    })
    @DeleteMapping("/properties/{id}")
    public String deleteProperty(@PathVariable("id") String[] ids){
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
     * 删除分类的逻辑
     * @param id  属性id
     * @return  删除成功后的标志
     */
    public String deleteLogic(Integer id){
        String deleteResult = propertyService.deleteProperty(id);
        return deleteResult;
    }

    @ApiOperation(value = "添加属性",notes = "添加一个分类下的属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name="property",value="属性实体(RequestBody)",required=true,paramType="body",dataType = "com.hhtholy.entity.Property"),
    })
    @PostMapping("/properties")
    public Property addProperty(@RequestBody Property property){
        propertyService.addProperty(property);
        return property;
    }

    @ApiOperation(value = "查询属性",notes = "根据id去查询属性(属性里面有分类的信息)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="属性id",required=true,paramType="path",dataType = "int"),
    })
    @GetMapping("/properties/{id}")
    public Property getProperty(@PathVariable("id") Integer id){
        return propertyService.getProperty(id);
    }


    //传递的bean实体信息是requestBody 不是form表单的形式  所以需呀加上注解RequestBody
    @ApiOperation(value = "更新属性",notes = "更新一个分类下的属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="属性id",required=true,paramType="path",dataType = "int"),
            @ApiImplicitParam(name="property",value="属性实体",required=true,paramType="body",dataType = "com.hhtholy.entity.Property"),
    })
    @PutMapping("/properties/{id}")
    public Property updateProperty(@PathVariable("id") Integer id,@RequestBody Property property){
        return propertyService.updateProperty(property);
    }

}
