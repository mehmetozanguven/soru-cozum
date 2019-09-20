package com.myProjects.soru_cozum.chainPattern.fileStorage;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.StoreType;

public class FileStorageRequest {
	private Path publisherSubFolder;
	private Path questionCategoryFolder;
	private Path questionSubCategoryFolder;
	private Path pageNumberFolder;

	private MultipartFile file;
	private StoreType types;
	private String studentUsername;
	private Long studentId;
	private Integer pageNumber;
	private Integer questionNumber;
	private Long publisherId;

	private String questionFilePath;

	public FileStorageRequest(MultipartFile file, StoreType types, Integer pageNumber, Integer questionNumber,
			Long publisherId) {
		this.file = file;
		this.types = types;
		this.pageNumber = pageNumber;
		this.questionNumber = questionNumber;
		this.publisherId = publisherId;
	}

	public Path getPublisherSubFolder() {
		return publisherSubFolder;
	}

	public void setPublisherSubFolder(Path publisherSubFolder) {
		this.publisherSubFolder = publisherSubFolder;
	}

	public Path getPageNumberFolder() {
		return pageNumberFolder;
	}

	public void setPageNumberFolder(Path pageNumberFolder) {
		this.pageNumberFolder = pageNumberFolder;
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

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Integer getQuestionNumber() {
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

	public Path getQuestionCategoryFolder() {
		return questionCategoryFolder;
	}

	public void setQuestionCategoryFolder(Path questionCategoryFolder) {
		this.questionCategoryFolder = questionCategoryFolder;
	}

	public Path getQuestionSubCategoryFolder() {
		return questionSubCategoryFolder;
	}

	public void setQuestionSubCategoryFolder(Path questionSubCategoryFolder) {
		this.questionSubCategoryFolder = questionSubCategoryFolder;
	}

}
