package com.myProjects.soru_cozum.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.enums.StoreType;

public interface FileStorageService {
	String storeFile(MultipartFile file, 
			StoreType types, 
			String studentUsername,
			Long studentId, 
			Long pageNumber,
			Long questionNumber,
			Long publisherId);

	Resource loadFileAsResource(String fileName, StoreType types);
}
