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
	public void createBrand(Brand brand) {
		brandDao.persist(brand);
	}
	
	@Override
	public List<Brand> getAllBrands() {
		return brandDao.findAll();
	}

	@Override
	public void createProduct(Product product) {
		productDao.persist(product);
	}

	@Override
	public void createCompany(Company company) {
		companyDao.persist(company);
	}

	@Override
	public void createWarehouse(Warehouse warehouse) {
		warehouseDao.persist(warehouse);
	}

	@Override
	public void createWarehouseRecord(WarehouseRecord warehouseRecord) {
		warehouseRecordDao.persist(warehouseRecord);
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
