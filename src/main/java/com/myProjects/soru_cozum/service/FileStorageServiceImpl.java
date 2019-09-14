package com.myProjects.soru_cozum.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.StoreType;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final Logger LOGGER =LoggerFactory.getLogger(FileStorageServiceImpl.class);
	
	
	private Path teacherFileStorageLocation;
	private Path studentFileStorageLocation;
	
	@Autowired
    public FileStorageServiceImpl(Environment environment) {
		LOGGER.debug(environment.getProperty("file.uploadStudentDir"));
        this.teacherFileStorageLocation = Paths.get(environment.getProperty("file.uploadTeacherDir"))
                .toAbsolutePath().normalize();
        this.studentFileStorageLocation = Paths.get(environment.getProperty("file.uploadStudentDir"))
                .toAbsolutePath().normalize();
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
	public String storeFile(MultipartFile file, StoreType types, Long studentId, String studentUsername) {
		if (types == null)
			return "Sorry, types parameter is null";
		
		String fileName = StringUtils.cleanPath(studentId + "!" + studentUsername + "/" + file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				LOGGER.error("Filename contains invalid path sequence");
				return "Sorry! Filename contains invalid path sequence ";
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = null;
			if (types == StoreType.STUDENT_QUESTION)
				targetLocation = this.studentFileStorageLocation.resolve(fileName);
			else if (types == StoreType.TEACHER_ANSWER_IMAGE)
				targetLocation = this.teacherFileStorageLocation.resolve("/image/" + fileName);
			else
				targetLocation = this.teacherFileStorageLocation.resolve("/video/" + fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			LOGGER.error("Can't store the file, exception: " + ex.getMessage());
			return "Sorry!, Could not store file ";
		}
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
