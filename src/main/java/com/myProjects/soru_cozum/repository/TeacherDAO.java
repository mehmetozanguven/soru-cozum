package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Teacher;

public interface TeacherDAO {

	Optional<Teacher> findTeacherById(long teacherId);

	void registerNewTeacher(Teacher teacher);


	void updateTeacher(Teacher teacher);
	
	public Optional<Teacher> findTeacherByUsername(String username);
	
	Optional<AnswerImage> findAnswerImageFromTeacher(Long teacherId, Long questionId);

	Optional<AnswerAudio> findAnswerAudioFromTeacher(Long teacherId, Long questionId);

}
