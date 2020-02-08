package com.arydz.jpa.hibernate.demo;

import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		long id = 10001L;
		Course course = courseRepository.findById(id);
		log.info("{}", course);

		courseRepository.save(new Course("Micro-services in 100 steps"));
	}
}
