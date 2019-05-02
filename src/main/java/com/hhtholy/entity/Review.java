package com.hhtholy.entity;

import lombok.Data;

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
@Data
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
}
