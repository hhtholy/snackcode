package com.hhtholy.entity.bak;

import lombok.Data;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-13 15:39
 */
@Entity
@Table(name="categorybak")
@Data
public class Categorybak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;    //主键

    @Column(name = "name")
    private String name;  //分类的名称
    @Column(name = "imageurl")
    private String imageurl;  //分类图片的url
}
