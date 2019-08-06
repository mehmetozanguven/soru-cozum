package com.myProjects.soru_cozum.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.model.json.PublisherJSON;
import com.myProjects.soru_cozum.model.json.QuestionJSON;
import com.myProjects.soru_cozum.model.json.StudentJSON;
import com.myProjects.soru_cozum.repository.TeacherDAO;
import com.myProjects.soru_cozum.request.AddQuestionToStudentRequest;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.request.NewRegisterRequestForTeacher;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
	@Autowired
	private TeacherDAO teacherDAO;
	
	@Override
	public Teacher getTeacherById(long teacherId) {
		Optional<Teacher> teacher = teacherDAO.getTeacherById(teacherId);
		return teacher.orElse(new Teacher((long) 0));
	}
	
	@Override
	public void resolveTeacherAccordingToAnswerQuestion(Teacher teacher, Question question,
			AnswerQuestionRequest answerQuestionRequest) {
		tempConvertStringToImageByte(answerQuestionRequest);
		tempConvertStringToAudioByte(answerQuestionRequest);
		
		AnswerImage answerImage = new AnswerImage();
		answerImage.setImage(answerQuestionRequest.getImageByte());
		
		AnswerAudio answerAudio = new AnswerAudio();
		answerAudio.setImage(answerQuestionRequest.getAudioByte());
		
		teacher.addImageToTeacher(answerImage);
		teacher.addAudioToTeacher(answerAudio);
	}
	
	@Override
	public List<AnsweredQuestionJSON> getAnsweredQuestionByTeacherId(Teacher teacher) {
		List<AnsweredQuestionJSON> resultList = new ArrayList<AnsweredQuestionJSON>();
		for (Question eachQuestion : teacher.getQuestionSet()) {
			Publisher publisher = eachQuestion.getPublisher();
			PublisherJSON publisherJSON = new PublisherJSON(publisher.getId(), publisher.getName(), publisher.getPublishYear());
			
			QuestionJSON questionJSON = new QuestionJSON(eachQuestion.getId(), eachQuestion.getQuestionImage().getId(),
					eachQuestion.isAnswered());
			
			Set<StudentJSON> setOfStudents = eachQuestion.getStudentList()
					.stream().map(elem -> {
						StudentJSON student = new StudentJSON(elem.getName(), elem.getSurname());
						return student;
					}).collect(Collectors.toSet());
			AnsweredQuestionJSON answeredQuestionJSON = new AnsweredQuestionJSON(publisherJSON, setOfStudents,
					questionJSON, eachQuestion.getQuestionNumber(), eachQuestion.getPageNumber());
			resultList.add(answeredQuestionJSON);
		}
		return resultList;
	}
	
	
	@Override
	public boolean checksTeacherExistsWithUsernameAndPassword(String name, String password) {
		boolean isTeacherExists = teacherDAO.checksTeacherExistsWithUsernameAndPassword(name, password);
		return isTeacherExists;
	}
	
	@Override
	public Teacher createTeacherFromRequest(String teacherName, String teacherPassword) {
		Teacher newTeacher = new Teacher();
		newTeacher.setName(teacherName);
		newTeacher.setPassword(teacherPassword);
		return newTeacher;
	}
	
	@Override
	public void registerNewTeacher(Teacher teacher) {
		teacherDAO.registerNewTeacher(teacher);
	}
	
	
	/// TEMPORAL METHODS /////
	
	private void tempConvertStringToImageByte(AnswerQuestionRequest answerQuestionRequest) {
		File file = new File(answerQuestionRequest.getImageFilePath());
        byte[] bFile = new byte[(int) file.length()];
        
        try {
	     FileInputStream fileInputStream = new FileInputStream(file);
	     //convert file into array of bytes
	     fileInputStream.read(bFile);
	     fileInputStream.close();
        } catch (Exception e) {
	     e.printStackTrace();
        }
        
        answerQuestionRequest.setImageByte(bFile);
	}
	
	private void tempConvertStringToAudioByte(AnswerQuestionRequest answerQuestionRequest) {
		File file = new File(answerQuestionRequest.getAudioFilePath());
        byte[] bFile = new byte[(int) file.length()];
        
        try {
	     FileInputStream fileInputStream = new FileInputStream(file);
	     //convert file into array of bytes
	     fileInputStream.read(bFile);
	     fileInputStream.close();
        } catch (Exception e) {
	     e.printStackTrace();
        }
        
        answerQuestionRequest.setAudioByte(bFile);
	}
	
	
}
