package com.orma.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orma.api.IDefinitionAPI;
import com.orma.dao.BrandDaoI;
import com.orma.dao.CompanyDaoI;
import com.orma.dao.ProductDaoI;
import com.orma.dao.WarehouseDaoI;
import com.orma.dao.WarehouseRecordDaoI;
import com.orma.domain.Brand;
import com.orma.domain.Company;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;

@Transactional(propagation = Propagation.REQUIRED)
@Component
public class DefinitionAPI implements IDefinitionAPI {

	@Autowired
	private BrandDaoI brandDao;

	@Autowired
	private ProductDaoI productDao;

	@Autowired
	private CompanyDaoI companyDao;

	@Autowired
	private WarehouseDaoI warehouseDao;

	@Autowired
	private WarehouseRecordDaoI warehouseRecordDao;

	@Override
	public void saveBrand(Brand brand) {
		brandDao.merge(brand);
	}
	
	@Override
	public void deleteBrand(Brand brand) {
		brandDao.delete(brandDao.findById(brand.getId()));
	}
	
	@Override
	public List<Brand> getAllBrands() {
		return brandDao.findAll();
	}

	@Override
	public void saveProduct(Product product) {
		productDao.merge(product);
	}

	@Override
	public void saveCompany(Company company) {
		companyDao.merge(company);
	}
	
	@Override
	public void deleteCompany(Company company) {
		companyDao.delete(companyDao.findById(company.getId()));
	}
	
	@Override
	public List<Company> getAllCompanies() {
		return companyDao.findAll();
	}

	@Override
	public void saveWarehouse(Warehouse warehouse) {
		warehouseDao.merge(warehouse);
	}
	
	@Override
	public void deleteWarehouse(Warehouse warehouse) {
		warehouseDao.delete(warehouseDao.findById(warehouse.getId()));
	}
	
	@Override
	public List<Warehouse> getAllWarehouses() {
		return warehouseDao.findAll();
	}

	@Override
	public void saveWarehouseRecord(WarehouseRecord warehouseRecord) {
		warehouseRecordDao.merge(warehouseRecord);
	}

	public void setBrandDao(BrandDaoI brandDao) {
		this.brandDao = brandDao;
	}

	public void setProductDao(ProductDaoI productDao) {
		this.productDao = productDao;
	}

	public void setCompanyDao(CompanyDaoI companyDao) {
		this.companyDao = companyDao;
	}

	public void setWarehouseDao(WarehouseDaoI warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public void setWarehouseRecordDao(WarehouseRecordDaoI warehouseRecordDao) {
		this.warehouseRecordDao = warehouseRecordDao;
	}

}
