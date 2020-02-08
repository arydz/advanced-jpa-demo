/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
}
