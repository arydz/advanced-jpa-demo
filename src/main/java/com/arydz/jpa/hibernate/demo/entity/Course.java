package com.arydz.jpa.hibernate.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString(exclude = {"id", "studentList"})
@NamedQueries(value = {
				@NamedQuery(name = "get_all_courses", query = "select c from Course c"),
				@NamedQuery(name = "get_100_steps_courses", query = "select c from Course c where name like '%100 steps'")
})
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@Setter
	private String name;

	// Default fetch type is LAZY
	@OneToMany(mappedBy = "course") //, fetch = FetchType.EAGER)
	private List<Review> reviewList = new ArrayList<>();

	// In many to many relationships, owning side of relation is not important.
	// Course is not owning side!
	@ManyToMany(mappedBy = "courseList")
	private List<Student> studentList = new ArrayList<>();

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;

	public void addReview(Review review) {
		this.reviewList.add(review);
	}

	public void removeReview(Review review) {
		this.reviewList.remove(review);
	}

	public void addStudent(Student student) {
		this.studentList.add(student);
	}

	public void removeReview(Student student) {
		this.studentList.remove(student);
	}



	protected Course() {
	}

	public Course(String name) {
		this.name = name;
	}
}
