package com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.request.service.StudentQuestionUploadRequest;

public class FileQuestionStorageRequest {
	private Path publisherSubFolder;
	private Path questionCategoryFolder;
	private Path questionSubCategoryFolder;
	private Path pageNumberFolder;

	private MultipartFile file;
	private StoreType types;
	private Long studentId;
	private Integer pageNumber;
	private Integer questionNumber;
	private Long publisherId;
	private String questionCategory;
	private String questionSubCategory;

	private String questionFilePath;

	public static FileQuestionStorageRequest createFileStorageFromServiceRequest(
			StudentQuestionUploadRequest serviceRequest) {
		FileQuestionStorageRequest newFileQuestionStorageRequest = new FileQuestionStorageRequest();
		newFileQuestionStorageRequest.setFile(serviceRequest.getQuestionImageFile());
		newFileQuestionStorageRequest.setPageNumber(serviceRequest.getPageNumber());
		newFileQuestionStorageRequest.setPublisherId(serviceRequest.getPublisherId());
		newFileQuestionStorageRequest.setQuestionCategory(serviceRequest.getQuestionCategory());
		newFileQuestionStorageRequest.setQuestionSubCategory(serviceRequest.getQuestionSubCategory());
		newFileQuestionStorageRequest.setQuestionNumber(serviceRequest.getQuestionNumber());
		newFileQuestionStorageRequest.setTypes(StoreType.STUDENT_QUESTION);
		return newFileQuestionStorageRequest;
	}

	public FileQuestionStorageRequest() {
	}

	public FileQuestionStorageRequest(MultipartFile file, StoreType types, Integer pageNumber, Integer questionNumber,
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

	public Path getPageNumberFolder() {
		return pageNumberFolder;
	}

	public void setPageNumberFolder(Path pageNumberFolder) {
		this.pageNumberFolder = pageNumberFolder;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public StoreType getTypes() {
		return types;
	}

	public void setTypes(StoreType types) {
		this.types = types;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getQuestionSubCategory() {
		return questionSubCategory;
	}

	public void setQuestionSubCategory(String questionSubCategory) {
		this.questionSubCategory = questionSubCategory;
	}

	public String getQuestionFilePath() {
		return questionFilePath;
	}

	public void setQuestionFilePath(String questionFilePath) {
		this.questionFilePath = questionFilePath;
	}

}
