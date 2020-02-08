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
		entityManager.flush(); // Changes that will be made until flush, will be send to database.
		// It's important when we are using clear or detach method. If we don't flush data and next we will clear entity manager, then it won't
		// know wat to track and send to database. No persistence operation will be invoked!

		entityManager.clear(); // This detach all tracked objects in current transaction - it's instead of those two detach method invokes
		//entityManager.detach(course); // This method cause that changes are no longer tracked for this object!
		//entityManager.detach(courseNext); // This method cause that changes are no longer tracked for this object!

		course.setName("Web Services in 100 steps - Updated");
//		entityManager.flush();
		courseNext.setName("Angular JS in 100 steps - Updated");
//		entityManager.flush();
	}
}
