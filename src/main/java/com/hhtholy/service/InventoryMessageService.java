package com.hhtholy.service;

import com.hhtholy.entity.InventoryMessage;
import com.hhtholy.utils.Page;

import java.util.List;

/**
 * @author hht
 * @create 2019-05-22 17:13
 * 
 * 库存通知消息  
 */
public interface InventoryMessageService {
    
    public void addMessage(InventoryMessage message); //保存库存通知消息

    Page<InventoryMessage> getInventoryMessagePage(Integer currentPage, Integer pagesize, Integer navigatenums);

    List<InventoryMessage> getInventoryList();
}
