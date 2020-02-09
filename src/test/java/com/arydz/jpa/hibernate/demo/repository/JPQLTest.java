/**
 * ------------------------------------------------------------
 * Copyright (c) PUMA SE
 * This software is the proprietary information of PUMA SE
 * All Right Reserved.
 * ------------------------------------------------------------
 */
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
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JPQLTest {

	@Autowired
	EntityManager em;

	@Test
	public void jpql_raw() {
		List resultList = em.createQuery("select c from Course c").getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_typed() {
		List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void jpql_where() {
		List<Course> resultList = em.createQuery("select c from Course c where name like '%100 steps'", Course.class).getResultList();
		log.info("List {}", resultList);
	}
}
