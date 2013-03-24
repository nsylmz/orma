package com.orma.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="COMPANY")
public class Company extends BaseEntity {
   
	@Column(name = "NAME")
	@NotNull
	private String name;
	
	@Column(name = "TEL")
	private String tel;
	
	@Column(name = "ADRESS")
	private String adress;
	
	@Column(name = "TAX_NUMBER")
	private Long taxNumber;
	
	@Column(name = "TAX_NAME")
	private String taxName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Long getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(Long taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

}
