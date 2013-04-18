package com.orma.vo;

import java.math.BigDecimal;

import com.orma.domain.Product;

public class ProductTotalInfo {
	
	private Product product;
	
	private Long totalAmount;
	
	private BigDecimal totalBuy;
	
	private BigDecimal totalSell;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalBuy() {
		return totalBuy;
	}

	public void setTotalBuy(BigDecimal totalBuy) {
		this.totalBuy = totalBuy;
	}

	public BigDecimal getTotalSell() {
		return totalSell;
	}

	public void setTotalSell(BigDecimal totalSell) {
		this.totalSell = totalSell;
	}
	
}
