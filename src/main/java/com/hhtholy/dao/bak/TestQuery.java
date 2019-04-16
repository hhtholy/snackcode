package com.hhtholy.dao.bak;


import com.hhtholy.entity.bak.Propertybak;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-13 16:06
 */
@SpringBootTest
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
public class TestQuery {
    @Autowired private CpDao cpDao;

    @Autowired private PropertyBakDao propertyBakDao;
    @Test
    public void f(){
      //  List<Propertybak> propertybakByCid = propertyBakDao.getPropertybakByCid(1, 0, 2);
          // System.out.println(propertybakByCid);
    }
}
