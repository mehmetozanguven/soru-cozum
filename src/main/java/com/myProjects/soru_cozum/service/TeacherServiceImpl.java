package com.myProjects.soru_cozum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.enums.Department;
import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.TeacherDetails;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.model.json.PublisherJSON;
import com.myProjects.soru_cozum.model.json.QuestionJSON;
import com.myProjects.soru_cozum.model.json.StudentJSON;
import com.myProjects.soru_cozum.repository.AnswerDAO;
import com.myProjects.soru_cozum.repository.TeacherDAO;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
	private final static Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
	
	@Autowired
	private TeacherDAO teacherDAO;
	
	@Autowired
	private AnswerDAO answerDAO;
	
	@Override
	public Optional<Teacher> findTeacherById(long teacherId) {
		return teacherDAO.findTeacherById(teacherId);
	}
	
	@Override
	public void resolveTeacherAccordingToAnswerQuestion(Teacher teacher, Question question,
			AnswerQuestionRequest answerQuestionRequest) {
	/*	tempConvertStringToImageByte(answerQuestionRequest);
		tempConvertStringToAudioByte(answerQuestionRequest);*/
		
		AnswerImage answerImage = new AnswerImage();
		answerImage.setImage(answerQuestionRequest.getImageByte());
		answerImage.setAssociatedQuestionId(answerQuestionRequest.getQuestionId());
		
		AnswerAudio answerAudio = new AnswerAudio();
		answerAudio.setAudio(answerQuestionRequest.getAudioByte());
		answerAudio.setAssociatedQuestionId(answerQuestionRequest.getQuestionId());
		
		teacher.addImageToTeacher(answerImage);
		teacher.addAudioToTeacher(answerAudio);
	}
	
	@Override
	public List<AnsweredQuestionJSON> getAnsweredQuestionByTeacherId(Teacher teacher) {
		List<AnsweredQuestionJSON> resultList = new ArrayList<AnsweredQuestionJSON>();
		for (Question eachQuestion : teacher.getQuestionSet()) {
			Publisher publisher = eachQuestion.getPublisher();
			PublisherJSON publisherJSON = new PublisherJSON(publisher.getId(), publisher.getName(), publisher.getPublishYear());
			LOGGER.debug("Answered list: " + teacher.getAnswerImageSet().size());
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
	public Optional<Teacher> findTeacherByUsername(String username) {
		Optional<Teacher> teacher = teacherDAO.findTeacherByUsername(username);
		return teacher;
	}
	
	@Override
	public void registerNewTeacher(Teacher teacher) {
		teacherDAO.registerNewTeacher(teacher);
	}
	
	@Override
	public Optional<AnswerImage> findAnswerImageFromTeacher(Long teacherId, Long questionId) {
		return teacherDAO.findAnswerImageFromTeacher(teacherId, questionId);
	}
	
	@Override
	public Optional<AnswerAudio> findAnswerAudioFromTeacher(Long teacherId, Long questionId) {
		return teacherDAO.findAnswerAudioFromTeacher(teacherId, questionId);
	}
	
	@Override
	public void updateTeacher(Teacher teacher) {
		teacherDAO.updateTeacher(teacher);	
	}
	
	@Override
	public void updateTeacherAnswerImage(Teacher teacher, AnswerImage oldAnswerImage, AnswerImage newAnswerImage) {
		teacher.updateAnswerImage(oldAnswerImage, newAnswerImage);
		answerDAO.deleteAnswerImage(oldAnswerImage);
		teacherDAO.updateTeacher(teacher);
	}
	
	@Override
	public void updateTeacherAnswerAudio(Teacher teacher, AnswerAudio oldAnswerAudio, AnswerAudio newAnswerAudio) {
		teacher.updateAnswerAudio(oldAnswerAudio, newAnswerAudio);
		answerDAO.deleteAnswerAudio(oldAnswerAudio);
		teacherDAO.updateTeacher(teacher);
	}

	@Override
	public Teacher createNewTeacher(String name, String password, String username, String surname) {
		Teacher newTeacher = new Teacher();
		newTeacher.setName(name);
		newTeacher.setPassword(password);
		newTeacher.setUsername(username);
		newTeacher.setSurname(surname);
		return newTeacher;
	}
	
	@Override
	public TeacherDetails createTeacherDetails(String schoolName, Department department) {
		TeacherDetails newDetails = new TeacherDetails();
		newDetails.setSchoolName(schoolName);
		newDetails.setTeacherDepartment(department);
		return newDetails;
	}

	
	
	
	
	/*
	
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
	*/
	
}
