package com.myProjects.soru_cozum.model.json;

public class QuestionJSON {
	private long questionId;
	private long questionImageId;
	private boolean isAnswered;
	
	public QuestionJSON(long questionId, long questionImageId, boolean isAnswered) {
		super();
		this.questionId = questionId;
		this.questionImageId = questionImageId;
		this.isAnswered = isAnswered;
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

	public boolean isAnswered() {
		return isAnswered;
	}

	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	
	
	
}
