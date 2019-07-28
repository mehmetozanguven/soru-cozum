package com.myProjects.soru_cozum.commandPattern;

public interface Command<T> {
	boolean execute();
	
	T getExecutionResult();
}
