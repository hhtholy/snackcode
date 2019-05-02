package com.hhtholy.entity.bak;

import lombok.Data;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-13 15:40
 */
@Entity
@Table(name="propertybak")
@Data
public class Propertybak {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;  //属性名称
}
