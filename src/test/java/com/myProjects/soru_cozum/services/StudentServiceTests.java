package com.myProjects.soru_cozum.services;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.StudentDetails;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.repository.StudentDAOImpl;
import com.myProjects.soru_cozum.service.StudentService;
import com.myProjects.soru_cozum.service.StudentServiceImpl;

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
	
	@Test
	public void studentFindByUsername_test() {
		Student s1 = new Student();
		s1.setUsername("ozan");
		
		when(studentDAO.findByUsername("ozan")).thenReturn(Optional.of(s1));
		String foundUsername = studentService.findByUsername("ozan").get().getUsername();
		
		assertEquals("ozan", foundUsername);
	}
	
	@Test
	public void registerNewStudent_test() {
		Student newStudent = new Student();
		newStudent.setId((long)2);
		
		when(studentDAO.registerNewStudent(newStudent)).thenReturn((long)newStudent.getId());
		long foundId = studentService.registerNewStudent(newStudent);
		assertEquals(2, foundId);
	}
	
	@Test
	public void addQuestionToStudent_test() {
		Student student = new Student();
		student.setId((long) 1);
		Question question = new Question();
		question.setId((long)1);
		
		studentService.addQuestionToStudent(student, question);
		
		assertEquals((int)1, student.getStudentQuestions().size());
	}
	
	@Test
	public void createNewStudentDetails_test() {
		String schoolName = "IYTE";
		String classNumber = "5";
		Department department = Department.DIL;
		StudentDetails newStudentDetails = studentService.createStudentDetails(schoolName, classNumber, department);
		
		Object[] expecteds = {schoolName, classNumber, department.getValue()};
		Object[] actuals = {newStudentDetails.getSchoolName(), newStudentDetails.getClassNum(), newStudentDetails.getDepartment()};
		assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void isStudentAskedThatQuestionBefore_succcessTest() {
		int pageNumber = 5;
		int questionNumber = 10;
		String questionCategory = "Matematik";
		String questionSubCategory = "Türev";
		Publisher tempPublisher = new Publisher();
		tempPublisher.setId((long)1);
		Question tempQuestion = createTempQuestion(pageNumber, questionNumber, questionCategory, questionSubCategory,
				tempPublisher);
		
		Student student = new Student();
		student.setId((long) 1);
		student.addQuestionToStudent(tempQuestion);
		
		Optional<Question> found = studentService.isStudentAskedThatQuestionBefore(student, tempPublisher, pageNumber, questionNumber, questionCategory, questionSubCategory);
		
		assertEquals(student.getId(), found.get().getId());
	}
	
	@Test
	public void isStudentAskedThatQuestionBefore_unsucccessTest() {
		int pageNumber = 5;
		int questionNumber = 10;
		String questionCategory = "Matematik";
		String questionSubCategory = "Türev";
		Publisher tempPublisher = new Publisher();
		tempPublisher.setId((long)1);
		Question tempQuestion = createTempQuestion(pageNumber, questionNumber, questionCategory, questionSubCategory,
				tempPublisher);
		
		Student student = new Student();
		student.setId((long) 1);
		
		Optional<Question> found = studentService.isStudentAskedThatQuestionBefore(student, tempPublisher, pageNumber, questionNumber, questionCategory, questionSubCategory);
		
		assertNotEquals(student.getId(), found.get().getId());
	}

	private Question createTempQuestion(int pageNumber, int questionNumber, String questionCategory,
			String questionSubCategory, Publisher tempPublisher) {
		Question tempQuestion = new Question();
		tempQuestion.setId((long)1);
		tempQuestion.addPublisherToQuestion(tempPublisher);
		tempQuestion.setPageNumber(pageNumber);
		tempQuestion.setQuestionNumber(questionNumber);
		tempQuestion.setQuestionCategory(questionCategory);
		tempQuestion.setQuestionSubCategory(questionSubCategory);
		return tempQuestion;
	}
}
