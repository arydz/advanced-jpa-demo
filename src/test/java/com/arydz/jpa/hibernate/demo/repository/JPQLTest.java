package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.DemoApplication;
import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JPQLTest {

	@Autowired
	EntityManager em;

	@Test
	public void jpql_raw() {
		List resultList = em.createNamedQuery("get_all_courses").getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_typed() {
		List<Course> resultList = em.createNamedQuery("get_all_courses", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_where() {
		List<Course> resultList = em.createNamedQuery("get_100_steps_courses", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	/**
	 * With SQL we would have to write such query
	 * select * from course
	 * where course.id not in
	 * (select course_id from student_course)
	 */
	@Test
	public void jpql_courses_without_students() {
		List<Course> resultList = em.createQuery("SELECT c FROM Course c WHERE c.studentList IS EMPTY", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_courses_with_at_least_two_students() {
		List<Course> resultList = em.createQuery("SELECT c FROM Course c WHERE size(c.studentList) >= 2", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_courses_order_by_nr_of_students() {
		List<Course> resultList = em.createQuery("SELECT c FROM Course c order by size(c.studentList) desc", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_students_with_passport_in_a_certain_pattern() {
		List<Student> resultList = em.createQuery("SELECT s FROM Student s where s.passport.number like '%234%'", Student.class).getResultList();
		log.info("List {}", resultList);
	}

	// JPQL supports a lot of utils methods:
	// like
	// between 100 and 200
	// is null
	// upper, lower, trim, length, etc. ...

	// JOIN => Select c, s from Course c JOIN c.studentList s -- it will not retrieve courses without students
	// LEFT JOIN => Select c, s from Course c LEFT JOIN c.studentList s -- it will retrieve all courses even courses without students at all
	// CROSS JOIN => Select c, s from Course c, Student s -- it will take all courses and all students and cross them. They are just mixed
	// EG. 3 (courses) and 4 students => 3*4 = will return 12 rows
	@Test
	public void join() {
		// Hibernate puts c,s results in separate arrays
		Query query = em.createQuery("Select c, s from Course c JOIN c.studentList s");
		List<Object[]> resultList = query.getResultList();
		log.info("Result {} ", resultList.size());
		for (Object[] result : resultList) {
			log.info("Courses {}, \n Students {} ", result[0], result[1]);
		}
	}

	@Test
	public void left_join() {
		Query query = em.createQuery("Select c, s from Course c LEFT JOIN c.studentList s");
		List<Object[]> resultList = query.getResultList();
		log.info("Result {} ", resultList.size());
		for (Object[] result : resultList) {
			log.info("Courses {}, \n Students {} ", result[0], result[1]);
		}
	}

	// Cross combines each row of first table with each row of second table
	@Test
	public void cross_join() {
		Query query = em.createQuery("Select c, s from Course c, Student s");
		List<Object[]> resultList = query.getResultList();
		log.info("Result {} ", resultList.size());
		for (Object[] result : resultList) {
			log.info("Courses {}, \n Students {} ", result[0], result[1]);
		}
	}
}
