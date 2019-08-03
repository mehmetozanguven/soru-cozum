package com.myProjects.soru_cozum.response;

import java.util.List;

import com.myProjects.soru_cozum.model.json.PublisherJSON;
import com.myProjects.soru_cozum.model.json.QuestionJSON;


public class StudentQuestionAnswerResponse {
	private QuestionJSON questionJSON;
	private PublisherJSON publisherJSON;
	public StudentQuestionAnswerResponse(QuestionJSON questionJSON, PublisherJSON publisherJSON) {
		super();
		this.questionJSON = questionJSON;
		this.publisherJSON = publisherJSON;
		
	}
	public QuestionJSON getQuestionJSON() {
		return questionJSON;
	}
	public void setQuestionJSON(QuestionJSON questionJSON) {
		this.questionJSON = questionJSON;
	}
	public PublisherJSON getPublisherJSON() {
		return publisherJSON;
	}
	public void setPublisherJSON(PublisherJSON publisherJSON) {
		this.publisherJSON = publisherJSON;
	}	
	
}
