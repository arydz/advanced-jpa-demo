package com.arydz.jpa.hibernate.demo.entity;

import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;

@ToString(callSuper = true)
@Entity
public class PartTimeEmployee extends Employee {

	private BigDecimal hourlyWage;

	protected PartTimeEmployee() {
	}

	public PartTimeEmployee(String name, BigDecimal hourlyWage) {
		super(name);
		this.hourlyWage = hourlyWage;
	}
}
