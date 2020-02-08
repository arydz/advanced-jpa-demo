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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@ToString(exclude = "id")
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@Setter
	private String name;

	// This is constructor only for JPA, to created this specified bean
	protected Course() {
	}

	public Course(String name) {
		this.name = name;
	}
}
