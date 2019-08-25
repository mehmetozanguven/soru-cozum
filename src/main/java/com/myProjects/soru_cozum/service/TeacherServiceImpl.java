package com.myProjects.soru_cozum.service;

import java.io.File;
import java.io.FileInputStream;
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

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.model.json.PublisherJSON;
import com.myProjects.soru_cozum.model.json.QuestionJSON;
import com.myProjects.soru_cozum.model.json.StudentJSON;
import com.myProjects.soru_cozum.repository.AnswerDAO;
import com.myProjects.soru_cozum.repository.TeacherDAO;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.request.NewRegisterRequestForTeacher;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
	private final static Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
	
	@Autowired
	private TeacherDAO teacherDAO;
	
	@Autowired
	private AnswerDAO answerDAO;
	
	@Override
	public Teacher findTeacherById(long teacherId) {
		Optional<Teacher> teacher = teacherDAO.findTeacherById(teacherId);
		return teacher.orElse(new Teacher((long) 0));
	}
	
	@Override
	public void resolveTeacherAccordingToAnswerQuestion(Teacher teacher, Question question,
			AnswerQuestionRequest answerQuestionRequest) {
		tempConvertStringToImageByte(answerQuestionRequest);
		tempConvertStringToAudioByte(answerQuestionRequest);
		
		AnswerImage answerImage = new AnswerImage();
		answerImage.setImage(answerQuestionRequest.getImageByte());
		answerImage.setAssociatedQuestionId(answerQuestionRequest.getQuestionId());
		
		AnswerAudio answerAudio = new AnswerAudio();
		answerAudio.setImage(answerQuestionRequest.getAudioByte());
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
	public Teacher createTeacherFromRequest(NewRegisterRequestForTeacher registerRequest) {
		Teacher newTeacher = new Teacher();
		newTeacher.setName(registerRequest.getName());
		newTeacher.setPassword(registerRequest.getPassword());
		newTeacher.setUsername(registerRequest.getUsername());

		return newTeacher;
	}
	
	@Override
	public void registerNewTeacher(Teacher teacher) {
		teacherDAO.registerNewTeacher(teacher);
	}
	
	@Override
	public AnswerImage getAnswerImageFromTeacher(Long teacherId, Long questionId) {
		Optional<AnswerImage> answerImage = teacherDAO.getAnswerImageFromTeacher(teacherId, questionId);
		return answerImage.orElse(new AnswerImage((long) 0));
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
