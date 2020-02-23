/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

// It's provided by Spring Data Jpa
// It resolves problem of duplicated code among all repositories, where was a lot of
// redundant methods, like insert, find, update, delete.
// Spring data also solves problem of different kinds of data stores.
// Earlier there were only relational databases, but now there more solution for storing data
// like no-sql databases etc. Spring Data Jpa provides simple abstraction to be able
// to access any kind of data. Spring Data Jpa is Jpa specific implementation of Spring Data.
@RepositoryRestResource(path = " courses")	// It's not recommended for production code. It's good to use when we want to quickly prototype something
public interface CourseSpringDataJpnRepository extends JpaRepository<Course, Long> {

	List<Course> findByName(String name);

	List<Course> findByNameOrderByIdDesc(String name);

	List<Course> findByNameAndCreatedDate(String name, LocalDateTime createdDate);

	int countByName(String name);

	void deleteByName(String name);

	@Query("select c from Course c where name like '%50 steps'")
	List<Course> coursesWith100StepsInName();

	@Query(name = "get_100_steps_courses")
	List<Course> coursesWith100StepsInNameNamedQuery();

	@Query(value = "select * from Course where name like '%50 steps'", nativeQuery = true)
	List<Course> coursesWith100StepsInNameUsingNativeQuery();
}
