package com.hhtholy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-04 15:36
 *
 * 分类表
 */
@Entity
@Table(name="category")
@JsonIgnoreProperties(value = {"products","properties"})
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;    //主键

    @Column(name = "name")
    private String name;  //分类的名称
    @Column(name = "imageurl")
    private String imageurl;  //分类图片的url

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="category",fetch = FetchType.LAZY)
    private List<Product> products;

    @Transient
    private List<Product> productsForJson;

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="category",fetch = FetchType.LAZY)
    private List<Property> properties;

    /*Gettter and Setter*/

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductsForJson() {
        return productsForJson;
    }

    public void setProductsForJson(List<Product> productsForJson) {
        this.productsForJson = productsForJson;
    }
}
