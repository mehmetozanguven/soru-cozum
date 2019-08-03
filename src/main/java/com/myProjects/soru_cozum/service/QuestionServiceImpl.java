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
import com.myProjects.soru_cozum.repository.QuestionDAO;
import com.myProjects.soru_cozum.request.AddQuestionToStudentRequest;

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
	public Question findQuestionByPageNumberQuestionNumberPublisher(int pageNumber, int questionNumber,
			Publisher publisher) {
		Optional<Question> question = questionDAO.findQuestionByPageNumberQuestionNumberPublisher(pageNumber, questionNumber,
				publisher);
		
		return question.orElse(new Question(0));
	}
	
	@Override
	public QuestionImage createNewQuestionImage(byte[] imageByte) {
		QuestionImage questionImage = new QuestionImage();
		questionImage.setImage(imageByte);
		return questionImage;
	}
	
	@Override
	public Question addQuestionImageToQuestion(Question question, QuestionImage questionImage) {
		question.setQuestionImage(questionImage);
		return question;
	}
	
	@Override
	public Question addPublisherToQuestion(Question question, Publisher publisher) {
		question.addPublisherToQuestion(publisher);
		return question;
	}

	@Override
	public Question addPageNumberToQuestion(Question question, int pageNumber) {
		question.setPageNumber(pageNumber);
		return question;
	}

	@Override
	public Question addQuestionNumberToQuestion(Question question, int questionNumber) {
		question.setQuestionNumber(questionNumber);
		return question;
	}

	@Override
	public Question addIsAnsweredProperty(Question question, boolean isAnswered) {
		question.setAnswered(isAnswered);
		return question;
	}
	
	@Override
	public Question addQuestionCategory(Question question, QuestionCategory questionCategory) {
		question.setQuestionCategory(questionCategory);
		return question;
	}
	
	@Override
	public Question addQuestionSubCategory(Question question, String questionSubCategory) {
		question.setQuestionSubCategory(questionSubCategory);
		return question;
	}
	
	@Override
	public List<Question> getAllQuestionsBySpecificType(QuestionCategory questionCategory) {
		List<Question> allSpecificQuestions = questionDAO.getAllQuestionsBySpecificType(questionCategory.getValue());
		if (allSpecificQuestions == null)
			return Arrays.asList();
		return allSpecificQuestions;
	}
}
