package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Teacher;

public interface TeacherDAO {

	Optional<Teacher> findTeacherById(long teacherId);

	void registerNewTeacher(Teacher teacher);

	Optional<AnswerImage> getAnswerImageFromTeacher(Long teacherId, Long questionId);

	void updateTeacher(Teacher teacher);
	
	public Optional<Teacher> findTeacherByUsername(String username);

}
