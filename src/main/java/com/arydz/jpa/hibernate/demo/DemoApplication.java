package com.arydz.jpa.hibernate.demo;

import com.arydz.jpa.hibernate.demo.entity.Course;
import com.arydz.jpa.hibernate.demo.entity.Review;
import com.arydz.jpa.hibernate.demo.entity.Student;
import com.arydz.jpa.hibernate.demo.repository.CourseRepository;
import com.arydz.jpa.hibernate.demo.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Student student = new Student("Jack");
		Course course = new Course("Microservices in 100 steps");

		repository.insertStudentAndCourse(student, course);
	}
}
