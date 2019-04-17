package com.hhtholy.entity;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hht
 * @create 2019-04-15 11:53
 * 属性值
 */
@Entity
@Table(name = "propertyvalue")
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
    private Product product;  //产品

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
