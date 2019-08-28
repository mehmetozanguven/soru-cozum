package com.myProjects.soru_cozum.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.StudentDetails;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.request.NewRegisterRequestForStudent;
import com.myProjects.soru_cozum.response.StudentQuestionAnswerResponse;
import com.myProjects.soru_cozum.service.jsonService.StudentJSONService;

/***
 * 
 * @author mehmetozanguven
 *
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private StudentJSONService studentJsonService;

	@Override
	public Student findById(Long studentId) {
		Optional<Student> student = studentDAO.findStudentById(studentId);
		return student.orElse(new Student("nonce"));
	}

	@Override
	public void addQuestionToStudent(Student student, Question question) {
		student.addQuestionToStudent(question);
	}

	@Override
	public void updateStudent(Student student) {
		studentDAO.updateStudent(student);
	}

	@Override
	public void addQuestionToStudentWithoutCreatingNewQuestion(Question question, Student student) {
		student.addQuestionToStudent(question);
		studentDAO.updateStudent(student);
	}

	/**
	 * Persist the student to database
	 */
	@Override
	public Long registerNewStudent(Student student) {
		Long studentId = studentDAO.registerNewStudent(student);
		return studentId;
	}

	@Override
	public Student createNewStudent(String name, String password, String username, String surname) {
		Student newStudent = new Student();
		newStudent.setName(name);
		newStudent.setPassword(password);
		newStudent.setUsername(username);
		newStudent.setUsername(username);
		return newStudent;
	}

	@Override
	public StudentDetails createStudentDetails(String schoolName, String classNum, Department department) {
		StudentDetails newDetails = new StudentDetails();
		newDetails.setSchoolName(schoolName);
		newDetails.setClassNum(classNum);
		newDetails.setDepartment(department);
		return newDetails;
	}

	@Override
	public Question isStudentAskedThatQuestionBefore(Student student, Publisher publisher, int pageNumber,
			int questionNumber) {
		for (Question eachStudentQuestion : student.getStudentQuestions()) {
			LOGGER.debug("Student question: " + eachStudentQuestion);
			if (eachStudentQuestion.getPageNumber() == pageNumber
					&& eachStudentQuestion.getQuestionNumber() == questionNumber
					&& eachStudentQuestion.getPublisher().getId() == publisher.getId()) {
				return eachStudentQuestion;
			}
		}
		return new Question(0);
	}

	@Override
	public List<StudentQuestionAnswerResponse> getQuestionList(Student student) {
		return studentJsonService.getStudentQuestions(student);
	}

	@Override
	public List<StudentQuestionAnswerResponse> getAnswerList(Student student) {
		return studentJsonService.getStudentAnswerList(student);
	}

	@Override
	public boolean checkStudentExistsWithUsernameAndPassword(String studentName, String studentPassword) {
		return studentDAO.checkStudentExistsWithUsernameAndPassword(studentName, studentPassword);
	}

	@Override
	public Student findByUsernameAndPassword(String username, String password) {
		Optional<Student> student = studentDAO.findByUsernameAndPassword(username, password);
		return student.orElse(new Student("nonce"));
	}

	@Override
	public Optional<Student> findByUsername(String username) {
		Optional<Student> student = studentDAO.findByUsername(username);
		return student;
	}

}
