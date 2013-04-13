package com.orma.api;

import java.util.List;

import com.orma.domain.Brand;
import com.orma.domain.Company;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;

public interface IDefinitionAPI {
	
	public void saveBrand(Brand brand);
	
	public void deleteBrand(Brand brand);
	
	public List<Brand> getAllBrands();
	
	public void saveProduct(Product product);
	
	public void deleteProduct(Product product);
	
	public List<Product> getAllProducts();
	
	public void saveCompany(Company company);
	
	public void deleteCompany(Company company);
	
	public List<Company> getAllCompanies();
	
	public void saveWarehouse(Warehouse warehouse);
	
	public void deleteWarehouse(Warehouse warehouse);
	
	public List<Warehouse> getAllWarehouses();
	
	public void saveWarehouseRecord(WarehouseRecord warehouseRecord);
	
	public void deleteWarehouseRecord(WarehouseRecord warehouseRecord);
	
	public List<WarehouseRecord> getAllWarehouseRecords();

}
