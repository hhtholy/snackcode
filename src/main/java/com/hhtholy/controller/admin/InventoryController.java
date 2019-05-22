package com.hhtholy.controller.admin;

import com.hhtholy.entity.InventoryMessage;
import com.hhtholy.entity.User;
import com.hhtholy.service.InventoryMessageService;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.ReadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author hht
 * @create 2019-05-22 19:19
 *
 *库存预警消息
 */
@RestController
public class InventoryController  {

    @Autowired private InventoryMessageService inventoryMessageService;


    /**
     * 查询库存预警信息
     * @param currentPage
     * @param size
     * @return
     * @throws IOException
     */
    @GetMapping("/inventorys")
    public Page<InventoryMessage> getInventoryMessages(@RequestParam(value = "currentPage", defaultValue="0") Integer currentPage,
                                           @RequestParam(value = "size", defaultValue="4") Integer size) throws IOException {
        return  inventoryMessageService.getInventoryMessagePage(currentPage,Integer.valueOf(ReadProperties.getPropertyValue("pagesize","application.properties")), Integer.valueOf(ReadProperties.getPropertyValue("navigatenums","application.properties")));
    }

    /**
     * 查询前5条数据  预警信息
     * @return
     * @throws IOException
     */
    @GetMapping("/searchInventory")
    public List<InventoryMessage> getInventoryList(){
        return  inventoryMessageService.getInventoryList();
    }
}
