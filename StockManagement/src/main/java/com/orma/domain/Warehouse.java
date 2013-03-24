package com.orma.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="WAREHOUSE")
public class Warehouse extends BaseEntity {

	@Column(name = "NAME")
	@NotNull
	private String name;
	
	@Column(name = "PLACE")
	@NotNull
	private String place;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
