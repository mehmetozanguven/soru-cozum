package com.myProjects.soru_cozum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.repository.PublisherDAO;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.repository.StudentDAOImpl;
import com.myProjects.soru_cozum.request.NewRegisterRequest;
import com.myProjects.soru_cozum.service.jsonService.StudentJSONService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{
	
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
	public Student createStudentFromRequest(NewRegisterRequest newRegisterRequest) {
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
			if (eachStudentQuestion.getPageNumber() == pageNumber && 
					eachStudentQuestion.getQuestionNumber() == questionNumber && 
					eachStudentQuestion.getPublisher().getId() == publisher.getId()) {
				return eachStudentQuestion;
			}
				
		}
		return new Question(0);
	}

	@Override
	public List<Question> getQuestionList(Student student) {
		return student.getStudentQuestions();
	}

	@Override
	public List<Question> getAnswerList(Student student) {
		List<Question> answerList = student.getStudentQuestions().stream()
			.filter(elem -> elem.isAnswered()).collect(Collectors.toCollection(ArrayList::new));
		return answerList;
	}
	
}
