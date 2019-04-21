package com.hhtholy.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public String getImageUrlSingle() {
        return imageUrlSingle;
    }

    public void setImageUrlSingle(String imageUrlSingle) {
        this.imageUrlSingle = imageUrlSingle;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getImageUrlsDetail() {
        return imageUrlsDetail;
    }

    public void setImageUrlsDetail(String imageUrlsDetail) {
        this.imageUrlsDetail = imageUrlsDetail;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getSingleImageUrlForJson() {
        return singleImageUrlForJson;
    }

    public void setSingleImageUrlForJson(String singleImageUrlForJson) {
        this.singleImageUrlForJson = singleImageUrlForJson;
    }
}
