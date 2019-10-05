package com.myProjects.soru_cozum.service;

import java.util.List;
import java.util.Optional;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.StudentDetails;
import com.myProjects.soru_cozum.model.json.StudentQuestionJSON;

public interface StudentService {

	Optional<Student> findStudentById(Long studentId);

	Optional<Student> findByUsername(String username);

	void addQuestionToStudent(Student student, Question question);

	void updateStudent(Student student);

	void addQuestionToStudentWithoutCreatingNewQuestion(Question question, Student student);

	Long registerNewStudent(Student student);

	Optional<Question> isStudentAskedThatQuestionBefore(Student student, Publisher publisher, int pageNumber,
			int questionNumber, String questionCategory, String questionSubCategory);

	List<StudentQuestionJSON> getStudentAnswerList(Student student);

	boolean checkStudentExistsWithUsernameAndPassword(String studentName, String studentPassword);

	StudentDetails createStudentDetails(String schoolName, String classNum, Department department);

	Student createNewStudent(String name, String password, String username, String surname);

	List<StudentQuestionJSON> getQuestionList_new(Student student);
}
