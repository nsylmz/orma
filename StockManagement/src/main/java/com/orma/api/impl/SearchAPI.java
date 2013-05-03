package com.orma.api.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
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
import com.orma.domain.Brand;
import com.orma.domain.Product;
import com.orma.domain.TransactionType;
import com.orma.domain.Warehouse;
import com.orma.domain.WarehouseRecord;
import com.orma.utils.SqlConstants;
import com.orma.vo.ProductTotalInfo;
import com.orma.vo.ReportType;
import com.orma.vo.WarehouseStockReport;

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
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<WarehouseStockReport> getReportsByWarehouseOrProductOrBrand(Warehouse warehouse, Product product, Brand brand, ReportType reportType) {
		
		List<WarehouseStockReport> reportList = new ArrayList<WarehouseStockReport>();
		Session session = (Session) entityManager.getDelegate();
		SQLQuery query = null;
		List<Object[]> results = null;
		Object[] row = null;
		WarehouseStockReport report = null;
		
		if ((warehouse != null && warehouse.getId() != null)
				&& (product != null && product.getId() != null)
				&& (brand != null && brand.getId() != null)
				&& reportType.equals(ReportType.warehouseAndBrandAndProduct)) {
			query = session.createSQLQuery(SqlConstants.reportByWarehouseAndBrandAndProduct);
			query.addScalar("wName");
			query.addScalar("bName");
			query.addScalar("pName");
			query.addScalar("pBarcode");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			query.setLong(0, warehouse.getId());
			query.setLong(1, brand.getId());
			query.setLong(2, product.getId());
			results = query.list();
			
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setWarehouseName(((String)row[0]).trim());
				report.setBrandName(((String)row[1]).trim());
				report.setProductName(((String)row[2]).trim());
				report.setProductBarcode(((BigInteger)row[3]).longValue());
				report.setTotalAmount(((BigDecimal)row[4]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[5]);
				report.setTotalSellPrice((BigDecimal)row[6]);
			}
		} else if ((warehouse != null && warehouse.getId() != null)
				&& reportType.equals(ReportType.brandsByWarehouse)) {
			query = session.createSQLQuery(SqlConstants.reportBrandsByWarehouse);
			query.setLong(0, warehouse.getId());
			query.addScalar("bName");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			results = query.list();
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setBrandName(((String)row[0]).trim());
				report.setTotalAmount(((BigDecimal)row[1]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[2]);
				report.setTotalSellPrice((BigDecimal)row[3]);
				reportList.add(report);
			}
		} else if ((brand != null && brand.getId() != null)
				&& (product != null && product.getId() != null)
				&& reportType.equals(ReportType.brandAndProduct)) {
			query = session.createSQLQuery(SqlConstants.reportByBrandAndProduct);
			query.setLong(0, brand.getId());
			query.setLong(1, product.getId());
			query.addScalar("wName");
			query.addScalar("bName");
			query.addScalar("pName");
			query.addScalar("pBarcode");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			results = query.list();
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setWarehouseName(((String)row[0]).trim());
				report.setBrandName(((String)row[1]).trim());
				report.setProductName(((String)row[2]).trim());
				report.setProductBarcode(((BigInteger)row[3]).longValue());
				report.setTotalAmount(((BigDecimal)row[4]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[5]);
				report.setTotalSellPrice((BigDecimal)row[6]);
				reportList.add(report);
			}
		} else if (reportType.equals(ReportType.general)) {
			query = session.createSQLQuery(SqlConstants.reportGeneral);
			query.addScalar("wName");
			query.addScalar("bName");
			query.addScalar("pName");
			query.addScalar("pBarcode");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			results = query.list();
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setWarehouseName(((String)row[0]).trim());
				report.setBrandName(((String)row[1]).trim());
				report.setProductName(((String)row[2]).trim());
				report.setProductBarcode(((BigInteger)row[3]).longValue());
				report.setTotalAmount(((BigDecimal)row[4]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[5]);
				report.setTotalSellPrice((BigDecimal)row[6]);
				reportList.add(report);
			}
		} else if (reportType.equals(ReportType.sayim)) {
			query = session.createSQLQuery(SqlConstants.reportSayim);
			query.addScalar("bName");
			query.addScalar("pName");
			query.addScalar("pBarcode");
			query.addScalar("pBuyPrice");
			query.addScalar("pSellPrice");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			results = query.list();
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setBrandName(((String)row[0]).trim());
				report.setProductName(((String)row[1]).trim());
				report.setProductBarcode(((BigInteger)row[2]).longValue());
				report.setProductBuyPrice(((BigDecimal)row[3]));
				report.setProductSellPrice(((BigDecimal)row[4]));
				report.setTotalAmount(((BigDecimal)row[5]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[6]);
				report.setTotalSellPrice((BigDecimal)row[7]);
				reportList.add(report);
			}
		} else if (reportType.equals(ReportType.brands)) {
			query = session.createSQLQuery(SqlConstants.reportBrands);
			query.addScalar("bName");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			results = query.list();
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setBrandName(((String)row[0]).trim());
				report.setTotalAmount(((BigDecimal)row[1]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[2]);
				report.setTotalSellPrice((BigDecimal)row[3]);
				reportList.add(report);
			}
		} else if (reportType.equals(ReportType.warehouse)) {
			query = session.createSQLQuery(SqlConstants.reportWarehouses);
			query.addScalar("wName");
			query.addScalar("total_amount");
			query.addScalar("total_buy");
			query.addScalar("total_sell");
			results = query.list();
			for (int i = 0; i < results.size(); i++) {
				row = results.get(i);
				report = new WarehouseStockReport();
				report.setWarehouseName(((String)row[0]).trim());
				report.setTotalAmount(((BigDecimal)row[1]).longValue());
				report.setTotalBuyPrice((BigDecimal)row[2]);
				report.setTotalSellPrice((BigDecimal)row[3]);
				reportList.add(report);
			}
		}
		return reportList;
	}

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
