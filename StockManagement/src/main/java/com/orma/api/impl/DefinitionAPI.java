package com.orma.api.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orma.api.IDefinitionAPI;
import com.orma.dao.BrandDaoI;
import com.orma.dao.ProductDaoI;
import com.orma.dao.WarehouseDaoI;
import com.orma.dao.WarehouseRecordDaoI;
import com.orma.domain.Brand;
import com.orma.domain.Product;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;
import com.orma.exception.StockManagementException;

@Transactional(propagation = Propagation.REQUIRED)
@Component
public class DefinitionAPI implements IDefinitionAPI {

	@Autowired
	private BrandDaoI brandDao;

	@Autowired
	private ProductDaoI productDao;

//	@Autowired
//	private CompanyDaoI companyDao;

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
	public void saveProduct(Product product) throws StockManagementException {
		if (product != null && product.getBarcode() != 0L) {
			if (getProductByBarcode(product.getBarcode()) == null) {
				productDao.merge(product);
			} else {
				throw new StockManagementException("6", "Aynı barkod ile birden fazla ürün tanımlayamazsınız!!! Lütfen barkod numarasını değiştirin.");
			}
		}
	}
	
	@Override
	public void deleteProduct(Product product) {
		productDao.delete(productDao.findById(product.getId()));
	}
	
	@Override
	public List<Product> getAllProducts() {
		return productDao.findAll();
	}
	
	@Override
	public List<Product> getProductsByBrand(Brand brand) {
		return productDao.findByCriteria(Restrictions.eq("brand.id", brand.getId()));
	}
	
	@Override
	public Product getProductByBarcode(Long barcode) throws StockManagementException {
		List<Product> products =  productDao.findByCriteria(Restrictions.eq("barcode", barcode));;
		if (products != null && products.size() == 1) {
			return products.get(0);
		} else if (products.size() > 1) {
			throw new StockManagementException("5", "Aynı barkod ile birden fazla ürün tanımlanmış!!! Barkod no: " + barcode);
		}
		return null;
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
	
	@Override
	public void deleteWarehouseRecord(WarehouseRecord warehouseRecord) {
		warehouseRecordDao.delete(warehouseRecordDao.findById(warehouseRecord.getId()));
	}
	
	@Override
	public List<WarehouseRecord> getAllWarehouseRecords() {
		return warehouseRecordDao.findAll();
	}
	
	@Override
	public List<WarehouseRecord> getWarehouseRecordsByWarehouseAndProduct(Warehouse warehouse, Product product) {
		return warehouseRecordDao.findByCriteria(Restrictions.and(Restrictions.eq("warehouse.id", warehouse.getId()), 
														  		  Restrictions.eq("product.id", product.getId())));
	}
	
	@Override
	public List<WarehouseRecord> getWarehouseRecordsByWarehouse(Warehouse warehouse) {
		return warehouseRecordDao.findByCriteria(Restrictions.eq("warehouse.id", warehouse.getId()));
	}
	
	@Override
	public List<WarehouseRecord> getWarehouseRecordsByProduct(Product product) {
		return warehouseRecordDao.findByCriteria(Restrictions.eq("product.id", product.getId()));
	}
	
	public void setBrandDao(BrandDaoI brandDao) {
		this.brandDao = brandDao;
	}

	public void setProductDao(ProductDaoI productDao) {
		this.productDao = productDao;
	}

	public void setWarehouseDao(WarehouseDaoI warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public void setWarehouseRecordDao(WarehouseRecordDaoI warehouseRecordDao) {
		this.warehouseRecordDao = warehouseRecordDao;
	}
	
}
