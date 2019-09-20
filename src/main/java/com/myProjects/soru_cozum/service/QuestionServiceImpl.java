package com.myProjects.soru_cozum.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.repository.QuestionDAO;

/**
 * 
 * @author mehmetozanguven
 *
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionDAO questionDAO;
	
	/**
	 * Finds Question if exists by page number and question number
	 * If Question exists, <b> return that Question </b>
	 * else <b> return Question with id 0 which means doesn't exists </b>
	 */
	@Override
	public Optional<Question> findQuestionByPageNumber_QuestionNumber_Publisher(int pageNumber, int questionNumber,
			Publisher publisher, String questionCategory, String questionSubCategory) {
		Optional<Question> question = questionDAO.findQuestionByPageNumberQuestionNumberPublisher(pageNumber, questionNumber,
				publisher, questionCategory, questionSubCategory);
		
		return question;
	}
	
	/**
	 * Find the question by Id
	 * If question doesn't exists, its id will be 0
	 */
	@Override
	public Question findQuestionById(long questionId) {
		Optional<Question> question = questionDAO.findQuestionById(questionId);
		return question.orElse(new Question(0));
	}
	
	@Override
	public QuestionImage createNewQuestionImage(byte[] imageByte) {
		QuestionImage questionImage = new QuestionImage();
		questionImage.setImage(imageByte);
		return questionImage;
	}
	
	@Override
	public Question createNewQuestionWithCommonProperties(int pageNumber, int questionNumber,
			String questionCategory, String questionSubCategory, byte[] questionImageByte) {
		
		Question newQuestion = new Question();
		newQuestion.setPageNumber(pageNumber);
		newQuestion.setQuestionNumber(questionNumber);
		newQuestion.setQuestionCategory(questionCategory);
		newQuestion.setQuestionSubCategory(questionSubCategory);
		
		QuestionImage questionImage = new QuestionImage();
		questionImage.setImage(questionImageByte);
		
		newQuestion.setQuestionImage(questionImage);
		
		return newQuestion;
	}
	
	public Question createNewQuestionWithCommonProperties_multipart(int pageNumber, int questionNumber,
			String questionCategory, String questionSubCategory) {
		
		Question newQuestion = new Question();
		newQuestion.setPageNumber(pageNumber);
		newQuestion.setQuestionNumber(questionNumber);
		newQuestion.setQuestionCategory(questionCategory);
		newQuestion.setQuestionSubCategory(questionSubCategory);
		
		return newQuestion;
	}
	
	@Override
	public void addPublisherToQuestionn(Question question, Publisher publisher) {
		question.addPublisherToQuestion(publisher);
	}
	
	
	@Override
	public List<Question> getAllNonAnsweredQuestionsBySpecificType(QuestionCategory questionCategory) {
		List<Question> allSpecificQuestions = questionDAO.getAllNonAnsweredQuestionsBySpecificType(questionCategory.getValue());
		if (allSpecificQuestions == null)
			return Arrays.asList();
		return allSpecificQuestions;
	}
	
	@Override
	public QuestionImage getQuestionImageByQuestionId(Long questionId) {
		QuestionImage questionImage = questionDAO.getQuestionImageByQuestionId(questionId);
		if (questionImage == null)
			return new QuestionImage((long) 0);
		return questionImage;
	}
	
	@Override
	public void updateQuestion(Question question) {
		questionDAO.updateQuestion(question);
	}
	
	@Override
	public Teacher findTeacherFromQuestion(Long questionId, Long teacherId) {
		Optional<Teacher> teacher = questionDAO.findTeacherFromQuestionId(questionId, teacherId);
		return teacher.orElse(new Teacher((long) 0));
	}
	
	
}
