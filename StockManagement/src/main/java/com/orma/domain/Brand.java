package com.orma.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="BRAND",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"id", "NAME"}))
public class Brand extends BaseEntity implements IBaseEntity {
	
	@Column(name = "NAME")
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean contentEquals(Object obj) {
		if (obj == null || this.name == null 
				|| this.name.trim().length() == 0) {
			return super.equals(obj);
		}
		return (this.name.trim().equals(((Brand) obj).getName().trim()));
	}

}
