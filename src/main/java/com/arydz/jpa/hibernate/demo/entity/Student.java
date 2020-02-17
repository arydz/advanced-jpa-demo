package com.arydz.jpa.hibernate.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

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

	// Student is an owning side, because there is no mapping
	// EAGER fetch is not recommended on many to many relationships
	@ManyToMany
	// It's optional annotation, it allows us to customize this associative array
	@JoinTable(name = "STUDENT_COURSE", //
					joinColumns = @JoinColumn(name = "STUDENT_ID"),
					inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
	private List<Course> courseList = new ArrayList<>();

	public void addCourse(Course course) {
		this.courseList.add(course);
	}

	public void removeCourse(Course course) {
		this.courseList.remove(course);
	}

	protected Student() {
	}

	public Student(String name) {
		this.name = name;
	}
}
