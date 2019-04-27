package com.hhtholy.dao;

import com.hhtholy.entity.Pay_;
import com.hhtholy.entity.Product;
import com.hhtholy.utils.aliPay.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hht
 * @create 2019-04-27 12:59
 *    交易记录实体
 */
public interface PayDao extends JpaRepository<Pay_,Integer> {

}
