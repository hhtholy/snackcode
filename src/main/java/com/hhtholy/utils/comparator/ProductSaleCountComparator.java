package com.hhtholy.utils.comparator;
import com.hhtholy.entity.Product;
import java.util.Comparator;

/**
 * 根据销量 进行比较
 */
public class ProductSaleCountComparator implements Comparator<Product> {
	@Override
	public int compare(Product p1, Product p2) {
		return p2.getSaleCount()-p1.getSaleCount();
	}

}
