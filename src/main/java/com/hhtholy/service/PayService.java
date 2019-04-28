package com.hhtholy.service;

import com.hhtholy.entity.Category;
import com.hhtholy.entity.Pay_;
import com.hhtholy.entity.Product;
import com.hhtholy.utils.Page;
import com.hhtholy.utils.aliPay.Pay;

/**
 * @author hht
 * @create 2019-04-27 13:01
 * 交易记录相关的业务接口
 */
public interface PayService {
    public Page<Pay_> getPayPage(Integer currentPage, Integer size, Integer navigateNum);//分页查询 分页信息
    public Pay_ addPay(Pay_ pay);    //添加交易记录
    public String deletePay(Integer id);   //删除交易记录
    public Pay_ getPay(Integer id); //根据id 获取交易记录

    public Pay_ getPayByOutTradeNo(String out_trade_no);
}
