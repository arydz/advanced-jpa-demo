package com.arydz.jpa.hibernate.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	// Using ordinal enum mapping is risky. We have to keep order of old values, otherwise we would mess our database.
	// It's not a good practice.
	@Enumerated(EnumType.STRING)
	private ReviewRating rating;

	private String description;

	// Default fetch type is EAGER
	@ManyToOne
	private Course course;

	protected Review() {
	}

	public Review(ReviewRating reviewRating, String description) {
		this.rating = reviewRating;
		this.description = description;
	}
}
