package com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.myProjects.soru_cozum.response.AnswerImageResponse;

public class AnswerImageCreatorHandler extends FileAnswerImageStorageAbstractHandler{
	private Logger LOGGER = LoggerFactory.getLogger(AnswerImageCreatorHandler.class);
	@Override
	public AnswerImageResponse handle(FileAnswerImageStorageRequest request) {
		String fileName = request.getQuestionId().toString();		
		
		try {
			Path answerImageFilePath = request.getTeacherIdPath().resolve(fileName);
			Map<Integer, String> answerImageParsedMap = parseAnswerImageFilePath(answerImageFilePath.toString());
			Files.copy(request.getAnswerImageFile().getInputStream(), answerImageFilePath, StandardCopyOption.REPLACE_EXISTING);
			return prepareResponseFromParsedMap(answerImageParsedMap);
		}catch (IOException e) {
			LOGGER.error("Can't upload answer Image file. It will cause an error");
		}
		
		return null;
	}
	// target location: /Answers/Teacher/answerImages/{teacherId}/{questionId}
	private Map<Integer, String> parseAnswerImageFilePath(String questionFilePath){
		Map<Integer, String> answerImageParsedMap = new HashMap<Integer, String>();
		
		String[] splitQuestion = questionFilePath.split("/");
		
		int count = 0;
		for (String eachPart: splitQuestion) {
			answerImageParsedMap.put(count, eachPart);
			count ++;
		}
		return answerImageParsedMap;
	}
	
	private AnswerImageResponse prepareResponseFromParsedMap(Map<Integer, String> parsedMap) {
		String folderName = parsedMap.get(2);
		int teacherId = Integer.valueOf(parsedMap.get(3));
		int questionId =Integer.valueOf(parsedMap.get(4));
		AnswerImageResponse response = new AnswerImageResponse(folderName, teacherId, questionId);
		return response;
	}
}
