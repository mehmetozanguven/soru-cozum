package com.myProjects.soru_cozum.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.response.AddQuestionToStudent;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.StudentQuestionDownloadRequest;
import com.myProjects.soru_cozum.response.service.TeacherAnswerAudioServiceResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;
import com.myProjects.soru_cozum.service.FileStorageService;

@RestController
@RequestMapping("/api/file")
public class FileController {
	private final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private Environment environment;
	
	@PostMapping("/downloadFile/AnswerAudio")
	public ResponseEntity<?> downloadTeacherAudioFile(@RequestBody TeacherAnswerAudioServiceResponse userRequest){
		String filePath = fileStorageService.createAnswerAudioFilePath(userRequest);
		Optional<Resource> resource = fileStorageService.loadFileAsResource_opt(filePath);
		
		if (!resource.isPresent()) {
			GenericResponse<String> response = new GenericResponse<String>();
			response.setStatu("Error");
			response.setInformation("There is no file");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.get().getFilename() + "\"")
                .body(resource.get());
	}

	@PostMapping("/downloadFile/AnswerImage")
	public ResponseEntity<?> downloadTeacherAnswerImage(@RequestBody TeacherAnswerImageServiceResponse userRequest){
		String filePath = fileStorageService.createAnswerImageFilePath(userRequest);
		Optional<Resource> resource = fileStorageService.loadFileAsResource_opt(filePath);
		
		if (!resource.isPresent()) {
			GenericResponse<String> response = new GenericResponse<String>();
			response.setStatu("Error");
			response.setInformation("There is no file");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.get().getFilename() + "\"")
                .body(resource.get());
	}
	
	@PostMapping("/downloadFile/Question")
	public ResponseEntity<?> downloadStudentQuestion(@RequestBody StudentQuestionDownloadRequest userRequest, HttpServletRequest request){
		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
		LOGGER.info(userRequest.toString());
		
		// /Questions/Students
		Path prefixPath = Paths.get(environment.getProperty("file.question_answer_dir")).normalize();
		Path questionRelativePath;
		if (userRequest.getQuestionSubCategory() != ""){
			questionRelativePath = Paths.get(userRequest.getPublisherId() + "/" 
					+ userRequest.getQuestionCategory() + "/" + userRequest.getQuestionSubCategory() + "/" + 
					userRequest.getPageNumber() + "/" +  userRequest.getQuestionNumber());
		}else {
			questionRelativePath = Paths.get(userRequest.getPublisherId() + "/" 
					+ userRequest.getQuestionCategory() + "/" + 
					userRequest.getPageNumber() + "/" +  userRequest.getQuestionNumber());
		}
		
		File isQuestionFileExists = prefixPath.resolve(questionRelativePath).toFile();
		if (!isQuestionFileExists.isFile()) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("There is no file."));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Resource resource = fileStorageService.loadFileAsResource(questionRelativePath.toString(), StoreType.STUDENT_QUESTION);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException | NullPointerException ex) {
			LOGGER.info("Exception: " + ex.getMessage() );
		}
		// Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}

}
