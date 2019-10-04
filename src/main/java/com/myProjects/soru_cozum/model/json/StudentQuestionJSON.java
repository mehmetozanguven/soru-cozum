package com.myProjects.soru_cozum.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myProjects.soru_cozum.model.json.student.AnswerAudioJSON;
import com.myProjects.soru_cozum.model.json.student.AnswerImageJSON;
import com.myProjects.soru_cozum.model.json.student.QuestionImageJSON;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentQuestionJSON {

	@JsonAlias("questionJson")
	private QuestionImageJSON questionImageJSON;

	@JsonAlias("answerImageJSONList")
	private List<AnswerImageJSON> answerImageJSONList;

	@JsonAlias("answerAudioJSONList")
	private List<AnswerAudioJSON> answerAudioJSONList;

	public StudentQuestionJSON(QuestionImageJSON questionImageJSON, List<AnswerImageJSON> answerImageJSONList,
			List<AnswerAudioJSON> answerAudioJSONList) {
		this.questionImageJSON = questionImageJSON;
		this.answerImageJSONList = answerImageJSONList;
		this.answerAudioJSONList = answerAudioJSONList;
	}

	public QuestionImageJSON getQuestionImageJSON() {
		return questionImageJSON;
	}

	public void setQuestionImageJSON(QuestionImageJSON questionImageJSON) {
		this.questionImageJSON = questionImageJSON;
	}

	public List<AnswerImageJSON> getAnswerImageJSONList() {
		return answerImageJSONList;
	}

	public void setAnswerImageJSONList(List<AnswerImageJSON> answerImageJSONList) {
		this.answerImageJSONList = answerImageJSONList;
	}

	public List<AnswerAudioJSON> getAnswerAudioJSONList() {
		return answerAudioJSONList;
	}

	public void setAnswerAudioJSONList(List<AnswerAudioJSON> answerAudioJSONList) {
		this.answerAudioJSONList = answerAudioJSONList;
	}

}
