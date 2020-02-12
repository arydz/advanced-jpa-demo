package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.DemoApplication;
import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Passport;
import com.arydz.jpa.hibernate.demo.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class StudentRepositoryTest {

	@Autowired
	StudentRepository repository;

	@Autowired
	EntityManager em;

	// Tests are invoked without order!
	@Test
	@Transactional // Without it there would be LazyInitializationException. That's because only  entity manager find method is in transaction
	// (starts and end in there) and hibernate is trying to retrieve passport in the same transaction but later, we need a sessions.
	// So we need the transaction to retrieve passport details. That solves the problem
	public void retrieveStudentAndPassportDetails() {
		Student student = em.find(Student.class, 20001L);
		log.info("Student -> {}", student);
		// Hibernate know how to retrieve this entity, because of mapping
		log.info("Passport -> {}", student.getPassport());
	}

	@Test
	public void someTest() {
		repository.someOperationToUnderstandPersistenceContext();
	}
}