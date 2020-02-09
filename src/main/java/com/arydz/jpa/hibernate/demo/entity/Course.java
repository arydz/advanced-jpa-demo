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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
//@Table(name = "CourseDetails") // it's used to define table name
@ToString(exclude = "id")
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@Setter
//	@Column(nullable = false)	// In video where described other useful @Column attributes
	private String name;

	// This is constructor only for JPA, to created this specified bean
	protected Course() {
	}

	public Course(String name) {
		this.name = name;
	}
}
