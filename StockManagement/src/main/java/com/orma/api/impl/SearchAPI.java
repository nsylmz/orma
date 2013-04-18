package com.orma.api.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orma.api.ISearchAPI;
import com.orma.dao.BrandDaoI;
import com.orma.dao.CompanyDaoI;
import com.orma.dao.ProductDaoI;
import com.orma.dao.WarehouseDaoI;
import com.orma.dao.WarehouseRecordDaoI;
import com.orma.domain.Product;
import com.orma.domain.TransactionType;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;
import com.orma.vo.ProductTotalInfo;

@Transactional(propagation = Propagation.REQUIRED)
@Component
public class SearchAPI implements ISearchAPI {

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

	public ProductTotalInfo getProductTotalInfoByWarehouse(Product product, Warehouse warehouse) {
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.groupProperty("transactionType"), "transactionType");
		proList.add(Projections.groupProperty("product.id"), "id");
		proList.add(Projections.groupProperty("warehouse.id"), "id");
		proList.add(Projections.sum("amount"), "amount");

		List<WarehouseRecord> recordList = warehouseRecordDao.findByCriteria(
				proList, Restrictions.and(Restrictions.eq("warehouse.id", warehouse.getId()),
										  Restrictions.eq("product.id", product.getId())));
		
		ProductTotalInfo ptInfo = new ProductTotalInfo();
		WarehouseRecord girdi = null;
		WarehouseRecord cikti = null;
		Long amount = 0L;
		if (recordList.size() == 2) {
			for (int i = 0; i < recordList.size(); i++) {
				if (recordList.get(i).getTransactionType().equals(TransactionType.girdi)) {
					girdi = recordList.get(i);
				} else if (recordList.get(i).getTransactionType().equals(TransactionType.çıktı)) {
					cikti = recordList.get(i);
				} else {
					//TODO throw exception
				}
			}
			amount = girdi.getAmount() - cikti.getAmount();
		} else if (recordList.size() == 1) {
			if (recordList.get(0).getTransactionType().equals(TransactionType.girdi)) {
				girdi = recordList.get(0);
				amount = girdi.getAmount();
			} else if (recordList.get(0).getTransactionType().equals(TransactionType.çıktı)) {
				// TODO : throw exception Olmayan stok düşülmüş
			} 
		}
		
		ptInfo.setProduct(product);
		ptInfo.setTotalAmount(amount);
		ptInfo.setTotalBuy(product.getBuyPrice().multiply(BigDecimal.valueOf(amount)));
		ptInfo.setTotalSell(product.getSellPrice().multiply(BigDecimal.valueOf(amount)));
		return ptInfo;
	}

	public BrandDaoI getBrandDao() {
		return brandDao;
	}

	public void setBrandDao(BrandDaoI brandDao) {
		this.brandDao = brandDao;
	}

	public ProductDaoI getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDaoI productDao) {
		this.productDao = productDao;
	}

	public CompanyDaoI getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDaoI companyDao) {
		this.companyDao = companyDao;
	}

	public WarehouseDaoI getWarehouseDao() {
		return warehouseDao;
	}

	public void setWarehouseDao(WarehouseDaoI warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public WarehouseRecordDaoI getWarehouseRecordDao() {
		return warehouseRecordDao;
	}

	public void setWarehouseRecordDao(WarehouseRecordDaoI warehouseRecordDao) {
		this.warehouseRecordDao = warehouseRecordDao;
	}

}
