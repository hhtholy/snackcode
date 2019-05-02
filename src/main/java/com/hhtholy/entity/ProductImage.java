package com.hhtholy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hht
 * @create 2019-04-14 16:35
 * 产品图片表
 */
@Entity
@Table(name="productimage")
@JsonIgnoreProperties(value = {"product"})
@Data
public class ProductImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    private String type; //图片的类型  单个 或者多个详情

    @Column(name = "imageurl")
    private String imageurl;  //分类图片的url
}
