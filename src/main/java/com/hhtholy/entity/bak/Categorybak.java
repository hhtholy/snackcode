package com.hhtholy.entity.bak;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-13 15:39
 */
@Entity
@Table(name="categorybak")
public class Categorybak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;    //主键

    @Column(name = "name")
    private String name;  //分类的名称
    @Column(name = "imageurl")
    private String imageurl;  //分类图片的url

    /*Gettter and Setter*/
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

}
