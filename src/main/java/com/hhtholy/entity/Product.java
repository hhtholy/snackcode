package com.hhtholy.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * @author hht
 * @create 2019-04-12 11:55
 * 产品
 */
@Entity
@Table(name="product")
@JsonIgnoreProperties(value = {"propertyValues"})
@Data
@ToString(exclude = {"productImages","propertyValues"})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;  //一个分类下有多个产品
    private String name; // 产品名称
    private String subTitle; //小标题
    private Float originalPrice; //原始价格
    private Float promotePrice;  //优惠价格
    private Integer stock; //库存
    private Date createDate; //创建时间
    private String imageUrlSingle;  //单图   存储到数据库

    private Integer saleCount;  //销量
    private Integer reviewCount;  //评价数量
    private String imageUrlsDetail; //多图  存储到数据库

    @Transient
    private String singleImageUrlForJson; //后台展产品的时候 需要的图片url

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="product",fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="product",fetch = FetchType.LAZY)
    private List<PropertyValue> propertyValues;


    private Integer isputAway;  //1  上架 2 下架  下架了主页不展示出来
    private Integer isDelete;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, String subTitle) {
        this.name = name;
        this.subTitle = subTitle;
    }
}
