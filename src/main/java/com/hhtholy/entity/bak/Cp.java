package com.hhtholy.entity.bak;

import lombok.Data;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-13 15:50
 */
@Entity
@Table(name="cp")
@Data
public class Cp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;    //主键

    @ManyToOne
    @JoinColumn(name="cid")
    private Categorybak categorybak;

    @ManyToOne
    @JoinColumn(name="pid")
    private Propertybak propertybak;

}
