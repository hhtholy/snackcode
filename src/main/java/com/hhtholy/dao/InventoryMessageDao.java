package com.hhtholy.dao;

import com.alipay.api.domain.Inventory;
import com.hhtholy.entity.InventoryMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author hht
 * @create 2019-05-22 18:58
 *
 *  库存相关的 数据层接口
 */
public interface InventoryMessageDao extends JpaRepository<InventoryMessage,Integer> {

    Page<InventoryMessage> findByRead(Pageable pageable, Integer isRead);
}
