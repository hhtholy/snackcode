package com.hhtholy.dao;

import com.hhtholy.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hht
 * @create 2019-04-04 17:06
 *
 * 分类相关的数据交互层
 */
public interface CategoryDao extends JpaRepository<Category,Integer> {
}
