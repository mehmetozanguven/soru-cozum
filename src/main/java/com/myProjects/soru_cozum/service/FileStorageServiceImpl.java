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
import com.myProjects.soru_cozum.enums.StoreType;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final Logger LOGGER =LoggerFactory.getLogger(FileStorageServiceImpl.class);
	
	
	private Path teacherFileStorageLocation;
	private Path teacherRelativeFileStorageLocation;
	private Path studentFileStorageLocation;
	private Path studentRelativeFileStorageLocaltion;
	
	private FileStorageAbstractHandler fileCreatorHandler;
	private FileStorageAbstractHandler directoryHandler;


	@Autowired
    public FileStorageServiceImpl(Environment environment) {
		this.fileCreatorHandler = new FileCreatorHandler();
		this.directoryHandler = new DirectoryHandler();
		
		LOGGER.debug(environment.getProperty("file.uploadStudentDir"));
        this.teacherFileStorageLocation = Paths.get(environment.getProperty("file.uploadTeacherDir"))
                .toAbsolutePath().normalize();
        this.teacherRelativeFileStorageLocation = Paths.get(environment.getProperty("file.uploadTeacherDir")).normalize();
        this.studentFileStorageLocation = Paths.get(environment.getProperty("file.uploadStudentDir"))
                .toAbsolutePath().normalize();
        this.studentRelativeFileStorageLocaltion = Paths.get(environment.getProperty("file.uploadStudentDir")).normalize();
        LOGGER.info("student file storage: " + studentFileStorageLocation);
		LOGGER.info("teacher file storage: " + teacherFileStorageLocation);

        try {
        	Files.createDirectories(studentFileStorageLocation);
            Files.createDirectories(teacherFileStorageLocation);
        } catch (Exception ex) {
            LOGGER.error("Can't inject file storage location for student & teacher. THIS WILL GIVE AN ERROR.");
            LOGGER.error("Error message: " + ex);
        }
    }
	
	@Override
	public String storeFile(MultipartFile file, 
							StoreType types, 
							String studentUsername,
							Long studentId, 
							Long pageNumber,
							Long questionNumber,
							Long publisherId) {
		if (types == null)
			return "Sorry, types parameter is null";
		LOGGER.info("Check user has a folder. Folder name: " + studentId + "_" + studentUsername);
		
		Path studentFolder = this.studentRelativeFileStorageLocaltion.resolve(studentId + "_" + studentUsername);
		Path publishFolder = studentFolder.resolve(String.valueOf(publisherId));

		FileStorageRequest handlerRequest = new FileStorageRequest(file, types, studentUsername, studentId, pageNumber, questionNumber, publisherId);
		handlerRequest.setStudentFolder(studentFolder);
		handlerRequest.setPublisherSubFolder(publishFolder);
		
		directoryHandler.setNextHandler(fileCreatorHandler);

		return directoryHandler.handle(handlerRequest);
	}
	
	@Override
	public Resource loadFileAsResource(String fileName, StoreType types) {
        try {
        	Path filePath = null;
        	if (types == StoreType.STUDENT_QUESTION) {
        		filePath = this.studentFileStorageLocation.resolve(fileName).normalize();
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
