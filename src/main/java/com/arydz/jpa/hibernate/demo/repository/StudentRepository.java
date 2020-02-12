/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.entity.Passport;
import com.arydz.jpa.hibernate.demo.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Slf4j
@Repository
// If we are changing data, we should use transaction
@Transactional // Now transaction is provided by class to all methods
public class StudentRepository {

	@Autowired
	EntityManager entityManager;

	public Student findById(Long id) {
		return entityManager.find(Student.class, id);
	}

	// insert or update
	public Student save(Student student) {
		if (student.getId() == null) {
			entityManager.persist(student);
		} else {
			entityManager.merge(student);
		}

		return student;
	}

	public void deleteById(Long id) {
		Student student = findById(id);
		entityManager.remove(student);
	}

	public void saveStudentWithPassport() {

		Passport passport = new Passport("Z456789");
		// Hibernate is lazy! It will wait as long as it can before inserting the passport in.
		// In this step, hibernate sequence generates id for passport
		entityManager.persist(passport);
		Student student = new Student("Beata");
		student.setPassport(passport);
		// Hibernate is lazy! It will wait as long as it can before inserting the student in.
		// In this step, hibernate sequence generates id for student
		entityManager.persist(student);
	} // At the end of transaction hibernate, will send all changes to DB

	// Now transaction is provided by class to all methods
	public void someOperationToUnderstandPersistenceContext() {
		// Database Operation 1 - Retrieve student
		Student student = entityManager.find(Student.class, 20001L);
		// Persistence Context (student)

		// Database Operation 2 - Retrieve passport
		Passport passport = student.getPassport();
		// Persistence Context (student, passport)

		// Database Operation 3 - Update passport
		passport.setNumber("X123456");
		// Persistence Context (student_changed, passport_changed)

		// Database Operation 4 - Update student
		student.setName("Adrian - Updated");
		// Persistence Context (student_changed, passport_changed)
	} // Here all ale changes send out to data base
}
