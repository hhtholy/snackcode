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
@Data
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


    @Transient
    List<List<Product>> productsByRow; //主页上

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="category",fetch = FetchType.LAZY)
    private List<Property> properties;

}
