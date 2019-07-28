package com.myProjects.soru_cozum.commandPattern;

public interface Chain<T> {
	void setNextChain(Chain nextChain);
	void process();
	T getResult();
}
