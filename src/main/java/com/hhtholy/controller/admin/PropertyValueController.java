package com.hhtholy.controller.admin;

import com.hhtholy.entity.Product;
import com.hhtholy.entity.PropertyValue;
import com.hhtholy.service.ProductService;
import com.hhtholy.service.PropertyValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-15 19:16
 * 属性值前端控制器
 */
@Api(tags = "属性值模块",description = "针对属性值相关的Api")
@RestController
public class PropertyValueController {

    @Autowired private PropertyValueService propertyValueService;
    @Autowired private ProductService productService;


    /**
     * 展示属性和对应的属性值
     * init方法是针对 起始的时候属性值实体中 都没有属性引用和产品引用
     * @param pid
     * @return
     */
    @ApiOperation(value = "获取属性值",notes = "根据产品id获取对应的属性值")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pid",value="产品id",required=true,paramType="path",dataType = "int"),
    })
    @GetMapping("/products/{pid}/propertyvalues")
    public List<PropertyValue> getPropertyValues(@PathVariable("pid") Integer pid) {
        Product product = productService.getProduct(pid); //根据产品id查询 产品
         propertyValueService.init(product); //初始化产品的属性对应的属性值 （如果没有的话）
        List<PropertyValue> propertyList = propertyValueService.getPropertyByProduct(product);
        return propertyList;
    }

    @ApiOperation(value = "更新属性值",notes ="更新一个产品下属性的属性值")
    @ApiImplicitParams({
            @ApiImplicitParam(name="propertyValue",value="属性值",required=true,paramType="body",allowMultiple = true,dataType = "com.hhtholy.entity.PropertyValue"),
    })
    @PutMapping("/propertyvalues")
    public PropertyValue updatePropertyValue(@RequestBody PropertyValue propertyValue){
        propertyValueService.updatePropertyValue(propertyValue);
        return propertyValue;
    }
}
