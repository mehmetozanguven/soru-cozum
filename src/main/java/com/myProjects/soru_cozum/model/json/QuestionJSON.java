package com.myProjects.soru_cozum.model.json;

public class QuestionJSON {
	private long questionId;
	private long questionImageId;
	
	public QuestionJSON(long questionId, long questionImageId) {
		super();
		this.questionId = questionId;
		this.questionImageId = questionImageId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public long getQuestionImageId() {
		return questionImageId;
	}

	public void setQuestionImageId(long questionImageId) {
		this.questionImageId = questionImageId;
	}
	
	
	
}
