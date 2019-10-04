package com.myProjects.soru_cozum.request;



import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;

public class AddQuestionRequest {

	@JsonAlias("questionUrl")
	private String questionUrl;
	

	public String getQuestionUrl() {
		return questionUrl;
	}

	public void setUrl(String url) {
		this.questionUrl = url;
	}
	
	
	
	
	
}
