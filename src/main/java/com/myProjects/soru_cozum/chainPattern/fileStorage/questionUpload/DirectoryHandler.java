package com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

public class DirectoryHandler extends FileQuestionStorageAbstractHandler {
	private Logger LOGGER = LoggerFactory.getLogger(DirectoryHandler.class);
	@Override
	public StudentQuestionUploadResponse handle(FileQuestionStorageRequest request) {
		// Questions/Student/{studentID}
	
		// Questions/Student/{publisherId}
		File isPublishFolderExists = new File(request.getPublisherSubFolder().toString());
		
		if (!isPublishFolderExists.exists())
			isPublishFolderExists.mkdir();
		
		// Questions/Student/{studentID}/{publisherId}/{questionCategory}
		File isQuestionCategoryFolderExists = new File(request.getQuestionCategoryFolder().toString());
		if (!isQuestionCategoryFolderExists.exists())
			isQuestionCategoryFolderExists.mkdir();
		
		if (request.getQuestionSubCategoryFolder() != null) {
			// Questions/Student/{studentID}/{publisherId}/{questionCategory}/{subCategory}
			File isQuestionSubCategoryFolderExists = new File(request.getQuestionSubCategoryFolder().toString());
			if (!isQuestionSubCategoryFolderExists.exists())
				isQuestionSubCategoryFolderExists.mkdir();
		}
		
		// Questions/Student/{studentID}/{publisherId}/{questionCategory}/{subCategory}?/{pageNumber}
		File isPageNumberFolderExists = new File(request.getPageNumberFolder().toString());
		LOGGER.info(isPageNumberFolderExists.toString());
		if (!isPageNumberFolderExists.exists())
			isPageNumberFolderExists.mkdir();
		
		return getNextHandler().handle(request);
	}
	
	
}
