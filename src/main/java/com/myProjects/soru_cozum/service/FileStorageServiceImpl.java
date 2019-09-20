package com.myProjects.soru_cozum.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.chainPattern.fileStorage.DirectoryHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.FileCreatorHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.FileStorageAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.FileStorageRequest;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final Logger LOGGER =LoggerFactory.getLogger(FileStorageServiceImpl.class);
	private final String DEFAULT_SUB_CATEGORY = "default";
	
	private Path teacherFileStorageLocation;
	private Path questionsFileStorageLocation;
	private Path questionsRelativeFileStorageLocaltion;
	
	private FileStorageAbstractHandler fileCreatorHandler;
	private FileStorageAbstractHandler directoryHandler;


	@Autowired
    public FileStorageServiceImpl(Environment environment) {
		this.fileCreatorHandler = new FileCreatorHandler();
		this.directoryHandler = new DirectoryHandler();
		
		LOGGER.debug(environment.getProperty("file.uploadStudentDir"));
        this.teacherFileStorageLocation = Paths.get(environment.getProperty("file.uploadTeacherDir"))
                .toAbsolutePath().normalize();
      
        this.questionsFileStorageLocation = Paths.get(environment.getProperty("file.uploadStudentDir"))
                .toAbsolutePath().normalize();
        this.questionsRelativeFileStorageLocaltion = Paths.get(environment.getProperty("file.uploadStudentDir")).normalize();
        LOGGER.info("student file storage: " + questionsFileStorageLocation);
		LOGGER.info("teacher file storage: " + teacherFileStorageLocation);

        try {
        	Files.createDirectories(questionsFileStorageLocation);
            Files.createDirectories(teacherFileStorageLocation);
        } catch (Exception ex) {
            LOGGER.error("Can't inject file storage location for student & teacher. THIS WILL GIVE AN ERROR.");
            LOGGER.error("Error message: " + ex);
        }
    }
	
	@Override
	public StudentQuestionUploadResponse storeFile(MultipartFile file, 
							StoreType types, 
							Integer pageNumber,
							Integer questionNumber,
							Long publisherId, 
							QuestionCategory questionCategory, 
							String questionSubCategory) {
		if (types == null)
			return new StudentQuestionUploadResponse();
		LOGGER.info("Question category: " + questionCategory.getValue());
		LOGGER.info("Question sub-category: " + questionSubCategory);
		Path publishFolder = this.questionsRelativeFileStorageLocaltion.resolve(String.valueOf(publisherId));
		Path questionCategoryFolder = publishFolder.resolve(questionCategory.getValue());
		
		Path questionSubCategoryFolder = null;
		LOGGER.info(questionSubCategory.equalsIgnoreCase("nonce") + " ");
		Path pageNumberFolder = questionCategoryFolder.resolve(String.valueOf(pageNumber));
		if (questionSubCategory.equalsIgnoreCase("nonce")) {
			LOGGER.debug("Empty sub category, default will be provided");
			questionSubCategoryFolder = questionCategoryFolder.resolve(DEFAULT_SUB_CATEGORY);
		}else {
			questionSubCategoryFolder = questionCategoryFolder.resolve(questionSubCategory);
			pageNumberFolder = questionSubCategoryFolder.resolve(String.valueOf(pageNumber));
		}
		
		FileStorageRequest handlerRequest = new FileStorageRequest(file, types, pageNumber, questionNumber, publisherId);

		handlerRequest.setPublisherSubFolder(publishFolder);
		handlerRequest.setQuestionCategoryFolder(questionCategoryFolder);
		handlerRequest.setQuestionSubCategoryFolder(questionSubCategoryFolder);
		handlerRequest.setPageNumberFolder(pageNumberFolder);
		
		directoryHandler.setNextHandler(fileCreatorHandler);

		return directoryHandler.handle(handlerRequest);
	}
	
	@Override
	public StudentQuestionUploadResponse storeFile(MultipartFile file, 
							StoreType types, 
							Integer pageNumber,
							Integer questionNumber,
							Long publisherId, 
							String questionCategory, 
							String questionSubCategory) {
		if (types == null)
			return new StudentQuestionUploadResponse();
		LOGGER.info("Question category: " + questionCategory);
		LOGGER.info("Question sub-category: " + questionSubCategory);
		Path publishFolder = this.questionsRelativeFileStorageLocaltion.resolve(String.valueOf(publisherId));
		Path questionCategoryFolder = publishFolder.resolve(questionCategory);
		
		Path questionSubCategoryFolder = null;
		LOGGER.info(questionSubCategory.equalsIgnoreCase("nonce") + " ");
		Path pageNumberFolder = questionCategoryFolder.resolve(String.valueOf(pageNumber));
		if (questionSubCategory.equalsIgnoreCase("nonce")) {
			LOGGER.debug("Empty sub category, default will be provided");
			questionSubCategoryFolder = questionCategoryFolder.resolve(DEFAULT_SUB_CATEGORY);
		}else {
			questionSubCategoryFolder = questionCategoryFolder.resolve(questionSubCategory);
			pageNumberFolder = questionSubCategoryFolder.resolve(String.valueOf(pageNumber));
		}
		
		FileStorageRequest handlerRequest = new FileStorageRequest(file, types, pageNumber, questionNumber, publisherId);

		handlerRequest.setPublisherSubFolder(publishFolder);
		handlerRequest.setQuestionCategoryFolder(questionCategoryFolder);
		handlerRequest.setQuestionSubCategoryFolder(questionSubCategoryFolder);
		handlerRequest.setPageNumberFolder(pageNumberFolder);
		
		directoryHandler.setNextHandler(fileCreatorHandler);

		return directoryHandler.handle(handlerRequest);
	}
	
	@Override
	public Resource loadFileAsResource(String fileName, StoreType types) {
        try {
        	Path filePath = null;
        	if (types == StoreType.STUDENT_QUESTION) {
        		filePath = this.questionsFileStorageLocation.resolve(fileName).normalize();
        	}else {
                filePath = this.teacherFileStorageLocation.resolve(fileName).normalize();
        	}
        	
            LOGGER.info("File Path after normalize: " + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
            	LOGGER.error("File not found " + fileName);
                return null;
            }
        } catch (MalformedURLException ex) {
        	LOGGER.error("File not found " + fileName + " " + ex);
            return null;
        }
    }
}
