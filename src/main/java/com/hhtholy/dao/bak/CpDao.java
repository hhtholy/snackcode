package com.hhtholy.dao.bak;

import com.hhtholy.entity.bak.Cp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author hht
 * @create 2019-04-13 15:54
 */
public interface CpDao extends JpaRepository<Cp,Integer> {
    // 一个分类下的属性
    @Query(value = "select * from cp where cid=?1 limit ?2,?3",nativeQuery = true)
    List<Cp> getCpsByCid(Integer id,Integer currentpage,Integer size);





}
