package com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image;

import com.myProjects.soru_cozum.response.AnswerImageResponse;

public abstract class FileAnswerImageStorageAbstractHandler {
	private FileAnswerImageStorageAbstractHandler nextHandler;

	public FileAnswerImageStorageAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(FileAnswerImageStorageAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public abstract AnswerImageResponse handle(FileAnswerImageStorageRequest request);
}
