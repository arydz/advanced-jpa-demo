package com.arydz.jpa.hibernate.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@ToString(exclude = "course")
public class Review {

	@Id
	@GeneratedValue
	private Long id;

	private String rating;

	private String description;

	// Default fetch type is EAGER
	@ManyToOne
	private Course course;

	protected Review() {
	}

	public Review(String rating, String description) {
		this.rating = rating;
		this.description = description;
	}
}
