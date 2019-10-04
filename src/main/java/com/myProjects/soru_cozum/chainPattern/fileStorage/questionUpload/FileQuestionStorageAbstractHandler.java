package com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload;

import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

public abstract class FileQuestionStorageAbstractHandler {
	private FileQuestionStorageAbstractHandler nextHandler;

	public FileQuestionStorageAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(FileQuestionStorageAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract StudentQuestionUploadResponse handle(FileQuestionStorageRequest request);
	
}
