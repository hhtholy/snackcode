package com.hhtholy.utils.comparator;




import com.hhtholy.entity.Product;

import java.util.Comparator;

/**
 * 根据评价数进行 排序
 */
public class ProductReviewComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount()-p1.getReviewCount();
	}

}
