package com.hhtholy.dao.bak;


import com.hhtholy.entity.bak.Propertybak;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * @author hht
 * @create 2019-04-13 15:54
 */
public interface PropertyBakDao extends JpaRepository<Propertybak,Integer> {
    @Query(value = "select p.* from cp c,propertybak p where p.id=c.pid and c.cid = ?1",nativeQuery = true)
    List<Propertybak> getPropertybakByCid(Integer cid);
}
