package com.hhtholy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hht
 * @create 2019-04-16 15:19
 * 订单表
 */
@Entity
@Table(name = "order_")
@JsonIgnoreProperties(value = {"orderItems"})
@Data
public class Order_ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;  //一个用户拥有多个订单

    private String orderCode;  //订单号
    private String address; // 地址
    private String post; //邮编
    private String receiver; //收货人地址
    private String mobile; //电话
    private String userMessage; //用户的备注信息
    private Date createDate; //创建日期
    private Date payDate; //支付日期
    private Date deliveryDate; //发货日期
    private Date confirmDate; //确认时间
    private String status; //订单的状态
    private Float totalPrice; //订单的总金额
    private Integer totalNum; //订单中 产品的总计数量

    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="order",fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Transient
    private List<OrderItem> orderItemsForJson;

}
