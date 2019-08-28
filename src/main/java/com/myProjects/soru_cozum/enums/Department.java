package com.myProjects.soru_cozum.enums;

public enum Department {
	SAY("Sayısal"),
	SOZ("Sözel"),
	EST("Eşit Ağırlık"),
	DIL("Yabancı Dil");
	
	private String value;
	
	private Department(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
}
