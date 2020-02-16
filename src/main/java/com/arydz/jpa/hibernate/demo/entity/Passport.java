/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@ToString(exclude = "student")
public class Passport {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String number;

	// Now we defined Bidirectional Relationship, this cause duplication of information in Data Base
	// Good quality data base is that without any information duplications! Now student id is stored in two tables, and passport id is also stored
	// in to tables! That's not good!
	// We should defined the owning side of relationship. To do that we need to use mappedBy property which indicates entity field which is mapping
	// an relations between entities. Therefore no additional column will be created. This gives us only the way to navigate between entities.
	// We always add mappedBy on to the non-owning side of relationship!
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
	private Student student;

	protected Passport() {
	}

	public Passport(String number) {
		this.number = number;
	}
}
