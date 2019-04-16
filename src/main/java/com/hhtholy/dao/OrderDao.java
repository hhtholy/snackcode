package com.hhtholy.dao;

import com.hhtholy.entity.Order;
import com.hhtholy.utils.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hht
 * @create 2019-04-16 15:25
 */
public interface OrderDao extends JpaRepository<Order,Integer> {

}
