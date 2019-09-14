package com.myProjects.soru_cozum.chainPattern.fileStorage;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.StoreType;

public class FileStorageRequest {
	private Path studentFolder;
	private Path publisherSubFolder;
	
	private MultipartFile file;
	private StoreType types; 
	private String studentUsername;
	private Long studentId; 
	private Long pageNumber;
	private Long questionNumber;
	private Long publisherId;
	
	private String questionFilePath;
	
	public FileStorageRequest(MultipartFile file, StoreType types, String studentUsername, Long studentId,
			Long pageNumber, Long questionNumber, Long publisherId) {
		this.file = file;
		this.types = types;
		this.studentUsername = studentUsername;
		this.studentId = studentId;
		this.pageNumber = pageNumber;
		this.questionNumber = questionNumber;
		this.publisherId = publisherId;
	}
	public Path getStudentFolder() {
		return studentFolder;
	}
	public void setStudentFolder(Path studentFolder) {
		this.studentFolder = studentFolder;
	}
	public Path getPublisherSubFolder() {
		return publisherSubFolder;
	}
	public void setPublisherSubFolder(Path publisherSubFolder) {
		this.publisherSubFolder = publisherSubFolder;
	}
	public MultipartFile getFile() {
		return file;
	}
	public StoreType getTypes() {
		return types;
	}
	public String getStudentUsername() {
		return studentUsername;
	}
	public Long getStudentId() {
		return studentId;
	}
	public Long getPageNumber() {
		return pageNumber;
	}
	public Long getQuestionNumber() {
		return questionNumber;
	}
	public Long getPublisherId() {
		return publisherId;
	}
	public String getQuestionFilePath() {
		return questionFilePath;
	}
	public void setQuestionFilePath(String questionFilePath) {
		this.questionFilePath = questionFilePath;
	}
	
	
}
