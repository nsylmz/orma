package com.orma.vo;

import java.math.BigDecimal;

public class WarehouseStockReport {

	private String warehouseName;

	private String brandName;

	private String productName;

	private Long productBarcode;

	private BigDecimal productBuyPrice;

	private BigDecimal totalBuyPrice;

	private BigDecimal totalSellPrice;

	private BigDecimal productSellPrice;

	private Long totalAmount;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(Long productBarcode) {
		this.productBarcode = productBarcode;
	}

	public BigDecimal getProductBuyPrice() {
		return productBuyPrice;
	}

	public void setProductBuyPrice(BigDecimal productBuyPrice) {
		this.productBuyPrice = productBuyPrice;
	}

	public BigDecimal getTotalBuyPrice() {
		return totalBuyPrice;
	}

	public void setTotalBuyPrice(BigDecimal totalBuyPrice) {
		this.totalBuyPrice = totalBuyPrice;
	}

	public BigDecimal getTotalSellPrice() {
		return totalSellPrice;
	}

	public void setTotalSellPrice(BigDecimal totalSellPrice) {
		this.totalSellPrice = totalSellPrice;
	}

	public BigDecimal getProductSellPrice() {
		return productSellPrice;
	}

	public void setProductSellPrice(BigDecimal productSellPrice) {
		this.productSellPrice = productSellPrice;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

}
