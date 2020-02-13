/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
// If we are changing data, we should use transaction
@Transactional
public class CourseRepository {

	@Autowired
	EntityManager entityManager;

	public Course findById(Long id) {
		return entityManager.find(Course.class, id);
	}

	// insert or update
	public Course save(Course course) {
		if (course.getId() == null) {
			entityManager.persist(course);
		} else {
			entityManager.merge(course);
		}

		return course;
	}

	public void deleteById(Long id) {
		Course course = findById(id);
		entityManager.remove(course);
	}

	// WARN!
	// Entity manager track specified object (which in eg. insert or update) in scope of current transaction and if this object will be modified like
	// here, then entity manager will
	// also
	// persist those changes. That's because all operations in transactions are atomic!
	public void playWithEntityManager() {

		Course course = new Course("Web Services in 100 steps");
		entityManager.persist(course);

		Course courseNext = findById(10001L);
		courseNext.setName("Jpa in 50 steps - Updated");
	}

	public void addHardcodedReviewsForCourse() {

		Course course = findById(10003L);
		log.info("Reviews {}", course.getReviewList());
		Review reviewOne = new Review("3", "Could be better done!");
		Review reviewTwo = new Review("2", "Waste of time!");

		// Setting the relationship
		course.addReview(reviewOne);
		reviewOne.setCourse(course);
		course.addReview(reviewTwo);
		reviewTwo.setCourse(course);

		entityManager.persist(reviewOne);
		entityManager.persist(reviewTwo);
	}

	public void addReviewsForCourse(Long courseId, List<Review> reviewList) {

		Course course = findById(courseId);
		log.info("Reviews {}", course.getReviewList());

		for (Review review : reviewList) {

			course.addReview(review);
			review.setCourse(course);
			entityManager.persist(review);
		}

	}
}
