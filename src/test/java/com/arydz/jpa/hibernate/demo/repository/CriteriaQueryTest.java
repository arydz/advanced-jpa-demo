package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.DemoApplication;
import com.arydz.jpa.hibernate.demo.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CriteriaQueryTest {

	@Autowired
	EntityManager em;

	@Test
	public void all_courses() {
		// Select c from Course c

		// 1. User Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

		// 2. Define roots for tables which are involved in the query
		Root<Course> from = criteriaQuery.from(Course.class);

		// 3. Define predicates etc. using Criteria Builder

		// 4. Add predicates etc. to the Criteria Query

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(from));
		List<Course> resultList = typedQuery.getResultList();
		log.info("Result list {}", resultList);
	}

	@Test
	public void all_courses_having_100Steps() {
		// select c from Course c where name like '%100 steps'

		// 1. User Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

		// 2. Define roots for tables which are involved in the query
		Root<Course> from = criteriaQuery.from(Course.class);

		// 3. Define predicates etc. using Criteria Builder
		Predicate like100Steps = criteriaBuilder.like(from.get("name"), "%100 steps");

		// 4. Add predicates etc. to the Criteria Query
		criteriaQuery.where(like100Steps);

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(from));
		List<Course> resultList = typedQuery.getResultList();
		log.info("Result list {}", resultList);
	}

	@Test
	public void all_courses_without_Students() {
		// select c from Course c where c.studentList is empty

		// 1. User Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

		// 2. Define roots for tables which are involved in the query
		Root<Course> from = criteriaQuery.from(Course.class);

		// 3. Define predicates etc. using Criteria Builder
		Predicate studentListIsEmpty = criteriaBuilder.isEmpty(from.get("studentList"));

		// 4. Add predicates etc. to the Criteria Query
		criteriaQuery.where(studentListIsEmpty);

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(from));
		List<Course> resultList = typedQuery.getResultList();
		log.info("Result list {}", resultList);
	}

	@Test
	public void join() {
		// select c from Course c JOIN c.studentList s

		// 1. User Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

		// 2. Define roots for tables which are involved in the query
		Root<Course> from = criteriaQuery.from(Course.class);

		// 3. Define predicates etc. using Criteria Builder
		Join<Object, Object> join = from.join("studentList");

		// 4. Add predicates etc. to the Criteria Query

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(from));
		List<Course> resultList = typedQuery.getResultList();
		log.info("Result list {}", resultList);
	}

	@Test
	public void left_join() {
		// select c from Course c JOIN c.studentList s

		// 1. User Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

		// 2. Define roots for tables which are involved in the query
		Root<Course> from = criteriaQuery.from(Course.class);

		// 3. Define predicates etc. using Criteria Builder
		Join<Object, Object> join = from.join("studentList", JoinType.LEFT);

		// 4. Add predicates etc. to the Criteria Query

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> typedQuery = em.createQuery(criteriaQuery.select(from));
		List<Course> resultList = typedQuery.getResultList();
		log.info("Result list {}", resultList);
	}
}
