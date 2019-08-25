package com.myProjects.soru_cozum.service;

import java.util.List;
import java.util.Optional;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.request.NewRegisterRequestForStudent;
import com.myProjects.soru_cozum.response.StudentQuestionAnswerResponse;

public interface StudentService {
	
	Student findById(Long studentId);
	
	Student findByUsernameAndPassword(String username, String password);
	
	Optional<Student> findByUsername(String username);
	
	void addQuestionToStudent(Student student, Question question);
	
	void updateStudent(Student student);
	
	void addQuestionToStudentWithoutCreatingNewQuestion(Question question, Student student);
	
	Long registerNewStudent(Student student);
	
	Student createStudentFromRequest(NewRegisterRequestForStudent newRegisterRequest);
	
	Question isStudentAskedThatQuestionBefore(Student student, Publisher publisher, int pageNumber,
			int questionNumber);
	
	List<StudentQuestionAnswerResponse> getQuestionList(Student student);
	
	List<StudentQuestionAnswerResponse> getAnswerList(Student student);

	boolean checkStudentExistsWithUsernameAndPassword(String studentName, String studentPassword);
}
