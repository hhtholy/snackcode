package com.hhtholy.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hht
 * @create 2019-04-12 11:55
 * 产品
 */
@Entity
@Table(name="product")
public class Product {
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
    private String imageUrlSingle;  //单图

    public String getImageUrlSingle() {
        return imageUrlSingle;
    }

    public void setImageUrlSingle(String imageUrlSingle) {
        this.imageUrlSingle = imageUrlSingle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
