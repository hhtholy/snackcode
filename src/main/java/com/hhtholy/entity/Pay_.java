package com.hhtholy.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hht
 * @create 2019-04-26 20:10
 */
@Entity
@Table(name = "pay_")
public class Pay_ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String out_trade_no;   //商户订单号
    private float total_fee;  //订单金额
    private String trade_no; //支付宝交易号
    private Date pay_date;  //付款时间

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public float getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(float total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public Date getPay_date() {
        return pay_date;
    }

    public void setPay_date(Date pay_date) {
        this.pay_date = pay_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
