package com.orma.api;

import java.util.List;

import com.orma.domain.Brand;
import com.orma.domain.Company;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;

public interface IDefinitionAPI {
	
	public void createBrand(Brand brand);
	
	public void saveAllBrands(List<Brand> brands);
	
	public List<Brand> getAllBrands();
	
	public void createProduct(Product product);
	
	public void createCompany(Company company);
	
	public void createWarehouse(Warehouse warehouse);
	
	public void createWarehouseRecord(WarehouseRecord warehouseRecord);

}
