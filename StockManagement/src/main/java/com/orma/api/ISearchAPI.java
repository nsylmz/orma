package com.orma.api;

import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.vo.ProductTotalInfo;

public interface ISearchAPI {
	
	public ProductTotalInfo getProductTotalInfoByWarehouse(Product product, Warehouse warehouse);
	
}
