package com.myProjects.soru_cozum.service;

import java.util.List;
import java.util.Optional;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.TeacherDetails;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.request.NewRegisterRequestForTeacher;

public interface TeacherService {
	
	Teacher findTeacherById(long teacherId);

	void resolveTeacherAccordingToAnswerQuestion(Teacher teacher, Question question,
			AnswerQuestionRequest answerQuestionRequest);
	
	List<AnsweredQuestionJSON> getAnsweredQuestionByTeacherId(Teacher teacher);

	Optional<Teacher> findTeacherByUsername(String username);


	void registerNewTeacher(Teacher teacher);

	AnswerImage getAnswerImageFromTeacher(Long teacherId, Long questionId);

	void updateTeacher(Teacher teacher);

	void updateTeacherAnswerImage(Teacher teacher, AnswerImage oldAnswerImage, AnswerImage newAnswerImage);

	TeacherDetails createTeacherDetails(String schoolName, Department department);

	Teacher createNewTeacher(String name, String password, String username, String surname);
}
