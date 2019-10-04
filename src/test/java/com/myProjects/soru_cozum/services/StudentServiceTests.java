package com.myProjects.soru_cozum.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.repository.StudentDAOImpl;
import com.myProjects.soru_cozum.service.StudentService;
import com.myProjects.soru_cozum.service.StudentServiceImpl;

//@RunWith(SpringRunner.class)
////@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@SpringBootTest(classes = StudentDAOImpl.class)
public class StudentServiceTests {
	
	@InjectMocks
	private StudentServiceImpl studentService;
	
	@Mock
	private StudentDAO studentDAO;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void studentFindById_test() {
		Student s1 = new Student();
		s1.setId((long)1);
		
		when(studentDAO.findStudentById((long)1)).thenReturn(Optional.of(s1));
		long found  = studentService.findStudentById((long) 1 ).get().getId();
		
		assertEquals(1, found);
	}
	
//	@InjectMocks
//	private 
//	
//	@Autowired
//	private TestEntityManager entityManager;
//	
//	@Autowired
//	private StudentDAOImpl studentDAO;
//	
//	@Test
//	public void whenFindByUsername_thenReturnEmployee() {
//		Student ozan = new Student();
//		ozan.setUsername("ozan");
//		entityManager.persist(ozan);
//		entityManager.flush();
//		
//		Optional<Student> found = studentDAO.findByUsername("ozan");
//		assertEquals(found.get().getUsername(), "ozan");
//	}

}
