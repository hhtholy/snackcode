package com.hhtholy.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hht
 * @create 2019-04-26 20:10
 */
@Entity
@Table(name = "pay_")
@Data
public class Pay_ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String out_trade_no;   //商户订单号
    private float total_fee;  //订单金额
    private String trade_no; //支付宝交易号
    private Date pay_date;  //付款时间
}
