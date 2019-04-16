package com.hhtholy.entity.bak;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-13 15:40
 */
@Entity
@Table(name="propertybak")
public class Propertybak {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;  //属性名称

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
}
