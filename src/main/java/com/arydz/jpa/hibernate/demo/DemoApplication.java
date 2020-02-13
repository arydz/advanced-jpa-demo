package com.arydz.jpa.hibernate.demo;

import com.arydz.jpa.hibernate.demo.entity.Review;
import com.arydz.jpa.hibernate.demo.repository.CourseRepository;
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
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Review> reviewList = new ArrayList<>();
		Review reviewOne = new Review("3", "Could be better done!");
		Review reviewTwo = new Review("2", "Waste of time!");
		reviewList.add(reviewOne);
		reviewList.add(reviewTwo);

		courseRepository.addReviewsForCourse(10003L, reviewList);
	}
}
