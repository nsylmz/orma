package com.orma.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="BRAND")
public class Brand extends BaseEntity {
	
	@Column(name = "NAME")
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.name == null) {
			return super.equals(obj);
		}
		return (this.name.equals(((Brand) obj).getName()));
	}

}
