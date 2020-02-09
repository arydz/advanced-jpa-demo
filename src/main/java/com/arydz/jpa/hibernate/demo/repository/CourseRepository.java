/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
		Course courseNext = new Course("Angular JS in 100 steps");
		entityManager.persist(courseNext);
		entityManager.flush();

		course.setName("Web Services in 100 steps - Updated");
		courseNext.setName("Angular JS in 100 steps - Updated");

		// It's retrieve specified object from database and omits changes. All changes to this object are lost
		entityManager.refresh(course);

		entityManager.flush();
	}
}
