package com.myProjects.soru_cozum.enums;

public enum QuestionCategory {
	MAT("Matematik"),
	BIO("Biyoloji"),
	GEO("Geometri"),
	FIZ("Fizik"),
	KIM("Kimya");
	
	
	private String value;
	
	private QuestionCategory(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
