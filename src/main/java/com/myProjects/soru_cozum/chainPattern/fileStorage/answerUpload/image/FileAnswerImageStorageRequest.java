package com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public class FileAnswerImageStorageRequest {
	private Path answerImagePath;
	private Path teacherIdPath;
	private MultipartFile answerImageFile;
	private Long questionId;
	private Long teacherId;

	public FileAnswerImageStorageRequest(Path answerImageFolder, Path teacherIdPath, MultipartFile answerImageFile,
			Long questionId, Long teacherId) {
		this.answerImagePath = answerImageFolder;
		this.teacherIdPath = teacherIdPath;
		this.answerImageFile = answerImageFile;
		this.questionId = questionId;
		this.teacherId = teacherId;
	}

	public Path getAnswerImagePath() {
		return answerImagePath;
	}

	public MultipartFile getAnswerImageFile() {
		return answerImageFile;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public Path getTeacherIdPath() {
		return teacherIdPath;
	}

}
