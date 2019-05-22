package com.hhtholy.service.impl;

import com.hhtholy.dao.InventoryMessageDao;
import com.hhtholy.entity.InventoryMessage;
import com.hhtholy.entity.User;
import com.hhtholy.service.InventoryMessageService;
import com.hhtholy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sun.nio.cs.ext.IBM037;

import java.util.List;

/**
 * @author hht
 * @create 2019-05-22 17:14
 */
@Service
public class InventoryMessageServiceImpl implements InventoryMessageService {

    @Autowired private InventoryMessageDao inventoryMessageDao;


    @Override
    public void addMessage(InventoryMessage message) {
        inventoryMessageDao.save(message);
    }

    /**
     * 查询 预警消息
     * @param currentPage
     * @param pagesize
     * @param navigatenums
     * @return
     */
    @Override
    public Page<InventoryMessage> getInventoryMessagePage(Integer currentPage, Integer pagesize, Integer navigatenums) {
        Pageable pageable = new PageRequest(currentPage,pagesize, Sort.Direction.DESC,"id");
        org.springframework.data.domain.Page<InventoryMessage> results = inventoryMessageDao.findAll(pageable);
        return new Page<>(results,navigatenums);

    }

    /**
     * 查询预警信息  查询前五条       预警 信息
     * @return
     */
    @Override
    public List<InventoryMessage> getInventoryList() {
        Pageable pageable = new PageRequest(0,5, Sort.Direction.DESC,"id");
        //查询 预警 前5条 未读信息
        org.springframework.data.domain.Page<InventoryMessage> page = inventoryMessageDao.findByRead(pageable,2);
        List<InventoryMessage> content = page.getContent();
        return content;
    }
}
