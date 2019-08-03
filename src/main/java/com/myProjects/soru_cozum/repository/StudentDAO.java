package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Student;

public interface StudentDAO {
	Optional<Student> findStudentById(Long studentId);
	
	void addQuestionToStudent(Student student, Question question);

	void updateStudent(Student student);

	Long registerNewStudent(Student student);

	boolean checkStudentExistsWithUsernameAndPassword(String studentName, String studentPassword);
	
	
}
