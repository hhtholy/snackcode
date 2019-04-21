package com.hhtholy.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hht
 * @create 2019-04-19 10:59
 *
 * 评价实体
 */
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;  //外键 用户

    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;  //外键 产品
    private String content; //评价内容
    private Date createDate;  //创建时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
