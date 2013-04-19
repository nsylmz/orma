package com.orma.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	private Long amount;

	@Column(name = "BILL_NUMBER")
	private Long billNumber;
	
	@NotNull
	@Column(name = "DEPLOYMENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date deploymentDate;

	@Column(name = "TRANSACTION_TYPE")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Long billNumber) {
		this.billNumber = billNumber;
	}
	
	public Date getDeploymentDate() {
		return deploymentDate;
	}

	public void setDeploymentDate(Date deploymentDate) {
		this.deploymentDate = deploymentDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}
