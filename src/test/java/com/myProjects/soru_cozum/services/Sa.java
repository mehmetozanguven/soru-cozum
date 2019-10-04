package com.myProjects.soru_cozum.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.service.StudentServiceImpl;

public class Sa {
	@InjectMocks
	private StudentServiceImpl service;
	
	@Mock
	private StudentDAO studentDAO;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllStudents() {
		Student s1 = new Student();
		s1.setUsername("ozan");
		
		when(studentDAO.findByUsername("ozan")).thenReturn(Optional.of(s1));
		
		assertEquals("ozan", service.findByUsername("ozan").get().getUsername());
	}
}
