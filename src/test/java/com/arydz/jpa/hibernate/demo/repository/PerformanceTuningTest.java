package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.DemoApplication;
import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Review;
import com.arydz.jpa.hibernate.demo.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class PerformanceTuningTest {

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	public void creatingNPlusOneProblem() {
		List<Course> courses = em.createNamedQuery("get_all_courses", Course.class).getResultList();
		for (Course course : courses) {
			log.info("Course -> {} \nStudents -> {}", course, course.getStudentList() );
		}
	}

	@Test
	@Transactional
	public void solvingNPlusOneProblem_EntityGraph() {
		EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
		Subgraph<Object> studentList = entityGraph.addSubgraph("studentList");

		List<Course> courses = em.createNamedQuery("get_all_courses", Course.class) //
						.setHint("javax.persistence.loadgraph", entityGraph)
						.getResultList();

		for (Course course : courses) {
			log.info("Course -> {} \nStudents -> {}", course, course.getStudentList() );
		}
	}

	@Test
	@Transactional
	public void solvingNPlusOneProblem_JoinFetch() {
		List<Course> courses = em.createNamedQuery("get_all_courses_with_join_fetch", Course.class).getResultList();
		for (Course course : courses) {
			log.info("Course -> {} \nStudents -> {}", course, course.getStudentList() );
		}
	}
}