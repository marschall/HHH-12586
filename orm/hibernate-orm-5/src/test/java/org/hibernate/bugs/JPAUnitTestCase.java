package org.hibernate.bugs;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.bugs.hhh12586.JavaTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		this.entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh12586Test() throws Exception {
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();


		TypedQuery<JavaTime> query = entityManager.createQuery("SELECT t FROM JavaTime t ORDER BY t.id ASC", JavaTime.class);
		List<JavaTime> resultList = query.getResultList();
		assertEquals(2, resultList.size());

		// validate the entity inserted by SQL
		JavaTime javaTime = resultList.get(0);
		assertEquals(LocalTime.parse("15:09:02"), javaTime.getLocalTime());
		assertEquals(LocalDate.parse("1988-12-25"), javaTime.getLocalDate());
		assertEquals(LocalDateTime.parse("1980-01-01T23:03:20.123456789"), javaTime.getLocalDateTime());


		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
