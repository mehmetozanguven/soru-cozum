package com.myProjects.soru_cozum.service;

import java.util.List;
import java.util.Optional;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.TeacherDetails;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;

public interface TeacherService {

	Optional<Teacher> findTeacherById(long teacherId);

	List<AnsweredQuestionJSON> getAnsweredQuestionByTeacherId(Teacher teacher);

	Optional<Teacher> findTeacherByUsername(String username);

	void registerNewTeacher(Teacher teacher);

	void updateTeacher(Teacher teacher);

	TeacherDetails createTeacherDetails(String schoolName, Department department);

	Teacher createNewTeacher(String name, String password, String username, String surname);

	Optional<AnswerImage> findAnswerImageFromTeacher(Long teacherId, Long questionId);

	Optional<AnswerAudio> findAnswerAudioFromTeacher(Long teacherId, Long questionId);

	void updateTeacherAnswerImage(Teacher teacher, AnswerImage oldAnswerImage, AnswerImage newAnswerImage);

	void updateTeacherAnswerAudio(Teacher teacher, AnswerAudio oldAnswerAudio, AnswerAudio newAnswerAudio);
}
