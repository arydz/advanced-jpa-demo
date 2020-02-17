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
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * This test contains very simple test scenario. We would use JPQL for them.
 * It's good to use native queries when:
 * - you have to set tuning parameters
 * - you want to use some specific sql feature (not supported by JPQL)
 * - you have to do mass update (you have to update all rows in same way). When using JPQL, row is received from DB and then updated.
 *
 *  disadvantage of native queries is :
 *  - complexity of result binding. You will have to declare an entity to which you can map your native query,
 *  	or define a complex result set mapped to multiple entities or to a blend of entities and scalar results.
 *  - your Java code becomes directly dependent on the underlying database structure (To work around this problem, while still using native
 *  queries, you might take advantage of stored procedures, moving complex SQL queries into programs stored and executed inside the database,
 *  and then calling those stored programs instead of making direct calls to the underlying tables)
 *
 * When using Native Query we are not making use of Persistence Context.
 * If we have entities directly present in persistence context then we need to refresh them to get newest data from DB.
 *
 * https://vladmihalcea.com/how-does-the-auto-flush-work-in-jpa-and-hibernate/
 * When using Native Query we are not making use of Persistence Context.
 * If we have entities directly present in persistence context then we need to refresh them to get newest data from DB.

 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class NativeQueriesTest {

	@Autowired
	EntityManager em;

	@Test
	public void nativeQueries_test() {
		List resultList = em.createNativeQuery("select * from Course", Course.class).getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void nativeQueriesWithParameters_test() {
		Query nativeQuery = em.createNativeQuery("select * from Course where id=?", Course.class);
		nativeQuery.setParameter(1, 10001);
		List resultList = nativeQuery.getResultList();
		log.info("List {}", resultList);
	}

	@Test
	public void nativeQueriesWithNamedParameters_test() {
		Query nativeQuery = em.createNativeQuery("select * from Course where id=:id", Course.class);
		nativeQuery.setParameter("id", 10001);
		List resultList = nativeQuery.getResultList();
		log.info("List {}", resultList);
	}

	// Doing it through JPA, would be inefficient. It had to be done by getting each row and updating it separately (row by row)
	@Test
	@Transactional
	public void nativeQueriesUpdate_test() {
		Query nativeQuery = em.createNativeQuery("update Course set last_updated_date=sysdate()", Course.class);
		int rowsAffected = nativeQuery.executeUpdate();
		log.info("Rows affected {}", rowsAffected);
	}
}
