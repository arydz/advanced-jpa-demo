package com.arydz.jpa.hibernate.demo;

import com.arydz.jpa.hibernate.demo.entity.FullTimeEmployee;
import com.arydz.jpa.hibernate.demo.entity.PartTimeEmployee;
import com.arydz.jpa.hibernate.demo.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private EmployeeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.insert(new FullTimeEmployee("Jan", new BigDecimal("10000")));
		repository.insert(new PartTimeEmployee("Julia", new BigDecimal("50")));

		log.info("All FullTimeEmployee {}", repository.findAllFullTimeEmployee());
		log.info("All PartTimeEmployee {}", repository.findAllPartTimeEmployees());
	}
}
