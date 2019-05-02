package com.hhtholy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-06 20:33
 * 属性表
 */
@Entity
@Table(name = "property")
@JsonIgnoreProperties(value = {"propertyValues"})
@Data
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
    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="property",fetch = FetchType.LAZY)
    private List<PropertyValue>  propertyValues; //一个属性有多个属性
}
