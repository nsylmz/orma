package com.orma.api;

import java.util.List;

import com.orma.domain.Brand;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.vo.ProductTotalInfo;
import com.orma.vo.ReportType;
import com.orma.vo.WarehouseStockReport;

public interface ISearchAPI {
	
	public ProductTotalInfo getProductTotalInfoByWarehouse(Product product, Warehouse warehouse);
	
	public List<WarehouseStockReport> getReportsByWarehouseOrProductOrBrand(Warehouse warehouse, Product product, Brand brand, ReportType reportType);
	
}
