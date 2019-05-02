package com.hhtholy.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-04-19 16:12
 * 订单项
 */
@Entity
@Table(name = "orderitem")
@Data
public class OrderItem {
    /**
     * 一个产品在多个订单项里
     *
     * 一个订单 拥有多个订单项
     *
     * 一个用户有多个订单项
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "oid")
    private Order_ order;
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    private Integer number; //数量 (购买对应产品的数量)

}
