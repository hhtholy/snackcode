package com.hhtholy.dao;

import com.hhtholy.entity.Pay_;
import com.hhtholy.entity.Product;
import com.hhtholy.utils.aliPay.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author hht
 * @create 2019-04-27 12:59
 *    交易记录实体
 */
public interface PayDao extends JpaRepository<Pay_,Integer> {
    @Query(value = "select * from pay_ where out_trade_no=?1",nativeQuery = true)
    public Pay_ getPay_WithOutTradeNo(String tradeno);
}
