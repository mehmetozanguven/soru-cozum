package com.myProjects.soru_cozum.chainPattern.fileStorage;

public abstract class FileStorageAbstractHandler {
	private FileStorageAbstractHandler nextHandler;

	public FileStorageAbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(FileStorageAbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract String handle(FileStorageRequest request);
	
}
