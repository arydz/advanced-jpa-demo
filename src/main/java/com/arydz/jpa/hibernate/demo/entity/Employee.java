package com.arydz.jpa.hibernate.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
//@Entity
// ** InheritanceType.SINGLE_TABLE is default strategy. Data is stored in one table. From the performance perspective it's really efficient way
// because we are getting details from one table.
// But when we look at it from data integrity, both columns (salary and hourlyWage) are nullable from obvious reasons. So if there would be there
// some defects in code, there is chance that there is invalid data that can be inserted in!
// There is also added additional column DTYPE, which says what kind of employee is being stored.
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "EmployeeType") // If we want to change DTYPE column name, use this annotation
// ** Using this strategy, doesn't force us to change inserting or retrieving data from DB. We can use the same way like with Single Table strategy.
// Union from all children tables being created when retrieving data.
// Problem of it is that, common columns (id and name) are repeated. If there would more common columns it might become inefficient.
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// ** Common columns are stored in one table and columns specified to entity will be stored in appropriate table.
// It's really good option in terms of database design. From performance perspective is not the best solution, because it has to join 3 tables to
// get all details.
//@Inheritance(strategy = InheritanceType.JOINED)
// ** Last option is not to use inheritance. We can use this annotation @MappedSuperclass. Then we have to remove @Entity annotation.
// Mapping is applied to subclasses.
// Mapping totally removes inheritance relationship between those children classes. Then selecting from db will be base on separate queries.
// So there is no union like in TABLE_PER_CLASS strategy. It's less flexible then inheritance JOINED or TABLE_PER_CLASS strategies.
@MappedSuperclass
//** SUMMARY
// If we are concerned about data integrity and data quality matter a lot for you then Join strategy is recommended. That because each column is
// present only once and we are linking everything by using foreign key.
// If we are worry about performance then Single strategy is recommended. But it affects worse data integrity.
// Table per class strategy and Mapping superclass might be not worth to be considerate, because they are causing repeating columns and it's not
// good table design.
public abstract class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	protected Employee() {
	}

	public Employee(String name) {
		this.name = name;
	}
}
