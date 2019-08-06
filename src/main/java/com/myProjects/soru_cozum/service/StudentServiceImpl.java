package com.myProjects.soru_cozum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.repository.PublisherDAO;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.repository.StudentDAOImpl;
import com.myProjects.soru_cozum.request.NewRegisterRequestForStudent;
import com.myProjects.soru_cozum.response.StudentQuestionAnswerResponse;
import com.myProjects.soru_cozum.service.jsonService.StudentJSONService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{
	
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	
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
	public Student addQuestionToStudent(Student student, Question question) {
		student.addQuestionToStudent(question);
		return student;
	}
	
	@Override
	public void updateStudent(Student student) {
		studentDAO.updateStudent(student);
	}

	@Override
	public Long registerNewStudent(Student student) {
		Long studentId = studentDAO.registerNewStudent(student);
		return studentId;
	}
	
	@Override
	public Student createStudentFromRequest(NewRegisterRequestForStudent newRegisterRequest) {
		Student newStudent = new Student();
		newStudent.setName(newRegisterRequest.getStudentName());
		newStudent.setPassword(newRegisterRequest.getStudentPassword());
		newStudent.setSurname(newRegisterRequest.getSurname());
		return newStudent;
	}

	@Override
	public Question isStudentAskedThatQuestionBefore(Student student, Publisher publisher, int pageNumber,
			int questionNumber) {		
		for (Question eachStudentQuestion : student.getStudentQuestions()) {
			logger.debug("Student question: " + eachStudentQuestion);
			if (eachStudentQuestion.getPageNumber() == pageNumber && 
					eachStudentQuestion.getQuestionNumber() == questionNumber && 
					eachStudentQuestion.getPublisher().getId() == publisher.getId()) {
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
	
}
