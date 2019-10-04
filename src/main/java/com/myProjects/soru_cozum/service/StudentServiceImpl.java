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
import com.myProjects.soru_cozum.model.json.StudentQuestionJSON;
import com.myProjects.soru_cozum.repository.StudentDAO;
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
	public Optional<Student> findStudentById(Long studentId) {
		Optional<Student> student = studentDAO.findStudentById(studentId);
		return student;
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
		newStudent.setSurname(surname);
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
	public Optional<Question> isStudentAskedThatQuestionBefore(Student student, Publisher publisher, int pageNumber,
			int questionNumber, String questionCategory, String questionSubCategory) {
		Question result = null;
		
		for (Question eachStudentQuestion : student.getStudentQuestions()) {
			LOGGER.debug("Student question: " + eachStudentQuestion);
			if (eachStudentQuestion.getPageNumber() == pageNumber
					&& eachStudentQuestion.getQuestionNumber() == questionNumber
					&& eachStudentQuestion.getPublisher().getId() == publisher.getId()
					&& eachStudentQuestion.getQuestionCategory().equalsIgnoreCase(questionCategory)) {
				if (questionSubCategory != "" && questionSubCategory != null && questionSubCategory.equalsIgnoreCase(eachStudentQuestion.getQuestionSubCategory())) {
					result = eachStudentQuestion;
					break;
				}
				result = eachStudentQuestion;
				break;
			}
		}
		return Optional.ofNullable(result);
	}

	@Override
	public List<StudentQuestionJSON> getQuestionList_new(Student student) {
		return studentJsonService.getStudentQuestions_new(student);
	}

	@Override
	public List<StudentQuestionJSON> getStudentAnswerList(Student student) {
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
