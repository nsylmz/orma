package com.orma.domain;

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
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id", "NAME", "BARCODE"}))
public class Product extends BaseEntity implements IBaseEntity {
	
	@Column(name = "NAME")
	@NotNull
	private String name;
	
	@Column(name = "BARCODE")
	@NotNull
	private Long barcode;
	
	@Column(name = "PRODUCT_TYPE")
	@NotNull
	private String productType;
	
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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
