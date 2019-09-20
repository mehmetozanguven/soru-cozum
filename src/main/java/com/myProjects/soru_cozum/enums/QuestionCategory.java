package com.myProjects.soru_cozum.enums;

import java.util.HashMap;
import java.util.Map;

public enum QuestionCategory {
	MAT("Matematik"), BIO("Biyoloji"), GEO("Geometri"), FIZ("Fizik"), KIM("Kimya");

	private String value;

	// Reverse-lookup map
	private static final Map<String, QuestionCategory> lookUp = new HashMap<String, QuestionCategory>();

	static {
		for (QuestionCategory eachCategory : QuestionCategory.values())
			lookUp.put(eachCategory.getValue(), eachCategory);
	}

	private QuestionCategory(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static QuestionCategory questionCategoryFromValue(String value){
		return lookUp.get(value);
	}
}
