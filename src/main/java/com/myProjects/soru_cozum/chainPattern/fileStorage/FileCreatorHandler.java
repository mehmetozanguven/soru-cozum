package com.myProjects.soru_cozum.chainPattern.fileStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.myProjects.soru_cozum.enums.StoreType;

public class FileCreatorHandler extends FileStorageAbstractHandler{
	private Logger LOGGER = LoggerFactory.getLogger(FileCreatorHandler.class);
	@Override
	public String handle(FileStorageRequest request) {
		String fileName = StringUtils.cleanPath(request.getPageNumber()+"_"+request.getQuestionNumber());
		
		try {
			if (fileName.contains("..")) {
				LOGGER.error("Filename contains invalid path sequence");
				return "Sorry! Filename contains invalid path sequence ";
			}
			
			Path targetLocation = null;
			if (request.getTypes() == StoreType.STUDENT_QUESTION)
				targetLocation = request.getPublisherSubFolder().resolve(fileName);
			else {
				return "Different types than: " + StoreType.STUDENT_QUESTION;
			}
			Files.copy(request.getFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			request.setQuestionFilePath(fileName);
			return targetLocation.toString();
			
		} catch (IOException ex) {
			LOGGER.error("Can't store the file, exception: " + ex.getMessage());
			return "Sorry!, Could not store file ";
		}
		
	}
	
}
