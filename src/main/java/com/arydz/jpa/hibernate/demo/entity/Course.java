package com.arydz.jpa.hibernate.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString(of = "name")
@NamedQueries(value = {
				@NamedQuery(name = "get_all_courses", query = "select c from Course c"),
				@NamedQuery(name = "get_100_steps_courses", query = "select c from Course c where name like '%100 steps'")
})
@Cacheable
// Those annotations are for Soft Delete - it's mean that row isn't removed from table, but appropriate column (in our example 'is_deleted') stores
// information, if row was deleted. Below we write appropriate query to do this.
// Column get updated in the database, however the entity in the cache does not get updated because hibernate doesn't now know at all about what
// happening in this query. For that we need to create PreRemove hook, which will update entity isDelete attribute to true.
@SQLDelete(sql = "update course set is_deleted=true where id=?")
// With annotation @Where we specify that only rows with false value in 'is_delete' column should be return. We want only active rows (not deleted).
// This clause is added to hibernate query
@Where(clause = "is_deleted = false") // Native queries doesn't support it
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
	@JsonIgnore
	private List<Student> studentList = new ArrayList<>();

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;

	private boolean isDeleted;

	// Without it Course entity would stays outdated comparing to database
	@PreRemove
	private void preRemove() {
		this.isDeleted = true;
	}

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
