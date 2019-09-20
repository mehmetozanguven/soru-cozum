package com.myProjects.soru_cozum.chainPattern.fileStorage;

import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

public abstract class FileStorageAbstractHandler {
	private FileStorageAbstractHandler nextHandler;

	public FileStorageAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(FileStorageAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract StudentQuestionUploadResponse handle(FileStorageRequest request);
	
}
