package com.hhtholy.utils.comparator;
import com.hhtholy.entity.Product;
import java.util.Comparator;

/**
 * 根据 产品的创建时间进行比较
 */

public class ProductDateComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		return p1.getCreateDate().compareTo(p2.getCreateDate());
	}

}
