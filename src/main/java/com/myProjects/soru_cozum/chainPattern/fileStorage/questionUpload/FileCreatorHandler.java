package com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

public class FileCreatorHandler extends FileQuestionStorageAbstractHandler{
	private Logger LOGGER = LoggerFactory.getLogger(FileCreatorHandler.class);
	@Override
	public StudentQuestionUploadResponse handle(FileQuestionStorageRequest request) {
		String fileName = StringUtils.cleanPath(request.getQuestionNumber().toString());
		LOGGER.info("filename: " + fileName);
		try {
			if (fileName.contains("..")) {
				LOGGER.error("Filename contains invalid path sequence");
				throw new IOException("Filename contain invalid path sequence");
			}
			
			Path questionFilePath = null;
			if (request.getTypes() == StoreType.STUDENT_QUESTION) {
				questionFilePath = request.getPageNumberFolder().resolve(fileName);
				LOGGER.info("target file location " + questionFilePath);
			}
			else {
				throw new IOException("Different file store type");
			}
			Files.copy(request.getFile().getInputStream(), questionFilePath, StandardCopyOption.REPLACE_EXISTING);
			request.setQuestionFilePath(fileName);
			StudentQuestionUploadResponse response = new StudentQuestionUploadResponse();
			response.setPageNumber(request.getPageNumber());
			response.setPublisherId(request.getPublisherId());
			response.setQuestionCategory(request.getQuestionCategory());
			response.setQuestionNumber(request.getQuestionNumber());
			response.setQuestionSubCategory(request.getQuestionSubCategory());
			return response;
			
		} catch (IOException ex) {
			LOGGER.error("Can't store the file, exception: " + ex);
		}
		return null; 
		
	}
	
	private Map<Integer, String> parseQuestionFilePath(String questionFilePath){
		Map<Integer, String> questionParsedMap = new HashMap<Integer, String>();
		
		String[] splitQuestion = questionFilePath.split("/");
		
		int count = 0;
		for (String eachPart: splitQuestion) {
			questionParsedMap.put(count, eachPart);
			count ++;
		}
		return questionParsedMap;
	}
	
//	target file location Questions/Student/1/Matematik/Hucreler/13/12

	private StudentQuestionUploadResponse prepareResponseFromParsedMap(Map<Integer, String> parsedMap) {
		StudentQuestionUploadResponse response ;
		Long studentId, publisherId;
		String questionCategory, questionSubCategory;
		int pageNumber, questionNumber;
		
		publisherId = Long.parseLong(parsedMap.get(2));
		questionCategory = parsedMap.get(3);
		if (parsedMap.size() == 7) {	
			questionSubCategory = parsedMap.get(4);
			pageNumber = Integer.valueOf(parsedMap.get(5));
			questionNumber = Integer.valueOf(parsedMap.get(6));
		}else {
			questionSubCategory = "";
			pageNumber = Integer.valueOf(parsedMap.get(4));
			questionNumber = Integer.valueOf(parsedMap.get(5));
		}
		response = new StudentQuestionUploadResponse( publisherId, questionCategory, questionSubCategory,
				pageNumber, questionNumber);
		return response;
	}
	
}
