package com.orma.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "WAREHOUSE_RECORD")
public class WarehouseRecord extends BaseEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false, referencedColumnName = "id")
	private Product product;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "WAREHOUSE_ID", nullable = false, referencedColumnName = "id")
	private Warehouse warehouse;

	@NotNull
	@Column(name = "AMOUNT")
	private Integer amount;

	@Column(name = "BILL_NUMBER")
	private Long billNumber;

	@Column(name = "PLACE")
	private String place;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Long billNumber) {
		this.billNumber = billNumber;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
