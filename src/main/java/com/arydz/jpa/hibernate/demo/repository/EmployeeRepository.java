package com.arydz.jpa.hibernate.demo.repository;

import com.arydz.jpa.hibernate.demo.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class EmployeeRepository {

	@Autowired
	EntityManager em;

	public void insert(Employee employee) {
		em.persist(employee);
	}

	public List<Employee> findAllPartTimeEmployees() {
		return em.createQuery("select e from PartTimeEmployee e", Employee.class).getResultList();
	}

	public List<Employee> findAllFullTimeEmployee() {
		return em.createQuery("select e from FullTimeEmployee e", Employee.class).getResultList();
	}

	// Will works with entity inheritance
//	public List<Employee> findAll() {
//		return em.createQuery("select e from Employee e", Employee.class).getResultList();
//	}
}
