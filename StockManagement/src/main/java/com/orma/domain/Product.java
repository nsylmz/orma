package com.orma.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="PRODUCT",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id", "BARCODE"}))
public class Product extends BaseEntity implements IBaseEntity {
	
	@Column(name = "NAME")
	@NotNull
	private String name;
	
	@Column(name = "BARCODE")
	@NotNull
	private Long barcode;
	
//	@Column(name = "PRODUCT_TYPE")
//	@NotNull
//	private String productType;
	
	@NotNull
	@Column(name = "BUY_PRICE")
	private BigDecimal buyPrice;
	
	@NotNull
	@Column(name = "SELL_PRICE")
	private BigDecimal sellPrice;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name="BRAND_ID", nullable=false, referencedColumnName="id")
	private Brand brand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBarcode() {
		return barcode;
	}

	public void setBarcode(Long barcode) {
		this.barcode = barcode;
	}

//	public String getProductType() {
//		return productType;
//	}
//
//	public void setProductType(String productType) {
//		this.productType = productType;
//	}
//	
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@Override
	public boolean contentEquals(Object obj) {
		if (obj == null || this.name == null 
				|| this.name.trim().length() == 0) {
			return super.equals(obj);
		}
		return (this.name.trim().equals(((Product) obj).getName().trim()));
	}
	
}
