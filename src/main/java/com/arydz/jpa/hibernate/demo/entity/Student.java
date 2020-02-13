/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString
public class Student {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	// Default fetch type is EAGER
	// THIS IS CALLED : unidirectional relationship - where relation is defined in one way
	// Creates foreign key of passport table
	// All one to one relations are default eager fetch, so when retrieving Student we are also getting passport entity
	// Eager fetching might cause some performance issues!
	@OneToOne(fetch = FetchType.LAZY)
	private Passport passport;

	protected Student() {
	}

	public Student(String name) {
		this.name = name;
	}
}
