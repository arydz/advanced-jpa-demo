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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString(exclude = "id")
// Those annotations allows us to assign query to name. Thanks for that, we ca avoid sql duplications and reuse it in multiple application parts
// We can use @NamedQuery alone
@NamedQueries(value = {
				@NamedQuery(name = "get_all_courses", query = "select c from Course c"),
				@NamedQuery(name = "get_100_steps_courses", query = "select c from Course c where name like '%100 steps'")
})
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@Setter
//	@Column(nullable = false)	// In video where described other useful @Column attributes
	private String name;

	// Populate information about creation date of current row
	@CreationTimestamp
	private LocalDateTime createdDate;

	// Populate information about last update date of current row
	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;


	// This is constructor only for JPA, to created this specified bean
	protected Course() {
	}

	public Course(String name) {
		this.name = name;
	}
}
