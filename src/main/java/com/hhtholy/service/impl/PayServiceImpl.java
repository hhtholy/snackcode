package com.hhtholy.service.impl;

import com.hhtholy.dao.PayDao;
import com.hhtholy.entity.Pay_;
import com.hhtholy.service.PayService;
import com.hhtholy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author hht
 * @create 2019-04-27 13:04
 *
 * 交易记录相关的业务实现类
 */
@Service
public class PayServiceImpl implements PayService {
    @Autowired private PayDao payDao;


    /**
     * 获取交易记录
     * @param currentPage
     * @param size
     * @param navigateNum
     * @return
     */
    @Override
    public Page<Pay_> getPayPage(Integer currentPage, Integer size, Integer navigateNum) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(currentPage, size, sort);
        org.springframework.data.domain.Page<Pay_> page = payDao.findAll(pageable);
        return  new Page<>(page,navigateNum);
    }

    /**
     * 添加交易记录
     * @param pay
     * @return
     */
    @Override
    public Pay_ addPay(Pay_ pay) {
        return payDao.save(pay);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public String deletePay(Integer id) {
        try {
            payDao.deleteById(id);
        }catch (Exception e){
            return "failure";
        }
        return "success";
    }

    /**
     * 获取交易记录 根据id
     * @param id
     * @return
     */

    @Override
    public Pay_ getPay(Integer id) {
        Pay_ pay = new Pay_(); //SpringBoot2.0+的版本 findOne方法有变化
        pay.setId(id);
        Example<Pay_> example = Example.of(pay);
        Optional<Pay_> payResult = payDao.findOne(example);//设置查询条件
        Pay_ result = null;
        if(payResult.isPresent()){  //结果存在的情况下
            result = payResult.get();
        }
        return result;
    }
}
