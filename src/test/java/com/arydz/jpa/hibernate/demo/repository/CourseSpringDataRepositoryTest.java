package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.DemoApplication;
import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CourseSpringDataRepositoryTest {

	@Autowired
	CourseSpringDataJpnRepository repository;

	@Autowired
	EntityManager em;

	@Test
	public void findById_CoursePresent() {
		Optional<Course> courseOptional = repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}

	@Test
	public void findById_CourseNotPresent() {
		Optional<Course> courseOptional = repository.findById(20001L);
		assertFalse(courseOptional.isPresent());
	}

	@Test
	public void playingAroundSpringDataRepository() {
//		Course course = new Course("Spring 5 in 2020");
//		repository.save(course);
//		course.setName("Spring 5 in 2020 - Updated");
//		repository.save(course);
		log.info("Courses -> {}", repository.findAll());
		log.info("Count -> {}", repository.count());
	}

	@Test
	public void sort() {
		Sort sort = Sort.by(Sort.Direction.DESC, "name");
		log.info("Sorted Courses -> {}", repository.findAll(sort));
		log.info("Count -> {}", repository.count());
	}

	@Test
	public void pagination() {

		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<Course> firstPage = repository.findAll(pageRequest);
		log.info("First page Courses -> {}", firstPage.getContent());

		Pageable secondPageable = firstPage.nextPageable();
		Page<Course> secondPage = repository.findAll(secondPageable);
		log.info("Second page Courses -> {}", secondPage.getContent());

	}

	@Test
	public void findByName() {
		List<Course> courses = repository.findByName("Java in 50 steps");
		log.info("Courses -> {}", courses);

		int count = repository.countByName("Java in 50 steps");
		log.info("Count Courses -> {}", count);
	}

	@Test
	public void nativeQuery() {
		List<Course> courses = repository.coursesWith100StepsInNameUsingNativeQuery();
		log.info("Courses -> {}", courses);
	}
}