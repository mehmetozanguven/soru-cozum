package com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image;

import java.io.File;

import com.myProjects.soru_cozum.response.AnswerImageResponse;

public class AnswerImageDirectoryHandler extends FileAnswerImageStorageAbstractHandler{

	@Override
	public AnswerImageResponse handle(FileAnswerImageStorageRequest request) {
		File isAnswerImageFolderExists = new File(request.getAnswerImagePath().toString());
		if (!isAnswerImageFolderExists.exists())
			isAnswerImageFolderExists.mkdir();
		
		File isTeacherIdFolderExists = new File(request.getTeacherIdPath().toString());
		if (!isTeacherIdFolderExists.exists())
			isTeacherIdFolderExists.mkdir();
		
		return getNextHandler().handle(request);
	}
	
}
