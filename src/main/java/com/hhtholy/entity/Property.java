package com.hhtholy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hht
 * @create 2019-04-06 20:33
 * 属性表
 */
@Entity
@Table(name = "property")
@JsonIgnoreProperties(value = {"category"})
public class Property implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;  //属性名称
    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;  //外键  一个分类下拥有多个属性
    
    public Integer getId() {
        return id;
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
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

}
