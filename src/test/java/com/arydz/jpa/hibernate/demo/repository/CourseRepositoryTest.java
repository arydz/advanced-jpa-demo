package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.DemoApplication;
import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
/**
 * We can even create a whole custom Spring Boot application to start up in tests.
 * If this application class is in the same package as the real application class,
 * but in the test sources rather than the production sources,
 * @SpringBootTest will find it before the actual application class and load the application context from this application instead.
 *
 * Alternatively, we can tell Spring Boot which application class to use to create an application context:
 * classes = DemoApplication.class
 *
 * When doing this, however, we’re testing an application context that may be completely different from the production environment,
 * so this should be a last resort only when the production application cannot be started in a test environment.
 * Usually, there are better ways, though,
 * such as to make the real application context configurable to exclude beans that won’t start in a test environment.
 * https://reflectoring.io/spring-boot-test/
 */
@SpringBootTest(classes = DemoApplication.class)
public class CourseRepositoryTest {

	@Autowired
	CourseRepository repository;

	@Autowired
	EntityManager em;

	@Test
	public void findById_basic() {
		Course course = repository.findById(10001L);
		assertEquals("Jpa in 50 steps", course.getName());
	}

	@Test
	@DirtiesContext
	public void deleteById_basic() {
		repository.deleteById(10002L);
		Course course = repository.findById(10002L);
		assertNull(course);
	}

	@Test
	@DirtiesContext
	public void saveById_basic() {
		Course course = repository.findById(10001L);
		course.setName("Jpa in 50 steps - Updated");
		repository.save(course);

		Course updatedCourse = repository.findById(10001L);
		assertEquals("Jpa in 50 steps - Updated", updatedCourse.getName());
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager() {

		repository.playWithEntityManager();
	}

	@Test
	@Transactional
	public void retrieveReviewsForCourse() {
		Course course = repository.findById(10001L);
		log.info("{}", course.getReviewList());
	}

	@Test
	@Transactional
	public void retrieveCourseForReview() {
		Review review = em.find(Review.class,40001L);
		log.info("{}", review.getCourse());
	}
}