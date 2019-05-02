package com.hhtholy.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hht
 * @create 2019-04-15 11:53
 * 属性值
 */
@Entity
@Table(name = "propertyvalue")
@Data
public class PropertyValue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value; //属性值

    @ManyToOne
    @JoinColumn(name = "ptid")
    private Property property;  //属性

    @ManyToOne
    @JoinColumn(name = "pid")
    @JsonBackReference
    private Product product;  //产品
}
